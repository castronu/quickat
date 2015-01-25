(function () {
    'use strict'
    quickiesApp.controller('EditQuickieController', ['$scope', 'Quickies', '$routeParams', 'UserGroups', '$location',
        function ($scope, Quickies, $routeParams, UserGroups, $location) {
            $scope.page = {
                title: 'Edit quickie'
            };

            var quickie = Quickies.get({id: $routeParams.quickieId}, function () {
                $scope.quickie = quickie.quickie;
            });

            $scope.userGroups = UserGroups.list();

            $scope.submit = function () {
                Quickies.update({id: $scope.quickie.id}, $scope.quickie, function() {
                    $location.path('/quickies/me');
                });
            }
        }
    ]);
})();