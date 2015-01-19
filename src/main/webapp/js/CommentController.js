(function () {
    'use strict';
    quickiesApp.controller('CommentController', ['$scope', 'Comments',
        function ($scope, Comments) {
            $scope.title = "Old quickies";

            $scope.comment = {};
            //$scope.comment.comment = "TEST DE COMMNET";


            $scope.submit = function () {
                alert('test');
                /*Comments.create({}, $scope.comment,
                 function (value, responseHeaders) {
                 alert('Success');
                 $location.path("/");
                 },
                 function (value, responseHeaders) {
                 alert('Error');
                 }
                 );*/
            }
        }
    ]);
})();


