(function () {
    'use strict';
    quickiesApp.controller('ProfileController', ['$scope', 'Users', '$location', '$routeParams',
        function ($scope, Users, $location, $routeParams) {

            if ($routeParams.userId != null) {
                var user = Users.query({id: $routeParams.userId});

                user.$promise.then(function (data) {
                    $scope.user = user;
                });
            }

            $scope.submit = function () {
                var createdUser = Users.create($scope.user);

                createdUser.$promise.then(function (data) {
                    console.log("/myProfile/" + data.id);
                    $location.path("/myProfile/" + data.id);
                });
            }
        }
    ]);
})();