(function () {
    'use strict';
    quickiesApp.controller('CountersController', ['$scope', '$rootScope', '$q', 'Quickies',
        function ($scope, $rootScope, $q, Quickies) {
            $scope.counters = Quickies.counters();

            $rootScope.$on('$routeChangeSuccess', function() {
                var counters = Quickies.counters({}, function() {
                    $scope.counters = counters;
                });
            });
        }
    ]);
})();