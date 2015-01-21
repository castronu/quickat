(function () {
    'use strict';
    quickiesApp.controller('ProfileController', ['$scope', 'Users', '$location', '$routeParams',
        function ($scope, Users, $location, $routeParams) {
            var userId = 'me';

            if ($routeParams.userId != null) {
                userId = $routeParams.userId;
            }

            var user = Users.query({id: userId});

            user.$promise.then(function () {
                $scope.user = user;
            });

            $scope.submit = function () {
                var createdUser = Users.update($scope.user);

                createdUser.$promise.then(function () {
                    $location.path("/myProfile");
                });
            }
        }
    ]);
})();