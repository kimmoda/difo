(function ($) {
    $.fn.socialshare = function (options, callback) {

        var settings = $.extend({
            popup: true,
            fontawesome: true,
            shareUrlsConfig: []
        }, options);

        var channels = settings.shareUrlsConfig.sort(function (s1, s2) {
                return s1.order - s2.order
            }),
            pluginContainer = $(this);

        $.each(channels, function (index, channel) {
            if (channel.type !== 'SHORTLINK') {
                var socialContainer = $("<span>");
                var socialLinkAttributes = {
                    href: "javascript:void(0);",
                    'data-social-share-id': channel.type,
                    'data-social-share-link': channel.url
                };
                var socialLink = $("<a>", socialLinkAttributes);

                if (channel.fontIcon && channel.fontIcon !== '') {
                    var socialFontawesomeIcon = $("<i>", {class: channel.fontIcon});
                    socialLink.append(socialFontawesomeIcon);
                } else {
                    socialLink.text(channel.displayName);
                }

                socialContainer.append(socialLink);
                pluginContainer.append(socialContainer);

                $('a[data-social-share-id=' + channel.type + ']').on('click', $.debounce(1000, true, (function (e) {
                    // make sure the callback is a function
                    if (typeof callback == 'function') {
                        // brings the scope to the callback
                        callback($(this).attr('data-social-share-id'));
                    }
                    var shareUrl = $(this).attr('data-social-share-link');
                    if (settings.popup) {
                        popup(shareUrl, 500, 300);
                    } else {
                        $(this).attr("target", "_blank");
                        window.open(shareUrl);
                    }
                })));
            } else {
                var shortLinkContainerAttributes = {'class': 'short-link-container'};
                var shortLinkContainer = $("<div>", shortLinkContainerAttributes);

                var shortLinkInputBoxAttributes = {
                    'type': 'text',
                    'readonly': 'true',
                    'value': channel.url,
                    'class': 'copy-short-link-input'
                };
                var shortLinkInputBox = $('<input>', shortLinkInputBoxAttributes);
                shortLinkContainer.append(shortLinkInputBox);
                pluginContainer.append(shortLinkContainer);
                $('.copy-short-link-input')
                    .focus(function () {
                        var target = this;
                        setTimeout(function () {
                            target.setSelectionRange(0, 9999);
                        }, 100);
                    });
                if (webConfig.ua !== "SAFARI" && webConfig.ua !== "MOBILE_SAFARI") {

                    var copyActionContainerAttributes = {
                        'class': 'copy-short-link-url-action',
                        'data-social-share-id': channel.type
                    };
                    var copyActionContainer = $('<span>', copyActionContainerAttributes);
                    var copyActionIconAttributes = {'class': 'fa fa-clipboard ', 'aria-hidden': true};
                    var copyActionIcon = $('<i>', copyActionIconAttributes);
                    copyActionContainer.append(copyActionIcon);
                    shortLinkContainer.append(copyActionContainer);
                }


                shortLinkContainer.on('click', '.copy-short-link-url-action', $.debounce(1000, true, function (e) {
                    shortLinkInputBox.select();
                    document.execCommand("copy");
                    copyActionContainer.text('Copied');
                    if (typeof callback === 'function') {
                        callback($(this).attr('data-social-share-id'));
                    }
                }));
            }
        });

        return this;
    };

    var popup = function (url, width, height) {
        var left = $(window).width() / 2 - width / 2;
        var top = $(window).height() / 2 - height / 2;
        window.open(url, "", 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,width=' + width + ',height=' + height + ',left=' + left + ',top=' + top);
    };
}(jQuery));
