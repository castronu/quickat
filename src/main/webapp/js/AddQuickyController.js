(function () {
    'use strict';
    quickiesApp.controller('AddQuickyController', ['$scope', '$location', 'Quickies', 'UserGroups',
        function ($scope, $location, Quickies, UserGroups) {
            $scope.page = {
                title: 'New quickie'
            };
            $scope.quickie = {};

            $scope.userGroups = UserGroups.list();

            $scope.submit = function () {
                Quickies.create({}, $scope.quickie,
                    function (value, responseHeaders) {
                        $location.path("/quickies/me");
                    }
                );
            }
        }
    ]);
})();