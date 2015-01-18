(function () {
    'use strict';
    quickiesApp.factory('Quickies', ['$resource',
        function ($resource) {
            return $resource('/quickies/:id', {}, {
                query: {method: 'GET'},
                create: {method: 'POST'},
                delete: {method: 'DELETE'}
            });
        }
    ])
    ;
})
();