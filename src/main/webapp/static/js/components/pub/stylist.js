jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var service,
            contact,
            workCount = 0,
            personalInfoHtml,
            servicesHtml,
            reviewsHtml,
            contactHtml,
            nextPageToken,
            followLabel = "Follow",
            unFollowLabel = "Following",
            loadLooks = function () {
                var looksHtml = Handlebars.templates.looks();
                $('#stylist-content').append(looksHtml);
                layoutFeed({
                    userId: userID,
                    displayFeedShowMoreBtn: true,
                    afterLoginCallback: function () {
                        setHeader();
                    }
                });
            },
            loadReviews = function (pageToken, dest) {
                var rateUrl = webConfig.restUrl + '/rate/v1/pub/list',
                    data = {'resultSize': 50, 'destId': dest.id},
                    alreadyRated = dest.loggedInUserRate !== null;
                if (pageToken) {
                    data['nextPageToken'] = pageToken;
                }
                zionWebService.getPub(rateUrl, data, function (response) {
                    var html = Handlebars.templates.reviews({'alreadyRated': alreadyRated, 'reviews': response.data});
                    $('#stylist-content').append(html);
                    if (response.nextPageToken) {
                        nextPageToken = pageToken;
                    }
                });
            },
            setHeader = function () {
                menuBusiness.initMenus(function (menus) {
                    jQuery('#main-menu-container').empty().append(Handlebars.templates.pubHeaderMenus(menus));
                });
                zionWebService.getPub(getUserUrl, null, function (response) {
                    var user = response['data'],
                        userSocialSharedUrl,
                        userHeader = Handlebars.templates.userheader({
                            'avatar': user.person ? user.person.avatar : undefined,
                            'followText': user.loggedInUserFollowed ? unFollowLabel : followLabel,
                            'following': user.loggedInUserFollowed ? 'following' : '',
                            'notMine': !user.mine
                        });
                    $('#user-header').empty().append(userHeader);
                    userSocialSharedUrl = zionUrls.rest_user_social_share_urls.replace("{userId}", user.id);
                    zionWebService.getPub(userSocialSharedUrl, {}, function (response) {
                        jQuery('#zion-user-social-share-container').socialshare({shareUrlsConfig: response.data}, undefined);
                    });
                });
            },
            loadUser = function () {
                zionWebService.getPub(getUserUrl, null, function (response) {
                    var user = response['data'],
                        person = user.person,
                        personalInfo,
                        userSummaryHtml,
                        looksCount = '<div class="look-count">Looks' +
                            '(' + user.workCount + ')' +
                            '</div>';
                    setHeader();
                    $('#looks-count').append(looksCount);
                    userSummaryHtml = Handlebars.templates.usersummary({
                        'avatar': user.person ? user.person.avatar : undefined,
                        'displayName': user.displayName,
                        'location': user.location,
                        'feedCount': user.workCount,
                        'fansCount': user.fansCount,
                        'introduce': user.person.introduce,
                        'followText': user.loggedInUserFollowed ? unFollowLabel : followLabel,
                        'following': user.loggedInUserFollowed ? 'following' : '',
                        'notMine': !user.mine,
                        'userRoles': user.userRoles,
                        'phone': user.contact && user.contact.phone ? user.contact.phone : undefined,
                        'email': user.contact && user.contact.email ? user.contact.email : undefined,
                        'facebook': user.contact && user.contact.facebook ? user.contact.facebook : undefined,
                        'instagram': user.contact && user.contact.instagram ? user.contact.instagram : undefined,
                        'youtube': user.contact && user.contact.youtube ? user.contact.youtube : undefined,
                        'website': user.contact && user.contact.website ? user.contact.website : undefined
                    });
                    workCount = user.workCount;
                    $('#user-summary').append(userSummaryHtml);
                    $('#comments')
                        .initCommentsWithUserId(user.id)
                        .setAfterLoginCallBack(function () {
                            setHeader();
                        });
                    if (user.fansCount > 1) {
                        $('#like-content').text('Followers');
                    } else {
                        $('#like-content').text('Follower');
                    }
                    if (person) {
                        personalInfo = {
                            'introduction': person.introduce,
                            'experience': person.experience,
                            'award': person.award,
                            'certificate': person.certification,
                            'partnership': person.partnership,
                            'client': person.client
                        }
                    }
                    if (user.service && user.service.length > 0) {
                        if (personalInfo) {
                            personalInfo['service'] = user.service;
                        } else {
                            personalInfo = {
                                'service': user.service
                            }
                        }
                    }
                    if (user.contact) {
                        if (personalInfo) {
                            personalInfo['contact'] = user.contact;
                        } else {
                            personalInfo = {
                                'contact': user.contact
                            }
                        }
                    }
                    personalInfoHtml = Handlebars.templates.personalinfo(personalInfo);
                    if (user.contact && !$.isEmptyObject(user.contact)) {
                        contactHtml = Handlebars.templates.contact({contact: user.contact});
                    } else {
                        contactHtml = Handlebars.templates.contact();
                    }

                    $('#me-menu').on('click', function () {
                        $('#stylist-content').empty();
                        $(this).addClass('active').parents('li').siblings().find('a').removeClass('active');
                        $('#stylist-content').append(personalInfoHtml);
                    });

                    $('#contact-menu').on('click', function () {
                        $('#stylist-content').empty();
                        $(this).addClass('active').parents('li').siblings().find('a').removeClass('active');
                        $('#stylist-content').append(contactHtml);
                    });


                    $('#stylist-content').empty();
                    $('#me-menu').removeClass('hidden');
                    $('#contact-menu').removeClass('hidden');
                    if (workCount > 0) {
                        $('#looks-menu').removeClass('hidden');
                        loadLooks();
                    } else {
                        $('#me-menu').click();
                    }
                    $('#stylist-rating').rating('update', user.rate);
                    $('#rating-btn').on('click', function (e) {
                        e.preventDefault();
                        var comment = $('#stylist-review-comment').val(),
                            rate = $('#stylist-rating').rating().val(),
                            rateUrl = webConfig.restUrl + '/rate/v1/save';
                        if (rate >= 1) {
                            zionWebService.post(rateUrl, {
                                    'comment': comment,
                                    'destId': user.id,
                                    'rateDestination': 'USER',
                                    'rate': rate
                                },
                                function (response) {
                                    $('#zion-review-stylist-dialog').modal('hide');
                                    $('#stylist-content').empty();
                                    user['loggedInUserRate'] = response.data;
                                    loadReviews(nextPageToken, user);
                                });
                        } else {
                            $('#rating-error-banner').removeClass('hide');
                        }
                    });
                    $('#user-header')
                        .on('mouseenter', '#follow-user-button', function () {
                            if ($(this).hasClass('following')) {
                                $(this).text('Unfollow').addClass('unfollow');
                            }
                        })
                        .on('mouseleave', '#follow-user-button', function () {
                            if ($(this).hasClass('following')) {
                                $(this).text('Following').removeClass('unfollow');
                            }
                        })
                        .on('click', '#follow-user-button', $.debounce(1000, true, function (e) {
                            var follow = function (refreshPage) {
                                var url = webConfig.restUrl + '/follower/v1/follow';
                                zionWebService.post(url, user.id,
                                    function (response) {
                                        var fansCountTxt = $('#fans-count').text(),
                                            fansCount = parseInt(fansCountTxt),
                                            followed = response.data.followed,
                                            followedText;
                                        if (refreshPage === true) {
                                            if (response.status === 200) {
                                                window.location.replace(commonService.getZionHomeUrl() + '/action/stylist/view' + '?userId=' + response.data.followedUser.id);
                                            }
                                        } else {
                                            if (response.status === 200) {
                                                followedText = followed ? unFollowLabel : followLabel;
                                                if (followed) {
                                                    fansCount = fansCount + 1;
                                                    $('#follow-user-button').addClass('following').removeClass('unfollow');
                                                } else {
                                                    if (fansCount > 0) {
                                                        fansCount = fansCount - 1;
                                                    }
                                                    $('#follow-user-button').removeClass('following').removeClass('unfollow');
                                                }
                                                if (fansCount > 1) {
                                                    $('#like-content').text('Followers');
                                                } else {
                                                    $('#like-content').text('Follower');
                                                }
                                                $('#fans-count').text(fansCount);
                                                $('#follow-user-button').text(followedText);
                                            }
                                        }
                                    });
                            };
                            if (!commonService.isLoggedIn()) {
                                $(this).zionSocialLogin({
                                    loginSuccessCallback: function (loggedInUser) {
                                        if (loggedInUser.id !== user.id) {
                                            follow(true);
                                        } else {
                                            window.location.replace(commonService.getZionHomeUrl() + '/action/stylist/view' + '?userId=' + user.id);
                                        }
                                    }
                                }).show();
                            } else {
                                follow(false);
                            }
                        }));
                });
            };
        loadUser();

        $('#looks-menu').on('click', function () {
            $('#stylist-content').empty();
            $(this).addClass('active').parents('li').siblings().find('a').removeClass('active');
            loadLooks();
        });

        $('#user-header').on('click', '#share-user-button', function () {
            $('.user-share-container').toggleClass('show-share-container');
            $('.zion-stylist-content').toggleClass('lower-stylist-content');
        });

    });
})(jQuery);