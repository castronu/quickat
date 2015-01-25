(function () {
    'use strict';
    quickiesApp.controller('LoginController', ['$scope', 'store', '$location', 'auth', 'Users',
        function ($scope, store, $location, auth, Users) {
            $scope.auth = auth;

            $scope.login = function () {
                auth.signin({}, function (profile, token) {
                    // Success callback
                    store.set('profile', profile);
                    store.set('token', token);

                    $scope.$emit('refreshCounters');

                    var user = new Users({
                        userId: profile.user_id,
                        firstName: profile.name,
                        nickname: profile.nickname,
                        picture: profile.picture
                    });

                    user.$save({}, function () {
                        store.set('id', user.id);
                        $location.path('/profile/edit');
                    }, function(response) {
                        store.set('id', response.data.id);
                    });
                }, function () {
                    // Error callback
                });
            };

            $scope.logout = function () {
                auth.signout();
                store.remove('profile');
                store.remove('token');
                store.remove('id');

                $scope.$emit('refreshCounters');

                $location.path("/");

            };
        }
    ]);
})();