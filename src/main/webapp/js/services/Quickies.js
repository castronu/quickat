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
                create: {method: 'POST'},
                delete: {method: 'DELETE'},
                counters: {method: 'GET', params: {id: 'counters'}} // a bit hack-ish, but who cares? ;)
            });
        }
    ]);
})();
