(function () {
    'use strict';
    quickiesApp.factory('FutureQuickiesService', ['Quickies', function (Quickies) {
        return {
            page: {
                title: 'Future quickies',
                scoreTitle: 'votes'
            },
            top: function () {
                return Quickies.topFuture();
            },
            list: function () {
                return Quickies.future();
            }
        }
    }]);
    quickiesApp.factory('PastQuickiesService', ['Quickies', function (Quickies) {
        return {
            page: {
                title: 'Past quickies',
                scoreTitle: 'likes'
            },
            top: function () {
                return Quickies.topPast();
            },
            list: function () {
                return Quickies.past();
            }
        }
    }]);
    quickiesApp.controller('QuickiesController', ['$scope', 'Quickies', 'Votes', '$routeParams', '$location', 'quickieService', 'Comments', 'auth',
        function ($scope, Quickies, Votes, $routeParams, $location, quickieService, Comments, auth) {
            $scope.auth = auth;
            $scope.page = quickieService.page;
            $scope.topQuickies = quickieService.top();
            $scope.quickies = quickieService.list();

            $scope.submitComment = function (quickie) {
                var comment = new Comments({
                    quickieId: quickie.quickie.id,
                    comment: quickie.comment.comment
                });

                comment.$save({},
                    function () {
                        quickie.comment.comment = '';
                        var commentList = Comments.list({quickieId: quickie.quickie.id});
                        commentList.$promise.then(function () {
                            quickie.comments = commentList;
                        });
                    }
                );
            };
            $scope.thumbUp = function (quickie) {
                var vote = new Votes({
                    quickieId: quickie.quickie.id
                });

                vote.$save({}, function () {
                    quickie.scored = true;
                    quickie.score++;
                });
            };
            $scope.thumbDown = function (quickie) {
                var vote = new Votes({
                    quickieId: quickie.quickie.id
                });

                Votes.delete(vote, function () {
                    quickie.scored = false;
                    quickie.score--;
                });
            };
            $scope.toggleComment = function (quickie) {
                if (typeof quickie._view == 'undefined') {
                    quickie._view = {
                        comments: false
                    }
                }
                quickie._view.comments = !quickie._view.comments;
            };
        }
    ]);
})();