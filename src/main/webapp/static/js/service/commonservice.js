var commonService = {
    winScroll: function (callback) {
        jQuery(window).scroll(function () {
            var
                pageYOffset = jQuery(window).scrollTop(),
                documentHeight = document.body.scrollHeight,
                windowHeight = document.documentElement.clientHeight;
            if (pageYOffset + windowHeight > documentHeight - 160) {
                callback();
            }
        });
    },

    setJwtToken: function (token) {
        localStorage.setItem('Zion-Jwt-Client-Token', token);
    },

    getJwtToken: function () {
        return localStorage.getItem('Zion-Jwt-Client-Token');
    },

    saveUserInfo: function (userInfo) {
        sessionStorage.setItem('zion-user-info', JSON.stringify(userInfo));
    },
    getUserInfo: function () {
        return JSON.parse(sessionStorage.getItem('zion-user-info') ? sessionStorage.getItem('zion-user-info') : "{}");
    },
    setFeedFilterTags: function (tags) {
        var normalizedTags = tags ? tags : [],
            tagsString = JSON.stringify(normalizedTags);
        localStorage.setItem('Zion-Feed-Filter-Tags', tagsString);
    },

    getFeedFilterTags: function () {
        var tags = localStorage.getItem('Zion-Feed-Filter-Tags'),
            tagList = tags ? JSON.parse(tags) : undefined;
        //We need to check tag type as pre version of STYLEHUB 2.1.0 only store string type for tag.
        //To avoid conflict with old version storage, clear everything.
        if (tagList && jQuery.type(tagList[0]) === 'string') {
            commonService.removeAllFeedFilterTag();
        }
        return tags ? tagList : [];
    },

    addFeedFilterTag: function (tag) {
        var tags = commonService.getFeedFilterTags(),
            hasSameTag = false;
        if (tag) {
            for (var i = 0; i < tags.length; i++) {
                if (tags[i].code === tag.code) {
                    hasSameTag = true;
                }
            }
            if (!hasSameTag) {
                tags.push(tag);
                commonService.setFeedFilterTags(tags);
            }
        }
    },
    removeAllFeedFilterTag: function () {
        localStorage.removeItem('Zion-Feed-Filter-Tags');
    },

    removeFeedFilterTag: function (tagCode) {
        var tags = commonService.getFeedFilterTags();
        if (tagCode) {
            for (var i = 0; i < tags.length; i++) {
                if (tags[i].code === tagCode) {
                    tags.splice(i, 1);
                    commonService.setFeedFilterTags(tags);
                }
            }
        }
    },

    setUserRoleTags: function (role) {
        var normalizedRoles = role ? role : [],
            rolesString = JSON.stringify(normalizedRoles);
        localStorage.setItem('Zion-User-roles-Filter-Tags', rolesString);
    },

    removeUserRoleTag: function (roleCode) {
        var roles = commonService.getUserRolesTags();
        if (roleCode) {
            for (var i = 0; i < roles.length; i++) {
                if (roles[i].code === roleCode) {
                    roles.splice(i, 1);
                    commonService.setUserRoleTags(roles);
                }
            }
        }
    },

    removeAllUserRoleTag: function () {
        localStorage.removeItem('Zion-User-roles-Filter-Tags');
    },

    getUserRolesTags: function () {
        var tags = localStorage.getItem('Zion-User-roles-Filter-Tags'),
            tagList = tags ? JSON.parse(tags) : undefined;
        //We need to check tag type as pre version of STYLEHUB 2.1.0 only store string type for tag.
        //To avoid conflict with old version storage, clear everything.
        if (tagList && jQuery.type(tagList[0]) === 'string') {
            commonService.removeAllUserRoleTag();
        }
        return tags ? tagList : [];
    },

    addUserRolesTag: function (role) {
        var roles = commonService.getUserRolesTags(),
            hasSameRole = false;
        if (role) {
            for (var i = 0; i < roles.length; i++) {
                if (roles[i].code === role.code) {
                    hasSameRole = true;
                }
            }
            if (!hasSameRole) {
                roles.push(role);
                commonService.setUserRoleTags(roles);
            }
        }
    },

    setTrendsetterFilterRoles: function (roles) {
        var normalizedRoles = roles ? roles : [],
            rolesString = JSON.stringify(normalizedRoles);
        localStorage.setItem('Zion-Trendsetter-Roles', rolesString);
    },

    getTrendsetterFilterRoles: function () {
        var roles = localStorage.getItem('Zion-Trendsetter-Roles');
        return roles ? JSON.parse(roles) : [];
    },

    addTrendsetterFilterRole: function (role) {
        var roles = commonService.getTrendsetterFilterRoles(),
            index;
        if (role) {
            index = jQuery.inArray(role, roles);
            if (index === -1) {
                roles.push(role);
                commonService.setTrendsetterFilterRoles(roles);
            }
        }
    },

    removeTrendsetterFilterRole: function (role) {
        var roles = commonService.getTrendsetterFilterRoles(),
            index;
        if (role) {
            index = jQuery.inArray(role, roles);
            if (index > -1) {
                roles.splice(index, 1);
                commonService.setTrendsetterFilterRoles(roles);
            }
        }
    },

    removeAllTrendsetterFilterRole: function () {
        localStorage.removeItem('Zion-Trendsetter-Roles');
    },

    setTrendsetterSelectedCountry: function (country) {
        if (country) {
            var countryStr = JSON.stringify(country);
            localStorage.setItem('Zion-Trendsetter-Country', countryStr);
        }
    },

    getTrendsetterSelectedCountry: function () {
        var countryStr = localStorage.getItem('Zion-Trendsetter-Country');
        return countryStr ? JSON.parse(countryStr) : {};
    },

    removeTrendsetterSelectedCountry: function () {
        localStorage.removeItem('Zion-Trendsetter-Country');
    },

    isLoggedIn: function () {
        return commonService.getJwtToken() && commonService.getJwtToken().length > 0;
    },

    logout: function () {
        localStorage.removeItem('Zion-Jwt-Client-Token');
        localStorage.removeItem('Zion-Feed-Filter-Tags');
        localStorage.removeItem('Zion-Trendsetter-Roles');
        sessionStorage.removeItem('zion-user-info');
        window.location.href = this.getZionHomeUrl();
    },

    getZionHomeUrl: function () {
        if (webConfig && webConfig.hostDomain) {
            return webConfig.hostDomain + webConfig.contextPath;
        }
    },

    getZionActionUrl: function (path) {
        if (webConfig && webConfig.actionUrl) {
            return webConfig.actionUrl + path;
        }
    },

    getZionErrorUrl: function () {
        if (webConfig && webConfig.actionUrl) {
            return webConfig.actionUrl + "/error";
        }
    },

    getZionRestUrl: function (path) {
        if (webConfig && webConfig.actionUrl) {
            return webConfig.restUrl + path;
        }
    },
    isString: function (str) {
        return !!(str && (typeof str === 'string') && str.constructor === String);
    },
    isEmpty: function (object) {
        if (!object) {
            return true;
        }

        //string
        if (this.isString(object)) {
            return jQuery.trim(object).length === 0;
        }

        //object
        if (object && typeof object === 'object') {
            return jQuery.isEmptyObject(object);
        }
    },
    allEmpty: function () {
        var empty = true;
        var self = this;
        jQuery.each(arguments, function (index, value) {
            if (!(self.isEmpty(value))) {
                empty = false;
            }
        });
        return empty;
    },
    getFileSize: function (obj) {
        var objValue = obj.value;
        if (objValue === "") return false;

        var fileLenth = -1;
        try {
            var fso = new ActiveXObject("Scripting.FileSystemObject");
            fileLenth = parseInt(fso.getFile(objValue).size);
        } catch (e) {
            try {
                fileLenth = parseInt(obj.files[0].size);
            } catch (e) {
                fileLenth = -1;

            }
        }
        return fileLenth;
    },
    merge: function () {
        return Array.prototype.concat.apply([], arguments)
    },
    removeObjectFromArray: function (myObjects, prop, value) {
        return myObjects.filter(function (val) {
            return val[prop] !== value + "";
        });
    },
    /**
     * Get the Youtube Video id.
     * @param {string} str - the url from which you want to extract the id
     * @returns {string|undefined}
     */
    getYoutubeIdFromUrl: function (str) {
        // shortcode
        var shortcode = /youtube:\/\/|https?:\/\/youtu\.be\//g;

        if (shortcode.test(str)) {
            var shortcodeid = str.split(shortcode)[1];
            return _stripParameters(shortcodeid);
        }

        // /v/ or /vi/
        var inlinev = /\/v\/|\/vi\//g;

        if (inlinev.test(str)) {
            var inlineid = str.split(inlinev)[1];
            return _stripParameters(inlineid);
        }

        // v= or vi=
        var parameterv = /v=|vi=/g;

        if (parameterv.test(str)) {
            var arr = str.split(parameterv);
            return arr[1].split('&')[0];
        }

        // v= or vi=
        var parameterwebp = /\/an_webp\//g;

        if (parameterwebp.test(str)) {
            var webp = str.split(parameterwebp)[1];
            return _stripParameters(webp);
        }

        // embed
        var embedreg = /\/embed\//g;

        if (embedreg.test(str)) {
            var embedid = str.split(embedreg)[1];
            return _stripParameters(embedid);
        }

        // user
        var userreg = /\/user\//g;

        if (userreg.test(str)) {
            var elements = str.split('/');
            return _stripParameters(elements.pop());
        }

        // attribution_link
        var attrreg = /\/attribution_link\?.*v%3D([^%&]*)(%26|&|$)/;

        if (attrreg.test(str)) {
            return str.match(attrreg)[1];
        }
    },
    guid: function () {
        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000)
                .toString(16)
                .substring(1);
        }

        return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
            s4() + '-' + s4() + s4() + s4();
    }
};

function _stripParameters(str) {
    // Split parameters or split folder separator
    if (str.indexOf('?') > -1) {
        return str.split('?')[0];
    } else if (str.indexOf('/') > -1) {
        return str.split('/')[0];
    }
    return str;
}

jQuery.fn.extend({
    animateCss: function (animationName, afterAnimate) {
        var animationEnd = 'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend';
        this.addClass('animated ' + animationName).one(animationEnd, function () {
            jQuery(this).removeClass('animated ' + animationName);
            if (afterAnimate && jQuery.isFunction(afterAnimate)) {
                afterAnimate();
            }
        });
        return this;
    }
});

// Overwrite the hideModal function in bootstrap,
// Remove 'modal-open' class when there are no feed modal view in the window
jQuery.fn.modal.Constructor.prototype.hideModal = function () {
    var that = this;
    this.$element.hide();
    this.backdrop(function () {
        var feedPopupModalClosed = jQuery('.zion-feed-modal.in').length === 0;
        if (feedPopupModalClosed) {
            //the page can scrolling after remove modal-open
            that.$body.removeClass('modal-open');
        }
        that.resetAdjustments();
        that.resetScrollbar();
        that.$element.trigger('hidden.bs.modal');
    })
};