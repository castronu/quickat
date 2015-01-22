(function () {
    'use strict';

    quickiesApp.filter("url", function () {
        return function (link) {
            if (typeof link == 'undefined' || link == '' || link == null) {
                return '#';
            }
            var result;
            var startingUrl = "http://";

            if (!link.startWith("http://") && !link.startWith("https://")) {
                return startingUrl + link;
            }

            return link;
        }
    });
    String.prototype.startWith = function (str) {
        return this.indexOf(str) == 0;
    };
})();