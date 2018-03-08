(function ($) {
    $.fn.initCommentsWithUserId = function (userId) {

        if (!userId) {
            return;
        }
        var afterLoginCallback = null,
            recaptcha = recaptchaService(),
            commentHtml = Handlebars.templates.commentsProfile(),
            nextPageToken = null,
            fields = [
                {
                    'target': '.comment-input',
                    'error': '#comment-error',
                    'minLength': 5,
                    'maxLength': 200
                }
            ],
            validationService = zionValidationService(),
            postComment = function (feedId, content) {
                zionLoadingService().show('.profile-comments-container');
                zionCommentsService("USER").post(feedId, content, function (resp) {
                    zionLoadingService().stop('.profile-comments-container');
                    jQuery('.comment-input').val("");
                    var commentsHtml = Handlebars.templates.comments({data: [resp]});
                    jQuery('#user-comments').prepend(commentsHtml);
                })
            },
            loadComments = function () {
                zionCommentsService('USER').get(userId, nextPageToken, function (resp) {
                    var html = Handlebars.templates.comments(resp);
                    $('#user-comments').append(html);
                    if (resp.nextPageToken) {
                        nextPageToken = resp.nextPageToken;
                        jQuery("#load-more-button-container").removeClass('hidden');
                    } else {
                        jQuery("#load-more-button-container").addClass('hidden');
                    }
                    recaptcha.success(function () {
                        var content = jQuery.trim(jQuery('.comment-input').val());
                        postComment(userId, content);
                    });
                }, 20);
            },
            setAfterLogin = function (callback) {
                afterLoginCallback = callback
            };
        $(this).empty().append(commentHtml);
        validationService.registerFieldEvents(fields);
        loadComments();

        $(this).on('click', '.profile-comments-submit', jQuery.debounce(1000, true, function () {
            if (commonService.getJwtToken()) {
                if (validationService.validFields(fields)) {
                    recaptcha.fire();
                }
            } else {
                $(this).zionSocialLogin({
                    loginSuccessCallback:function () {
                        if (validationService.validFields(fields)) {
                            recaptcha.fire();
                        }
                        if (afterLoginCallback && jQuery.isFunction(afterLoginCallback)) {
                            afterLoginCallback();
                        }
                    }
                }).show();
            }
        }));

        $(this).on('click', '#load-more-button', jQuery.debounce(1000, true, function () {
            loadComments();
        }));
        return {
            setAfterLoginCallBack: setAfterLogin
        };

    }
}(jQuery));
