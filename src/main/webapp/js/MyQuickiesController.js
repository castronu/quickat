quickiesApp
    .controller('MyQuickiesController', MyQuickiesController);

function MyQuickiesController($scope, $http) {
    //TODO: when login implemented, retrieve user id from session
    $http.get('users/1/quickies/').success(function (data) {
        $scope.quickies = data;
    });

    $scope.title = "My quickies";

    $scope.deleteQuickie = function (id) {
        alert(id);
        /*if (popupService.showPopup('Really delete this?')) {
         movie.$delete(function () {
         $window.location.href = ''; //redirect to home
         });
         } */
    };

}