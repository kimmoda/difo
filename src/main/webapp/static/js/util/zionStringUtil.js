var zionStringUtils = {
    normalizeToIdString: function (content) {
        //trim input string and only keep alphanumeric characters, at last replace space to underscore
        return jQuery.trim(content.replace(/[^a-z0-9\ ]/gi, '')).replace(/\s+/g, '_');
    },

    isUrl: function (urlString) {
        var isHttp = urlString.match(/http:\/\/.+/);
        var isHttps = urlString.match(/https:\/\/.+/);
        return !(isHttp === null && isHttps === null);
    }
};
