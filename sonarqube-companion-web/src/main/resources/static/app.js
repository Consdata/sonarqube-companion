var app = angular.module('sqcomp', ['ui.router']);

console.log('Initializing app...');

/**
 * Global
 */

app.config(['$urlRouterProvider', function($urlRouterProvider) {
    $urlRouterProvider.otherwise('/groups');
}]).run(['$rootScope', function($rootScope) {
    $rootScope
        .$on('$stateChangeStart',
        function() {
            $("[ui-view]").addClass("hidden");
            $(".page-loading").removeClass("hidden");
        });

    $rootScope
        .$on('$stateChangeSuccess',
        function() {
            $("[ui-view]").removeClass("hidden");
            $(".page-loading").addClass("hidden");
        });
}]);

/**
 * Navigation
 */

app.controller('SidebarNavigationController', ['$http', function($http) {
    var controller = this;
    $http.get('/api/repository/groups').success(function(data) {
        controller.groups = data;
    }).error(function(reason) {
        console.log('error... ' + reason);
    });
}]);

/**
 * Groups.
 */

app.config(['$stateProvider', function($stateProvider) {
    $stateProvider.state('groups', {
        url: '/groups',
        templateUrl: 'views/groups.tpl.html',
        controller: 'ListGroupsController',
        controllerAs: 'ctrl',
        resolve: {
            groupsPromise: ['$http', function($http) {
                return $http.get('/api/repository/groups/summaries');
            }]
        }
    });
}]).controller('ListGroupsController', ['groupsPromise', function(groupsPromise) {
    this.groups = groupsPromise.data;
}]);

/**
 * Group details.
 */

app.config(['$stateProvider', function($stateProvider) {
    $stateProvider.state('group_details', {
        url: '/group/{id}',
        templateUrl: 'views/group_details.tpl.html',
        controller: 'GroupDetailsController',
        controllerAs: 'ctrl',
        resolve: {
            groupPromise: ['$stateParams', '$http', function($stateParams, $http) {
                console.log('get...');
                return $http.get('/api/repository/groups/' + $stateParams.id);
            }]
        }
    });

}]).controller('GroupDetailsController', ['groupPromise', function(groupPromise) {
    var ctrl = this;
    ctrl.showIssues = {
        'blockers': true,
        'criticals': true,
        'majors': false,
        'minors': false,
        'infos': false
    };
    ctrl.historySize = 90;
    ctrl.group = groupPromise.data;
    ctrl.maxIssuesCount = 30;
    ctrl.trimmedData = function(data, size) {
        return data.slice(0, size);
    }
    ctrl.modal = {};
    ctrl.modal.healthStreak = {};
    ctrl.toggleVisibleIssues = function(type) {
        var keys = [];
        var labels = [];

        if (typeof type === 'string') {
            console.log('toggle' + type);
            ctrl.showIssues[type] = !ctrl.showIssues[type];
        }

        for (var key in ctrl.showIssues) {
            if (ctrl.showIssues.hasOwnProperty(key)) {
                if(ctrl.showIssues[key]) {
                    keys.push(key);
                    labels.push(key);
                }
            }
        }

        $('#project-history').html('');
        new Morris.Line({
            element: 'project-history',
            data: ctrl.trimmedData(ctrl.group.historicalData, ctrl.historySize),
            xkey: 'date',
            xLabels: 'day',
            ykeys: keys,
            labels: labels
        });
    }
    ctrl.setHistorySize = function(newSize) {
        ctrl.historySize = newSize;
        ctrl.toggleVisibleIssues();
    };
    ctrl.showStreakModal = function(what) {
        if (what === 'significant') {
            ctrl.modal.healthStreak = ctrl.group.newsetSignificantIssue;
        } else {
            ctrl.modal.healthStreak = ctrl.group.newestAnyIssue;
        }
        ctrl.modal.healthStreak
        $('#healthStreakModal').modal('show');
    }
    ctrl.toggleVisibleIssues();
}]);

/**
 * Project details.
 */
