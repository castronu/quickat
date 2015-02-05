(function () {
    'use strict';
    quickiesApp.factory('UserGroups', ['$resource',
        function ($resource) {
            return $resource('/api/userGroups', {}, {
                list: {method: 'GET', isArray:true}
            });
        }
    ]);
})();