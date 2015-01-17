(function () {
    'use strict';
    quickiesApp.factory('UserGroups', ['$resource',
        function ($resource) {
            return $resource('/userGroups', {}, {
                list: {method: 'GET', isArray:true}
            });
        }
    ]);
})();