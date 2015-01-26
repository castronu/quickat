(function () {
    'use strict';
    quickiesApp.factory('Quickies', ['$resource',
        function ($resource) {
            return $resource('/api/quickies/:id', {}, {
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
                topFuture: {
                    method: 'GET',
                    params: {
                        filter: 'topFuture'
                    },
                    isArray: true
                },
                topPast: {
                    method: 'GET',
                    params: {
                        filter: 'topPast'
                    },
                    isArray: true
                },
                get: {method: 'GET'},
                update: {method: 'PUT'},
                create: {method: 'POST'},
                delete: {method: 'DELETE'},
                counters: {method: 'GET', params: {id: 'counters'}} // a bit hack-ish, but who cares? ;)
            });
        }
    ]);
})();
