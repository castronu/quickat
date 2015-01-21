(function () {
    'use strict';
    quickiesApp.controller('MyQuickiesController', ['$scope', '$location', 'UserQuickies', 'Quickies',
        function ($scope, $location, UserQuickies, Quickies) {
            $scope.title = "My quickies";

            $scope.deleteQuickie = function (idToDelete, index) {
                Quickies.delete({'id': idToDelete}, function (success) {
                    if (success) {
                        $scope.quickies.splice(index, 1);
                    }
                });
            };

            $scope.quickies = UserQuickies.quickies();
        }
    ]);
})();