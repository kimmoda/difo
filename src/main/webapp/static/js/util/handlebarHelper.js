Handlebars.registerHelper('listRateStar', function (content, options) {
    var ret = '';
    for (var i = 1; i < 6; i++) {
        if (i <= content) {
            ret = ret + '<span class="fa fa-lg fa-star active"></span>';
        } else {
            ret = ret + '<span class="fa fa-lg fa-star"></span>';
        }
    }
    return ret;
});

Handlebars.registerHelper('contentFormat', function (content, displayType, options) {
    var urlRegex = new RegExp("^(http|https)://", "i"),
        tempContents = content.split(/(\s)/),
        maxLength = 160,
        totalNum = 0,
        firstContents = [],
        remainContents = [],
        firstText = '',
        remainText = '',
        fullContents = '',
        remainContainerClass = 'zion-more-content-link',
        remainContentsClass = 'zion-remain-content',
        moreButtonClass = 'zion-more-link',
        moreButtonHtml = '<span class="' + moreButtonClass + '">more</span>';
    for (var i = 0; i < tempContents.length; i++) {
        var component,
            urlTxt,
            safeUrl,
            safeUrlTxt;
        if (urlRegex.test(tempContents[i])) {
            if (String(tempContents[i]).length > 40) {
                urlTxt = tempContents[i].substr(0, 30) + '...';
            } else {
                urlTxt = tempContents[i];
            }
            safeUrl = Handlebars.Utils.escapeExpression(tempContents[i]);
            safeUrlTxt = Handlebars.Utils.escapeExpression(urlTxt);
            component = '<a href="' + safeUrl + '" target="_blank">' + safeUrlTxt + '</a>';
        } else {
            if (tempContents[i] === '\r\n' || tempContents[i] === '\r' || tempContents[i] === '\n') {
                component = '<br/>';
            } else {
                component = Handlebars.Utils.escapeExpression(tempContents[i]);
            }
        }

        if (totalNum < maxLength) {
            firstContents.push(component);
            totalNum = totalNum + tempContents[i].length;
        } else {
            remainContents.push(component);
        }
    }

    firstText = firstContents.join(' ');
    remainText = remainContents.join(' ');
    fullContents = firstText + remainText;

    if (displayType === true) {
        if (remainContents.length === 0) {
            return new Handlebars.SafeString(fullContents);
        } else {
            /*
            * control remain content display by add 'zion-show-more-content' class to 'zion-more-content-link' element.
            * */
            firstText = '<span>' + firstText + '</span>';
            remainText = '<span class="' + remainContainerClass + '">' + moreButtonHtml + '<span class="' + remainContentsClass + '">' + remainText + '</span>' + '</span>';
            return new Handlebars.SafeString(firstText + remainText);
        }
    } else {
        return new Handlebars.SafeString(fullContents);
    }
});

Handlebars.registerHelper('listUserRoles', function (content, options) {
    var trendsetterCssStyleMap = {
            'STYLIST': 'blue-lighter',
            'DESIGNER': 'blue-dark',
            'INFLUENCER': 'blue-light',
            'DEFAULT': 'grey-lighter'
        },
        cssClass = trendsetterCssStyleMap[content] ? trendsetterCssStyleMap[content] : 'grey-lighter',
        role = (content === 'DEFAULT') ? "STYLE SEEKER" : content;
    return '<span class="dot ' + cssClass + ' ">&#9679;</span><span class="role-content">' + role + '</span>';
});

Handlebars.registerHelper('ifEqual', function (content, equalsObj, options) {
    if (content === equalsObj) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});

Handlebars.registerHelper('dateTimeFormat', function (content, options) {
    var dateFormat = new Date(content),
        months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        year = dateFormat.getFullYear(),
        month = months[dateFormat.getMonth()],
        date = dateFormat.getDate(),
        s = ["th", "st", "nd", "rd"],
        v = date % 100,
        time = '';
    date = date + (s[(v - 20) % 10] || s[v] || s[0]);
    time = date + ' ' + month + ' ' + year;
    return time;
});

Handlebars.registerHelper("prettifyDate", function(timestamp) {
    return (new Date(timestamp)).format("yyyy-MM-dd");
});

Handlebars.registerHelper('subString', function(str, max) {
    var maxBoundary = max ? max : 256;
    if (str.length > maxBoundary) {
        return str.substring(0, maxBoundary) + '...';
    }else {
        return str;
    }
  });

Handlebars.registerHelper("emailHelper", function(email) {
    if (jQuery.type(email) === 'string' && email.length > 0) {
        return email;
    }else {
        return webConfig.appConfig.supportEmail;
    }
});