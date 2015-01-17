quickiesApp
    .controller('MyQuickiesController', MyQuickiesController);

function MyQuickiesController($scope, $http) {
    $http.get('quickies/').success(function (data) {
        $scope.quickies = data;
    });


    $scope.title = "My quickies";

}