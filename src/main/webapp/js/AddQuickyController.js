(function () {
    'use strict';
    quickiesApp.controller('AddQuickyController', ['$scope', '$location', 'flash', 'Quickies',
        function ($scope, $location, flash, Quickies) {
            $scope.title = "Add quicky";

            $scope.quickie = {};

            $scope.openDatePicker = function($event) {
                $event.preventDefault();
                $event.stopPropagation();

                $scope.opened = true;
            };

            $scope.submit = function() {
                Quickies.create({}, $scope.quickie,
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