var quickiesApp = angular
    .module('quickiesApp', ['ngRoute', 'ui.bootstrap'])
    .config(config);

function config($routeProvider) {
    $routeProvider
        .when('/futureQuickies', {
            templateUrl: 'html/futureQuickies.html',
            controller: 'FutureQuickiesController'
        })
        .when('/addQuicky', {
            templateUrl: 'html/addQuicky.html',
            controller: 'AddQuickyController'
        })
        .when('/oldQuickies', {
            templateUrl: 'html/oldQuickies.html',
            controller: 'OldQuickiesController'
        })
        .when('/myQuickies', {
            templateUrl: 'html/myQuickies.html',
            controller: 'MyQuickiesController'
        })
        .otherwise({
            templateUrl: 'html/futureQuickies.html',
            controller: 'FutureQuickiesController'
        });
};