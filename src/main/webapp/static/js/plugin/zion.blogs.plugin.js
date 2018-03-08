(function ($) {
    var page = 1,
        loading = false,
        hasNextPage = true,
        target,
        defaultOptions = {
            postsUrl: "https://trending.stylehub.online/wp-json/wp/v2/posts",
            mediaUrl: "https://trending.stylehub.online/wp-json/wp/v2/media/{mediaId}",
            authorUrl: "https://trending.stylehub.online/wp-json/wp/v2/users/{usersId}",
            per_page: 10,
            order: "desc",
            orderby: "date",
            status: "publish",
            afterClicked: function () {
            }
        },
        opts,
        listRequestParams,
        tpl = $('[zion-blogs-item-template]').clone(),
        get = function (url, parameters, success) {
            jQuery.ajax({
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: parameters ? parameters : "",
                type: "GET",
                url: url,
                cache: true,
                success: function (response) {
                    if (success && jQuery.isFunction(success)) {
                        success(response);
                    }
                }
            });
        },
        tplHandler = function (data, tpl) {
            if (typeof(data) === 'object') {
                for (var i in data) {
                    var content = data[i];
                    var pattern = new RegExp("\\{\\[" + i + "\\]\\}", "g");
                    tpl = tpl.replace(pattern, filterXSS(content));
                }
            }
            return tpl;
        },
        loadData = function () {
            loading = true;
            get(opts.postsUrl, listRequestParams, function (resp) {
                if (resp && resp.length > 0) {
                    if (resp.length === defaultOptions.per_page) {
                        page = page + 1;
                        listRequestParams.page = page;
                    } else {
                        hasNextPage = false;
                    }
                    var counter = 0,
                        itemCount = resp.length;
                    $.each(resp, function (index, value) {
                        var authorUrl = opts.authorUrl.replace("{usersId}", value.author);
                        get(authorUrl, null, function (authorInfo) {
                            var data = {
                                    title: value.title.rendered,
                                    content: value.excerpt.rendered,
                                    id: value.id,
                                    date: new Date(value.date_gmt).toLocaleDateString(),
                                    author: authorInfo.name
                                },
                                template = tplHandler(data, tpl.html());
                            if (value.featured_media > 0) {
                                var mediaUrl = opts.mediaUrl.replace("{mediaId}", value.featured_media);
                                get(mediaUrl, null, function (resp) {
                                    counter += 1;
                                    if (counter === itemCount) {
                                        loading = false;
                                    }
                                    $("#cover-" + value.id).attr("src", resp.source_url);
                                })
                            }
                            target.append(template);
                        });
                    });
                } else {
                    hasNextPage = false;
                }
            });
        };

    $.fn.zionblogs = function (options) {
        opts = $.extend({}, defaultOptions, options);
        listRequestParams = {
            page: page,
            per_page: opts.per_page,
            order: opts.order,
            orderby: opts.orderby,
            status: opts.status
        };
        target = this;
        loadData();
        this.on("click", ".article-card", function () {
            var articleId = $(this).data("id");
            opts.afterClicked(articleId);
        });

        $(window).scroll(function () {
            var pageYOffset = $(window).scrollTop(),
                documentHeight = document.body.scrollHeight,
                windowHeight = document.documentElement.clientHeight;
            if (hasNextPage && pageYOffset + windowHeight > documentHeight - 360 && target && $(target).children.length > 0 && !loading) {
                loadData();
            }
        });
    }
}(jQuery));
