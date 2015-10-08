/// <reference path="typings/angularjs/angular.d.ts" />
/// <reference path="typings/angular-ui-router/angular-ui-router.d.ts" />
/// <reference path="typings/jquery/jquery.d.ts" />
/// <reference path="typings/chartjs/chart.d.ts" />

/** missing JQuery modal typings */
interface JQuery { modal:any; }

/** missing Morris.js typings */
declare module Morris {
    export class Line {
        constructor(any);
    }
}

class ListGroupsController {

    private groups;

    /**
     * @param groupsPromise
     */
    constructor(groupsPromise) {
        this.groups = groupsPromise.data;
    }

    /**
     * Used by View.
     * @returns {any}
     */
    getGroups() {
        return this.groups;
    }

}

interface VisibleIssues {
    blockers:boolean;
    criticals:boolean;
    majors:boolean;
    minors:boolean;
    infos:boolean;
}

class GroupDetailsController {

    private group;
    private historySize:number;
    private issuesToDisplay:VisibleIssues;
    private modal;
    private firstHistoryPoint;
    private lastHistoryPoint;

    /**
     * @param groupPromise
     */
    constructor(groupPromise) {
        this.group = groupPromise.data;
        this.historySize = 90;
        this.issuesToDisplay = {
            blockers: true,
            criticals: true,
            majors: false,
            minors: false,
            infos: false
        };
        this.modal = {};

        this.toggleVisibleIssues();
    }

    /**
     * Gets current group.
     * @returns {any}
     */
    getGroup() {
        return this.group;
    }

    /**
     * Gets current issue severities to display.
     * @returns {VisibleIssues}
     */
    getIssuesToDisplay():VisibleIssues {
        return this.issuesToDisplay;
    }

    /**
     * Gets current history size.
     * @returns {number} number of days before today to display
     */
    getHistorySize():number {
        return this.historySize;
    }

    /**
     * Sets current history size.
     * @param newSize number of days before today to display
     */
    setHistorySize(newSize:number) {
        this.historySize = newSize;
        this.toggleVisibleIssues();
    }

    /**
     * Gets issues limit to display in issue details view.
     * @returns {number}
     */
    getMaxIssueDetails():number {
        return 30;
    }

    /**
     * Shows user modal with issue breaking streak.
     * <p>
     *     Streak is a number of days since last new issue.
     * </p>
     * @param what streak to show breaking issue
     */
    showStreakModal(what:string) {
        if (what === 'significant') {
            this.modal.healthStreak = this.group.newsetSignificantIssue;
        } else {
            this.modal.healthStreak = this.group.newestAnyIssue;
        }
        $('#healthStreakModal').modal('show');
    }

    /**
     * Toggels issue type to display and redraws history graph.
     * @param typeToChange optional name of issue severity to toggle on/off
     */
    toggleVisibleIssues(typeToChange?:string) {
        let keys = [];
        let labels = [];
        let currentShowData;

        if (typeof typeToChange === 'string') {
            this.issuesToDisplay[typeToChange] = !this.issuesToDisplay[typeToChange];
        }

        for (let key in this.issuesToDisplay) {
            if (this.issuesToDisplay.hasOwnProperty(key)) {
                if (this.issuesToDisplay[key]) {
                    keys.push(key);
                    labels.push(key);
                }
            }
        }

        $('#project-history').html('');
        currentShowData = this.trimData(this.getGroup().historicalData, this.getHistorySize());

        this.firstHistoryPoint = currentShowData[currentShowData.length - 1];
        this.lastHistoryPoint = currentShowData[0];
        if (keys.length > 0) {
            let min:number;
            let max:number;
            currentShowData.forEach((e) => {
                keys.forEach((key) => {
                    if (min === undefined || e[key] < min) {
                        min = e[key];
                    }
                    if (max === undefined || e[key] > max) {
                        max = e[key];
                    }
                })
            });
            new Morris.Line({
                element: 'project-history',
                data: currentShowData,
                xkey: 'date',
                xLabels: 'day',
                ykeys: keys,
                labels: labels,
                ymin: min,
                ymax: max
            });
        }
    }


    /**
     * Trimes data to expected size.
     * @param dataToTrim
     * @param size
     * @returns {T[]}
     */
    private trimData(dataToTrim:any[], size:number):any[] {
        return dataToTrim.slice(0, size);
    }

}

class SidebarNavigationController {
    private groups;
    constructor($http:ng.IHttpService) {
        $http.get('/api/repository/groups').success((data) => {
            this.groups = data;
        }).error((reason) => {
            console.log('error... ' + reason);
        });
    }
}

angular.module('sqcomp', ['ui.router'])
    .config(['$stateProvider', '$urlRouterProvider', ($stateProvider:ng.ui.IStateProvider, $urlRouterProvider:ng.ui.IUrlRouterProvider) => {
        $urlRouterProvider.otherwise('/groups');
        $stateProvider.state('groups', {
            url: '/groups',
            templateUrl: 'views/groups.tpl.html',
            controller: 'ListGroupsController',
            controllerAs: 'ctrl',
            resolve: {
                groupsPromise: ['$http', ($http) => {
                    return $http.get('/api/repository/groups/summaries');
                }]
            }
        }).state('group_details', {
            url: '/group/{id}',
            templateUrl: 'views/group_details.tpl.html',
            controller: 'GroupDetailsController',
            controllerAs: 'ctrl',
            resolve: {
                groupPromise: ['$stateParams', '$http', ($stateParams, $http:ng.IHttpService) => {
                    return $http.get('/api/repository/groups/' + $stateParams.id);
                }]
            }
        });
    }])
    .controller('ListGroupsController', ['groupsPromise', ListGroupsController])
    .controller('GroupDetailsController', ['groupPromise', GroupDetailsController])
    .controller('SidebarNavigationController', ['$http', SidebarNavigationController])
    .run(['$rootScope', ($rootScope:ng.IRootScopeService) => {
        $rootScope
            .$on('$stateChangeStart',
                () => {
                    $("[ui-view]").addClass("hidden");
                    $(".page-loading").removeClass("hidden");
                });

        $rootScope
            .$on('$stateChangeSuccess',
                () => {
                    $("[ui-view]").removeClass("hidden");
                    $(".page-loading").addClass("hidden");
                });
    }]);
