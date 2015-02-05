(function () {
    'use strict';
    quickiesApp.factory('Comments', ['$resource',
        function ($resource) {
            return $resource('/api/quickies/:quickieId/comments/', {quickieId: '@quickieId'}, {
                list: {
                    method: 'GET',
                    isArray: true
                },
                create: {method: 'POST'},
                delete: {method: 'DELETE'}
            });
        }
    ]);
})();