(function () {
    'use strict';
    quickiesApp.controller('LoginController', ['$scope', 'store', '$location', 'auth',
        function ($scope, store, $location, auth) {
            $scope.login = function () {
                auth.signin({}, function (profile, token) {
                    // Success callback
                    store.set('profile', profile);
                    store.set('token', token);
                    $location.path('/');

                    $scope.isAuthenticated = auth.isAuthenticated;
                }, function () {
                    // Error callback
                });
            };

            $scope.logout = function () {
                auth.signout();
                store.remove('profile');
                store.remove('token');

                $scope.isAuthenticated = auth.isAuthenticated;
            };


        }
    ]);
})();