/// <reference path="typings/angularjs/angular.d.ts" />
/// <reference path="typings/angular-ui-router/angular-ui-router.d.ts" />
/// <reference path="typings/jquery/jquery.d.ts" />
/// <reference path="typings/chartjs/chart.d.ts" />
var ListGroupsController = (function () {
    /**
     * @param groupsPromise
     */
    function ListGroupsController(groupsPromise) {
        this.groups = groupsPromise.data;
    }
    /**
     * Used by View.
     * @returns {any}
     */
    ListGroupsController.prototype.getGroups = function () {
        return this.groups;
    };
    return ListGroupsController;
})();
var GroupDetailsController = (function () {
    /**
     * @param groupPromise
     */
    function GroupDetailsController(groupPromise) {
        this.group = groupPromise.data;
        this.historySize = 90;
        this.issuesToDisplay = {
            blockers: true,
            criticals: true,
            majors: false,
            minors: false,
            infos: false,
            all: false,
            significant: false,
            nonsignificant: false
        };
        this.modal = {};
        this.toggleVisibleIssues();
    }
    /**
     * Gets current group.
     * @returns {any}
     */
    GroupDetailsController.prototype.getGroup = function () {
        return this.group;
    };
    /**
     * Gets current issue severities to display.
     * @returns {VisibleIssues}
     */
    GroupDetailsController.prototype.getIssuesToDisplay = function () {
        return this.issuesToDisplay;
    };
    GroupDetailsController.prototype.getAvailableHistoryRanges = function () {
        return [90, 60, 30, 14, 7, 5];
    };
    /**
     * Gets current history size.
     * @returns {number} number of days before today to display
     */
    GroupDetailsController.prototype.getHistorySize = function () {
        return this.historySize;
    };
    /**
     * Sets current history size.
     * @param newSize number of days before today to display
     */
    GroupDetailsController.prototype.setHistorySize = function (newSize) {
        this.historySize = newSize;
        this.toggleVisibleIssues();
    };
    /**
     * Gets issues limit to display in issue details view.
     * @returns {number}
     */
    GroupDetailsController.prototype.getMaxIssueDetails = function () {
        return 30;
    };
    /**
     * Shows user modal with issue breaking streak.
     * <p>
     *     Streak is a number of days since last new issue.
     * </p>
     * @param what streak to show breaking issue
     */
    GroupDetailsController.prototype.showStreakModal = function (what) {
        if (what === 'significant') {
            this.modal.healthStreak = this.group.newsetSignificantIssue;
        }
        else {
            this.modal.healthStreak = this.group.newestAnyIssue;
        }
        $('#healthStreakModal').modal('show');
    };
    /**
     * Toggels issue type to display and redraws history graph.
     * @param typeToChange optional name of issue severity to toggle on/off
     */
    GroupDetailsController.prototype.toggleVisibleIssues = function (typeToChange) {
        var keys = [];
        var labels = [];
        var currentShowData;
        if (typeof typeToChange === 'string') {
            this.issuesToDisplay[typeToChange] = !this.issuesToDisplay[typeToChange];
        }
        for (var key in this.issuesToDisplay) {
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
            var min;
            var max;
            currentShowData.forEach(function (e) {
                keys.forEach(function (key) {
                    if (min === undefined || e[key] < min) {
                        min = e[key];
                    }
                    if (max === undefined || e[key] > max) {
                        max = e[key];
                    }
                });
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
    };
    /**
     * Trimes data to expected size.
     * @param dataToTrim
     * @param size
     * @returns {T[]}
     */
    GroupDetailsController.prototype.trimData = function (dataToTrim, size) {
        return dataToTrim.slice(0, size);
    };
    return GroupDetailsController;
})();
var SidebarNavigationController = (function () {
    function SidebarNavigationController($http) {
        var _this = this;
        $http.get('/api/repository/groups').success(function (data) {
            _this.groups = data;
        }).error(function (reason) {
            console.log('error... ' + reason);
        });
    }
    return SidebarNavigationController;
})();
angular.module('sqcomp', ['ui.router'])
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/groups');
        $stateProvider.state('groups', {
            url: '/groups',
            templateUrl: 'views/groups.tpl.html',
            controller: 'ListGroupsController',
            controllerAs: 'ctrl',
            resolve: {
                groupsPromise: ['$http', function ($http) {
                        return $http.get('/api/repository/groups/summaries');
                    }]
            }
        }).state('group_details', {
            url: '/group/{id}',
            templateUrl: 'views/group_details.tpl.html',
            controller: 'GroupDetailsController',
            controllerAs: 'ctrl',
            resolve: {
                groupPromise: ['$stateParams', '$http', function ($stateParams, $http) {
                        return $http.get('/api/repository/groups/' + $stateParams.id);
                    }]
            }
        });
    }])
    .controller('ListGroupsController', ['groupsPromise', ListGroupsController])
    .controller('GroupDetailsController', ['groupPromise', GroupDetailsController])
    .controller('SidebarNavigationController', ['$http', SidebarNavigationController])
    .run(['$rootScope', function ($rootScope) {
        $rootScope
            .$on('$stateChangeStart', function () {
            $("[ui-view]").addClass("hidden");
            $(".page-loading").removeClass("hidden");
        });
        $rootScope
            .$on('$stateChangeSuccess', function () {
            $("[ui-view]").removeClass("hidden");
            $(".page-loading").addClass("hidden");
        });
    }]);
