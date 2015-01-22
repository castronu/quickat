(function () {
    'use strict';
    quickiesApp.controller('CountersController', ['$scope', '$rootScope', 'Quickies',
        function ($scope, $rootScope, Quickies) {
            $scope.counters = Quickies.counters();

            $rootScope.$on('$routeChangeSuccess', function() {
                refreshCounters();
            });

            $rootScope.$on('refreshCounters', function() {
                refreshCounters();
            });

            function refreshCounters() {
                var counters = Quickies.counters({}, function() {
                    $scope.counters = counters;
                });
            }
        }
    ]);
})();