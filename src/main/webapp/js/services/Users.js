(function () {
    'use strict';
    quickiesApp.factory('Users', ['$resource',
        function ($resource) {
            return $resource('/users/:id/:cmd', {}, {
                query: {method: 'GET'},
                create: {method: 'POST'},
                quickies: {method: 'GET', params: {cmd: "quickies", id: 1}, isArray: true}
            });
        }
    ]);
})();