(function () {
    'use strict';
    quickiesApp.controller('MyQuickiesController', ['$scope', '$location', 'Users', 'Quickies',
        function ($scope, $location, Users, Quickies) {
            $scope.title = "My quickies";

            $scope.deleteQuickie = function (idToDelete, index) {
                Quickies.delete({'id': idToDelete}, function (success) {
                    if (success) {
                        $scope.quickies.splice(index, 1);
                    }
                });

            };

            $scope.quickies = Users.quickies();

        }
    ]);
})();