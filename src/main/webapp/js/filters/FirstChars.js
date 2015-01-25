(function () {
    'use strict';
    quickiesApp.filter("firstChars", function () {
        return function (data) {
            if (!data) return data;
            return data.substring(0, 60) + " ...";
        };
    })
})();