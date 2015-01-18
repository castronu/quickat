(function () {
    'use strict';
    quickiesApp.controller('OldQuickiesController', ['$scope', 'Quickies', 'Votes',
        function ($scope, Quickies, Votes) {
            $scope.title = "Old quickies";

            $scope.fullQuickies = Quickies.old();
        }
    ]);
})();


