(function () {
    'use strict';
    quickiesApp.factory('MyProfileService', ['Users', function (Users) {
        return {
            page: {
                title: 'My profile',
                editable: true
            },
            getProfilePromise: function ($routeParams) {
                return Users.query({id: 'me'});
            }
        }
    }]);
    quickiesApp.factory('UserProfileService', ['Users', function (Users) {
        return {
            page: {
                title: 'User profile',
                editable: false
            },
            getProfilePromise: function ($routeParams) {
                return Users.query({id: $routeParams.userId});
            }
        }
    }]);
    quickiesApp.controller('ProfileController', ['$scope', 'Users', '$location', '$routeParams', 'profileService',
        function ($scope, Users, $location, $routeParams, profileService) {
            $scope.page = profileService.page;

            var user = profileService.getProfilePromise($routeParams)

            user.$promise.then(function () {
                $scope.user = user;
            });

            $scope.submit = function () {
                var createdUser = Users.update($scope.user);

                createdUser.$promise.then(function () {
                    $location.path("/profile/me");
                });
            }
        }
    ]);
})();