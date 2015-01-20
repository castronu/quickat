(function () {
    'use strict';
    quickiesApp.controller('OldQuickiesController', ['$scope', 'Quickies', 'Comments',
        function ($scope, Quickies, Comments) {
            $scope.title = "Old quickies";

            $scope.fullQuickies = Quickies.past();

        }
    ]);
})();


