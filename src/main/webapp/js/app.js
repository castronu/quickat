var quickiesApp = angular
    .module('quickiesApp', ['ngRoute', 'ngResource', 'ui.bootstrap', 'angularMoment', 'auth0', 'angular-storage', 'angular-jwt', 'ui.bootstrap.datetimepicker', 'ngSanitize'])
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
            .when('/profile/me', {
                templateUrl: 'html/profile.html',
                controller: 'ProfileController',
                resolve: {
                    profileService: 'MyProfileService'
                },
                requiresLogin: true
            })
            .when('/profile/edit', {
                templateUrl: 'html/editProfile.html',
                controller: 'ProfileController',
                resolve: {
                    profileService: 'MyProfileService'
                },
                requiresLogin: true
            })
            .when('/profile/:userId', {
                templateUrl: 'html/profile.html',
                controller: 'ProfileController',
                resolve: {
                    profileService: 'UserProfileService'
                }
            })
            .when('/login', {
                templateUrl: 'html/login.html'
            })
            .when('/quickies/new', {
                templateUrl: 'html/editQuickie.html',
                controller: 'AddQuickyController',
                requiresLogin: true
            })
            .when('/quickies/me', {
                templateUrl: 'html/myQuickies.html',
                controller: 'MyQuickiesController',
                requiresLogin: true
            })
            .when('/quickies/past', {
                templateUrl: 'html/quickies.html',
                controller: 'QuickiesController',
                resolve: {
                    quickieService: 'PastQuickiesService'
                }
            })
            .when('/quickies/future', {
                templateUrl: 'html/quickies.html',
                controller: 'QuickiesController',
                resolve: {
                    quickieService: 'FutureQuickiesService'
                }
            })
            .when('/quickies/:quickieId', {
                templateUrl: 'html/quickieDetails.html',
                controller: 'QuickieDetailsController'
            })
            .when('/quickies/:quickieId/edit', {
                templateUrl: 'html/editQuickie.html',
                controller: 'EditQuickieController',
                requiresLogin: true
            })
            .when('/help', {
                templateUrl: 'html/help.html'
            })
            .otherwise({
                templateUrl: 'html/quickies.html',
                controller: 'QuickiesController',
                resolve: {
                    quickieService: 'FutureQuickiesService'
                }
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
                        auth.signout();
                        store.remove('profile');
                        store.remove('token');

                        $scope.$emit('refreshCounters');

                        $location.path("/");
                    }
                }
            }
        });
    });
