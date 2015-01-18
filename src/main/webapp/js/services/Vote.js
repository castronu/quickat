(function () {
    'use strict';
    quickiesApp.factory('Votes', ['$resource',
        function ($resource) {
            return $resource('/quickies/:quickieId/vote', {quickieId: '@quickieId'}, {
                create: {method: 'POST'},
                delete: {method: 'DELETE'}
            });
        }
    ]);
})();