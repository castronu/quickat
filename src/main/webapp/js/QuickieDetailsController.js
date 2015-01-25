(function () {
    'use strict';
    quickiesApp.controller('QuickieDetailsController', ['$scope', 'Quickies', '$routeParams', 'store',
        function ($scope, Quickies, $routeParams, store) {
            var quickie = Quickies.get({id: $routeParams.quickieId}, function () {
                $scope.quickie = quickie;
                $scope.page = {
                    editable: quickie.speaker.id == store.get('id')
                }
            })
        }]);
})();
