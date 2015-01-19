(function () {
    'use strict';
    quickiesApp.factory('Comments', ['$resource',
        function ($resource) {
            return $resource('/quickies/:quickieId/comments/', {quickieId: '@quickieId'}, {
                create: {method: 'POST'},
                delete: {method: 'DELETE'}
            });
        }
    ]);
})();