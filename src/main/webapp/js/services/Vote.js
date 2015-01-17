(function () {
    'use strict';
    quickiesApp.factory('Votes', ['$resource',
        function ($resource) {
            return $resource('/quickies/:quickieId/vote', {}, {
                create: {method: 'POST'},
                delete: {method: 'DELETE'}
            });
        }
    ]);
})();