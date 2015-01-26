(function () {
    'use strict';
    quickiesApp.factory('Brainfuck', ['$resource',
        function ($resource) {
            return $resource('/brainfuck/', {}, {
                appName: {method: 'GET'}
            });
        }
    ]);
})();