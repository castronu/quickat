var quickiesApp = angular
			.module('quickiesApp',['ngRoute','ui.bootstrap'])
			.config(config);

function config($routeProvider) {
    $routeProvider
        .when('/futureQuickies', {
            templateUrl: 'html/futureQuickies.html',
            controller: 'FutureQuickiesController'
		});
};