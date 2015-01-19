(function () {
    'use strict';
    quickiesApp.controller('CountersController', ['$scope', '$rootScope', 'Quickies',
        function ($scope, $rootScope, Quickies) {
            $scope.counters = Quickies.counters();

            $rootScope.$on('$routeChangeSuccess', function() {
                var counters = Quickies.counters({}, function() {
                    $scope.counters = counters;
                });
            });
        }
    ]);
})();