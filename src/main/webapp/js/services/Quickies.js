(function () {
    'use strict';
    quickiesApp.factory('Quickies', ['$resource',
        function ($resource) {
            return $resource('/quickies', {}, {
                query: {method: 'GET'},
                create: {method: 'POST'}
            });
        }
    ]);
})();