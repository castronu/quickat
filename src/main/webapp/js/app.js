var quickiesApp = angular
    .module('quickiesApp', ['ngRoute', 'ngResource', 'ui.bootstrap', 'angularMoment'])
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
        .when('/myProfile', {
            templateUrl: 'html/myProfile.html',
            controller: 'MyProfileController'
        })
        .when('/createProfile', {
            templateUrl: 'html/createProfile.html',
            controller: 'CreateProfileController'
        })
        .otherwise({
            templateUrl: 'html/futureQuickies.html',
            controller: 'FutureQuickiesController'
        });
};