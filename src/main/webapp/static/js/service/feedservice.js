var zionFeedService = function (data) {
    var config = {
            showViewButton: true,
            modalId: '#myModal',
            feedListId: '#feed-list',
            commentInputId: '#comments-input',
            commentsContainerId: '#comment-container',
            commentContainerId: '#comments',
            commentErrorMessage: '#comment-error',
            sendCommentBtnId: '#comment-send-button',
            loadMoreBtnContainerId: '#load-comment-more-button-container',
            loadMoreBtnId: '#load-more-button',
            likeBtnId: '#like-it-button',
            shareContainer: '.share-container',
            commentSendContainer: '.comment-send-container',
            signInPrompt: '.signin-prompt',
            description: '.description',
            moreContent: '.zion-more-content-link',
            showMore: 'zion-show-more-content',
            scrollContent: '#scroll-content',
            leftImage: '.annotation-container'
        },
        likeButtonCallback,
        hideModalCallback,
        showAllDesc = true,
        fullDesc,
        nextpage = null,
        feedInfo,
        showAll = true,
        getImageUrl = function (imageUrl) {
            var photo_url = "",
                url_split;
            if (imageUrl) {
                url_split = imageUrl.split("/");
                for (var i = 0; i < url_split.length - 2; i++) {
                    photo_url += url_split[i] + "/";
                }
                photo_url += url_split[url_split.length - 1];
            }
            return photo_url;
        },
        showModal = function (feed) {
            feedInfo = feed;
            initModal(feed);
            if (config.showViewButton === false) {
                jQuery('.contact-me-button').addClass('hidden');
            }

        },
        validateComment = function (submit) {
            var isValid = true,
                targetInput = jQuery(config.commentInputId),
                content = jQuery.trim(targetInput.val()),
                errorField = jQuery(config.commentErrorMessage);
            errorField.text('');
            if (content) {
                if (content.length < 200) {
                    targetInput.removeClass('zion-input-error-indicator');
                    errorField.addClass('zion-hide');
                    isValid = true
                } else {
                    targetInput.addClass('zion-input-error-indicator');
                    errorField.removeClass('zion-hide');
                    errorField.text('Please provide your comments less than 200 characters.');
                    isValid = false;
                }
            } else {
                if (submit === false) {
                    targetInput.removeClass('zion-input-error-indicator');
                    errorField.addClass('zion-hide');
                    isValid = true
                } else {
                    targetInput.addClass('zion-input-error-indicator');
                    errorField.removeClass('zion-hide');
                    errorField.text('Please write a comment.');
                    isValid = false;
                }
            }
            return isValid;
        },
        initModal = function (feed) {
            nextpage = null;
            fullDesc = feed.content ? feed.content : "";

            var postCommentHandler = function () {
                var content = jQuery.trim(jQuery(config.commentInputId).val());
                if (commonService.getJwtToken()) {
                    if (validateComment(true) === true) {
                        postComment(feed.id, content);
                        zionGAEventService('feed', 'comment', 'posted').track();
                    }
                } else {
                    zionGAEventService('feed', 'comment', 'notsignedin').track();
                    jQuery(this).zionSocialLogin({
                        loginSuccessCallback: function () {
                            if (validateComment(true) === true) {
                                postComment(feed.id, content);
                                zionGAEventService('feed', 'comment', 'posted').track();
                            }
                        }
                    }).show();
                }
            };
            if (feed.coverImage && feed.coverImage.description) {
                feed.coverImage.description = filterXSS(feed.coverImage.description);
            }
            if (feed.mediaContent) {
                for (var i = 0; i < feed.mediaContent.length; i++) {
                    feed.mediaContent[i].description = filterXSS(feed.mediaContent[i].description);
                }
            }


            var modal = Handlebars.templates.annotation(feed);
            jQuery(config.modalId).empty().append(modal).modal({});

            if (feed.id) {
                var feedSocialSharedUrl = zionUrls.rest_feed_social_share_urls.replace("{feedId}", feed.id);
                zionWebService.getPub(feedSocialSharedUrl, {}, function (response) {
                    jQuery('#zion-social-share-container').socialshare({shareUrlsConfig: response.data}, function (socialShareId) {
                        zionGAEventService('feed', 'socialshare', socialShareId).track();
                        zionWebService.postPub(zionUrls.rest_feed_share_stats, {
                            "feedId": feed.id,
                            "source": socialShareId
                        });
                    });
                });
            }

            if (isIE()) {
                jQuery(config.scrollContent).addClass('ie');
            }

            jQuery(config.moreContent).on('click', function () {
                jQuery(this).addClass(config.showMore);
            });
            if (feed.id) {
                getCommentsByFeedId(feed.id);
            }

            autosize(jQuery('.comment-input'));
            // jQuery(config.sendCommentBtnId).on('click', postCommentHandler);

            jQuery(config.sendCommentBtnId).on('click', jQuery.debounce(1000, true, postCommentHandler));

            //Do not support the key enter to post comment. Otherwise we need to support debounce for it.
            // jQuery(config.commentInputId).keypress(function (e) {
            //     if (e.which === 13) {
            //         //prevent enter key submit the form.
            //         e.preventDefault();
            //         return false;
            //     }
            // });

            jQuery(config.commentInputId).on('change keydown paste', function (e) {
                validateComment(false);
            });

            jQuery(config.loadMoreBtnId).on('click', function () {
                getCommentsByFeedId(feed.id);
            });

            jQuery('.zion-modal-feed-like-container').zionFeedLikePlugin({
                id: feed.id,
                likeCount: feed.likeCount,
                like: feed.like
            });

            jQuery('#myModal').zionFeedAnnotation().getAnnotation(feed.annotationData);

            if (feed.like) {
                jQuery('#like-it-button').find('i').addClass('like');
            } else {
                jQuery('#like-it-button').find('i').addClass('dislike');
            }

            jQuery('.user-profile-link').on('click', function (e) {
                e.preventDefault();
                var userId = feed.author.userId;
                if (userId) {
                    window.location.href = zionUrls.action_stylist_view + '?userId=' + userId;
                }
            });
        },
        getfeedById = function (url) {
            if (url) {
                zionLoadingService().show('body');
                zionWebService.getPub(url, null, function (e) {
                    zionLoadingService().stop('body');
                    if (e.status === 200) {
                        var feed = e.data;
                        if (feed) {
                            if (feed.coverImage && feed.coverImage.url) {
                                feed.coverImage.url = getImageUrl(feed.coverImage.url);
                            }
                            feed.likeIcon = feed.like ? 'fa-heart' : 'fa-heart-o';
                            showModal(feed)
                        }
                    }
                });
            }
        },
        getCommentsByFeedId = function (feedId) {
            zionLoadingService().show(config.commentContainerId);
            zionCommentsService().get(feedId, nextpage, function (resp) {
                zionLoadingService().stop(config.commentContainerId);
                var commentsHtml = Handlebars.templates.comments(resp);
                jQuery(config.commentContainerId).append(commentsHtml);
                if (resp.nextPageToken) {
                    nextpage = resp.nextPageToken;
                    jQuery(config.loadMoreBtnContainerId).removeClass('hidden');
                } else {
                    jQuery(config.loadMoreBtnContainerId).addClass('hidden');
                }
            })
        },
        postComment = function (feedId, content) {
            zionLoadingService().show(config.commentContainerId);
            zionCommentsService().post(feedId, content, function (resp) {
                zionLoadingService().stop(config.commentContainerId);
                jQuery(config.commentInputId).val("");
                var commentsHtml = Handlebars.templates.comments({data: [resp]});
                jQuery(config.commentContainerId).prepend(commentsHtml);
                jQuery(config.commentsContainerId).scrollTop(0);
            })
        },
        likeFeed = function (feedId, currentNode) {
            if (currentNode) {
                currentNode.toggleClass('fa-heart fa-heart-o');
                currentNode.toggleClass('like dislike');
            }
            zionWebService.post(webConfig.restUrl + '/userfeed/v1/like', {
                'feedId': feedId,
                'status': 'LIKE'
            }, function (e) {
                if (feedInfo) {
                    feedInfo.like = !!e.data.likeStatus;
                }
                if (likeButtonCallback && jQuery.isFunction(likeButtonCallback)) {
                    likeButtonCallback(feedId, !!e.data.likeStatus);
                }
            });
        },
        setLikeButtonCallback = function (callback) {
            likeButtonCallback = callback;
        },
        setHideModalCallback = function (callback) {
            hideModalCallback = callback;
        },
        showModalByFeedInfo = function (feedInfo) {
            if (feedInfo) {
                var feed = jQuery.extend(true, {}, feedInfo);
                if (feed.coverImage && feed.coverImage.url) {
                    feed.coverImage.url = getImageUrl(feedInfo.coverImage.url);
                }
                feed.likeIcon = feedInfo.like ? 'fa-heart' : 'fa-heart-o';
                showModal(feed)

            }
        };

    if (data) {
        jQuery.extend(config, data);
    }

    jQuery(config.feedListId).on('click', '.zion-like-feed', function (e) {
        e.preventDefault();
        var feedId = jQuery(this).data('feedid'),
            node = jQuery(this);
        likeFeed(feedId, node);
    });

    jQuery(config.feedListId).on('click', '.zion-feed-tag', function () {
        zionFeedTagUtils.listFeedsByTag(jQuery(this));
    });

    jQuery(config.modalId).on('click', '.zion-feed-tag', function () {
        zionFeedTagUtils.listFeedsByTag(jQuery(this));
    });

    jQuery(config.modalId).on('click', '#share-feed-button', function () {
        jQuery('.feed-share-container').toggleClass('show-feed-share-container');
    });

    jQuery(config.modalId).on('hidden.bs.modal', function (e) {
        if (hideModalCallback && jQuery.isFunction(hideModalCallback)) {
            hideModalCallback(feedInfo);
        }
    });

    jQuery('#myModal').on('shown.bs.modal', function () {
        jQuery(this).zionFeedAnnotation().showAllAnnotation();
        jQuery(this).zionFeedAnnotation().addShowAllListener();
    });



    return {
        getfeedById: getfeedById,
        getImageUrl: getImageUrl,
        likeFeed: likeFeed,
        likeBtnCallBack: setLikeButtonCallback,
        hideModalCallback: setHideModalCallback,
        showModalByFeedinfo: showModalByFeedInfo
    }
};