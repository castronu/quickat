(function () {
    'use strict';
    quickiesApp.controller('CommentController', ['$scope', 'Comments',
        function ($scope, Comments) {
            $scope.title = "Old quickies";

            $scope.comment = {};
            $scope.submit = function () {
                $scope.comment.quickieId = $scope.fullQuickie.quickie.id;
                Comments.create({"quickieId": $scope.comment.quickieId}, $scope.comment,
                    function () {
                        $scope.fullQuickie.comments.push($scope.comment);
                        $scope.comment = {};
                    },
                    function (value, responseHeaders) {
                        alert('Error ' + value)
                    });

                console.log($scope.comment.comment);
            }
        }
    ]);
})();
