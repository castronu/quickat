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
            list: function (groups) {
                return Quickies.future({groups: groups});
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
            list: function (groups) {
                return Quickies.past({groups: groups});
            }
        }
    }]);
    quickiesApp.controller('QuickiesController', ['$scope', 'Quickies', 'Votes', '$routeParams', '$location', 'quickieService', 'Comments', 'auth', 'UserGroups',
        function ($scope, Quickies, Votes, $routeParams, $location, quickieService, Comments, auth, UserGroups) {
            $scope.auth = auth;
            $scope.page = quickieService.page;
            $scope.topQuickies = quickieService.top();
            $scope.quickies = quickieService.list($location.search().filter);
            $scope.groups = UserGroups.list();

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
            $scope.selectFilter = function (group) {
                group = '' + group;

                var selectedGroups = selectedFilters();
                var index = selectedGroups.indexOf(group);

                if (index > -1) {
                    selectedGroups.splice(index, 1);
                } else {
                    selectedGroups.push(group);
                }

                if (selectedGroups.length > 0) {
                    $location.search({filter: selectedGroups.join(',')});
                } else {
                    $location.search({});
                }
            };
            $scope.deselectFilters = function () {
                $location.search({});
            };
            $scope.filterSelected = function (group) {
                var selectedGroups = selectedFilters();
                return selectedGroups.indexOf('' + group) > -1;
            };

            function selectedFilters() {
                var filter = $location.search().filter;

                if (typeof filter == 'undefined') {
                    filter = '';
                }

                var selectedGroups = [];
                if (filter != '') {
                    selectedGroups = filter.split(',');
                }

                return selectedGroups;
            }
        }
    ]);
})();