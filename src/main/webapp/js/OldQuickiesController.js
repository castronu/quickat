(function () {
    'use strict';
    quickiesApp.controller('OldQuickiesController', ['$scope', 'Quickies', 'Comments', 'Votes',
        function ($scope, Quickies, Comments, Votes) {
            $scope.title = "Old quickies";

            $scope.fullQuickies = Quickies.past();

            $scope.topQuickies = Quickies.topPast();

            $scope.like = function (fullQuickie) {
                var vote = new Votes({
                    quickieId: fullQuickie.quickie.id
                });

                vote.$save({}, function () {
                    fullQuickie.liked = true;
                    fullQuickie.likes++;
                });
            };

            $scope.dislike = function (fullQuickie) {
                var vote = new Votes({
                    quickieId: fullQuickie.quickie.id
                });

                Votes.delete(vote, function () {
                    fullQuickie.liked = false;
                    fullQuickie.likes--;
                });
            };

        }
    ]);
})();


