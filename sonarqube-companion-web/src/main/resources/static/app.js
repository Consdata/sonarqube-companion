var app = angular.module('sqcomp', ['ui.router']);

console.log('Initializing app...');

/**
 * Global
 */

app.config(['$urlRouterProvider', function($urlRouterProvider) {
    $urlRouterProvider.otherwise('/groups');
}]);

/**
 * Navigation
 */

app.controller('SidebarNavigationController', ['$http', function($http) {
    var controller = this;
    $http.get('/api/group/infos').success(function(data) {
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
                return $http.get('/api/group/summaries');
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
                return $http.get('/api/group/' + $stateParams.id);
            }]
        }
    });

}]).controller('GroupDetailsController', ['groupPromise', function(groupPromise) {
    this.group = groupPromise.data;
    this.maxIssuesCount = 50;
    new Morris.Line({
        element: 'project-history',
        data: this.group.historicalData,
        xkey: 'date',
        xLabels: 'day',
        ykeys: ['blockers', 'criticals'],
        labels: ['Blocker', 'Critical']
    });
}]);

/**
 * Project details.
 */
