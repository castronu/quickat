(function () {
    'use strict';
    quickiesApp.factory('Quickies', ['$resource',
        function ($resource) {
            return $resource('/quickies', {}, {
                past: {
                    method: 'GET',
                    params: {
                        filter: 'past'
                    },
                    isArray: true
                },
                future: {
                    method: 'GET',
                    params: {
                        filter: 'future'
                    },
                    isArray: true
                },
                create: {method: 'POST'}
            });
        }
    ]);
})();