jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var postUrl = "https://trending.stylehub.online/wp-json/wp/v2/posts",
            articleUrl = "https://trending.stylehub.online/wp-json/wp/v2/posts/{articleId}",
            mediaUrl = "https://trending.stylehub.online/wp-json/wp/v2/media/{mediaId}",
            authorUrl = "https://trending.stylehub.online/wp-json/wp/v2/users/{usersId}",
            currentArticleId,
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
            getArticleData = function () {
                var articlePath = articleUrl.replace("{articleId}", articleId);
                get(articlePath, null, function (articleResp) {
                    currentArticleId = articleResp.id;
                    getRecommend();
                    $('.title').text(articleResp.title.rendered);
                    $('.article-content').html(articleResp.content.rendered);
                    var authorPath = authorUrl.replace("{usersId}", articleResp.author);
                    var mediaPath = mediaUrl.replace("{mediaId}", articleResp.featured_media);
                    get(authorPath, null, function (authorResp) {
                        $('.author').text(authorResp.name);
                        $('.date').text(new Date(articleResp.date_gmt).toLocaleDateString());
                    });
                    get(mediaPath, null, function (mediaResp) {
                        $('.cover-image').attr("src", mediaResp.source_url);
                    })
                })
            },
            getRecommend = function () {
                var param = {
                    per_page: 10,
                    order: "desc",
                    orderby: "date",
                    status: "publish"
                };
                get(postUrl, param, function (resp) {
                    if (resp && resp.length > 0) {
                        $.each(resp, function (index, value) {
                            var authorPath = authorUrl.replace("{usersId}", value.author);
                            if (currentArticleId && currentArticleId === value.id) {
                                return;
                            }
                            get(authorPath, null, function (authorInfo) {
                                var data = {
                                        title: value.title.rendered,
                                        content: value.excerpt.rendered,
                                        id: value.id,
                                        date: new Date(value.date_gmt).toLocaleDateString(),
                                        author: authorInfo.name
                                    },
                                    template = Handlebars.templates.postslistitem(data);
                                $('.recommend-area').append(template);
                                if (value.featured_media > 0) {
                                    var mediaPath = mediaUrl.replace("{mediaId}", value.featured_media);
                                    get(mediaPath, null, function (resp) {
                                        $("#recommend-cover-" + value.id).attr("src", resp.source_url);
                                    })
                                }
                            });
                        });
                    }
                })
            },
            init = function () {
                getArticleData();
            };
        init();
        $('.recommend-area').on('click', ".recommend-item", function () {
            var id = $(this).data("id");
            window.location.href = commonService.getZionActionUrl("/article?articleId=") + id;
        })

    });
})(jQuery);
