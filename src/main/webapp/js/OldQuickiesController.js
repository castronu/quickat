(function () {
    'use strict';
    quickiesApp.controller('OldQuickiesController', ['$scope', 'Quickies', 'Comments',
        function ($scope, Quickies, Comments) {
            $scope.title = "Old quickies";

            $scope.fullQuickies = Quickies.past();

            $scope.submit = function () {
                Comments.create({}, $scope.quickie,
                    function (value, responseHeaders) {
                        alert('Success');
                        $location.path("/");
                    },
                    function (value, responseHeaders) {
                        alert('Error');
                    }
                );
            }
        }
    ]);
})();


