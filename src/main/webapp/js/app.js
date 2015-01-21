var quickiesApp = angular
    .module('quickiesApp', ['ngRoute', 'ngResource', 'ui.bootstrap', 'angularMoment', 'auth0', 'angular-storage', 'angular-jwt'])
    .config(function ($routeProvider, authProvider, jwtInterceptorProvider, $httpProvider) {
        authProvider.init({
            domain: 'cpollet.auth0.com',
            clientID: 'SXOLuO6vugQOhmsBADUnT0b72gI90Tem',
                callbackURL: location.href,
            loginUrl: '/login'
        });

        jwtInterceptorProvider.tokenGetter = ['store', function (store) {
            // Return the saved token
            return store.get('token');
        }];

        $httpProvider.interceptors.push('jwtInterceptor');

        $routeProvider
            .when('/futureQuickies', {
                templateUrl: 'html/futureQuickies.html',
                controller: 'FutureQuickiesController'
            })
            .when('/addQuicky', {
                templateUrl: 'html/addQuicky.html',
                controller: 'AddQuickyController',
                requiresLogin: true
            })
            .when('/oldQuickies', {
                templateUrl: 'html/oldQuickies.html',
                controller: 'OldQuickiesController'
            })
            .when('/myQuickies', {
                templateUrl: 'html/myQuickies.html',
                controller: 'MyQuickiesController',
                requiresLogin: true
            })
            .when('/quickyDetails', {
                templateUrl: 'html/quickyDetails.html',
                controller: 'QuickyDetailsController'
            })
            .when('/myProfile/:userId', {
                templateUrl: 'html/myProfile.html',
                controller: 'ProfileController'
            })
            .when('/createProfile', {
                templateUrl: 'html/createProfile.html',
                controller: 'ProfileController'
            })
            .when('/editProfile/:userId', {
                templateUrl: 'html/createProfile.html',
                controller: 'ProfileController'
            })
            .when('/login', {
                templateUrl: 'html/login.html'
            })
            .otherwise({
                templateUrl: 'html/futureQuickies.html',
                controller: 'FutureQuickiesController'
            });
    })
    .run(function (auth, $rootScope, store, jwtHelper, $location) {
        // This hooks al auth events to check everything as soon as the app starts
        auth.hookEvents();

        $rootScope.$on('$locationChangeStart', function () {
            if (!auth.isAuthenticated) {
                var token = store.get('token');
                if (token) {
                    if (!jwtHelper.isTokenExpired(token)) {
                        auth.authenticate(store.get('profile'), token);
                    } else {
                        // Either show Login page or use the refresh token to get a new idToken
                        $location.path('/');
                    }
                }
            }
        });
    });
