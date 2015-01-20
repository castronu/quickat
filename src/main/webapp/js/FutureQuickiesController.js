(function () {
    'use strict';
    quickiesApp.controller('FutureQuickiesController', ['$scope', 'Quickies', 'Votes',
        function ($scope, Quickies, Votes) {
            $scope.title = "Future Quickies";

            $scope.quickies = Quickies.future();

            $scope.message = {
                text: 'hello world!',
                time: new Date()
            };

            $scope.topQuickies = Quickies.topActive();

            $scope.vote = function (quickie) {
                var vote = new Votes({
                    quickieId: quickie.quickie.id
                });

                vote.$save({}, function () {
                    quickie.voted = true;
                    quickie.votes++;
                });
            };

            $scope.unvote = function (quickie) {
                var vote = new Votes({
                    quickieId: quickie.quickie.id
                });

                Votes.delete(vote, function () {
                    quickie.voted = false;
                    quickie.votes--;
                });
            };

            $scope.toggleComment = function (quickie) {
                if (typeof quickie._view == 'undefined') {
                    quickie._view = {
                        comments: false
                    }
                }
                quickie._view.comments = !quickie._view.comments;
            }
        }
    ]);
})();


