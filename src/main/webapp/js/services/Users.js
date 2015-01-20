(function () {
    'use strict';
    quickiesApp.factory('UserQuickies', ['$resource',
        function ($resource) {
            return $resource('/users/:id/:cmd', {}, {
                quickies: {method: 'GET', params: {cmd: "quickies", id: 1}, isArray: true}
            });
        }
    ]);

    quickiesApp.factory('Users', ['$resource',
        function ($resource) {
            return $resource('/users/:id', {}, {
                query: {method: 'GET'},
                create: {method: 'POST'}
            });
        }
    ]);
})();