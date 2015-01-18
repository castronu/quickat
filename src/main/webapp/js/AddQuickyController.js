(function () {
    'use strict';
    quickiesApp.controller('AddQuickyController', ['$scope', '$location', 'Quickies', 'UserGroups',
        function ($scope, $location, Quickies, UserGroups) {
            $scope.title = "Add quicky";

            $scope.quickie = {};

            $scope.userGroups = UserGroups.list();

            $scope.submit = function () {
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