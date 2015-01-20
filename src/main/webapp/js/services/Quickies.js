(function () {
    'use strict';
    quickiesApp.factory('Quickies', ['$resource',
        function ($resource) {
            return $resource('/quickies/:id', {}, {
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
                topActive: {
                    method: 'GET',
                    params: {
                        filter: 'topActive'
                    },
                    isArray: true
                },
                create: {method: 'POST'},
                delete: {method: 'DELETE'},
                counters: {method: 'GET', params: {id: 'counters'}} // a bit hack-ish, but who cares? ;)
            });
        }
    ]);
})();
