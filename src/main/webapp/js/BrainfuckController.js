(function () {
    'use strict';
    quickiesApp.controller('BrainfuckController', ['$scope', '$rootScope', 'Brainfuck',
        function ($scope, $rootScope, Brainfuck) {

            var applicationName = Brainfuck.appName({}, function () {
                $scope.applicationName = applicationName.answer;
            });
        }
    ]);
})();