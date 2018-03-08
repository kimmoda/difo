jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var isLoading = false,
            loading = $('.zion-loading').zionLoading(),
            nextPageToken = null,
            $grid = zionImageReveal.init(),
            feedService = zionFeedService(),
            loadTags = function () {
                zionWebService.getPub(zionUrls.rest_getAllTags, null, function (resp) {
                    var data = resp.data,
                        filterHtml,
                        tags = commonService.getFeedFilterTags();
                    for (var i = 0; i < tags.length; i++) {
                        var tag = '<li class="tag" data-tag-name="' + tags[i].name + '" data-tag-code="' + tags[i].code + '">' +
                            '<span class="content">#' + tags[i].name + '</span>' +
                            '<span class="filter-delete-btn">&#10005;</span>' +
                            '</li>';
                        $('#tags-area').append(tag);
                    }
                    filterHtml = Handlebars.templates.inspirationFilter({
                        'tags': [{
                            name: "STYLES",
                            tags: data
                        }]
                    });
                    $('#tag-filter-container').append(filterHtml);
                    toggleTagScrollBtn();
                })
            },
            setFilterNum = function () {
                var tagNumber = commonService.getFeedFilterTags().length,
                    roleNumber = commonService.getUserRolesTags().length,
                    totalNumber = tagNumber + roleNumber;
                if (totalNumber > 0) {
                    $('.filter-num').text(totalNumber);
                    $('.filter-num').addClass('filter-num-show');
                } else {
                    $('.filter-num').removeClass('filter-num-show');
                }
            },
            setFilterStatus = function () {
                if ($('#tag-filter-container').hasClass('zion-closed')) {
                    var tags = commonService.getFeedFilterTags(),
                        roles = commonService.getUserRolesTags();
                    $('#tag-filter-container').removeClass('zion-closed');
                    for (var i = 0; i < tags.length; i++) {
                        $('input[data-tag-code="' + tags[i].code + '"]').prop('checked', true);
                    }
                    for (var j = 0; j < roles.length; j++) {
                        $('input[data-role-code="' + roles[j].code + '"]').prop('checked', true);
                    }
                    if (roles.length === $('#author-roles-filter').find('input').length - 1) {
                        $('#all').prop('checked', true);
                    }
                } else {
                    $('#tag-filter-container').addClass('zion-closed');
                    $('#tag-filter-container').find('input:checkbox').prop('checked', false);
                }
                $('#tag-filter-container').slideToggle(500);
                $('#filter-toggle-button').find('i').toggleClass('fa-angle-down fa-angle-up');
                $('.filter-display').toggleClass('filter-hide');
                toggleTagScrollBtn();
            },
            deleteFilter = function (code, deleteElement, filterType) {
                if (filterType === 'role') {
                    commonService.removeUserRoleTag(code);
                }
                if (filterType === 'tag') {
                    commonService.removeFeedFilterTag(code);
                }
                deleteElement.remove();
                setFilterNum();
                toggleTagScrollBtn();
                getFeedsList(null);
            },
            loadUserRolesFilter = function () {
                var roles = commonService.getUserRolesTags();
                for (var i = 0; i < roles.length; i++) {
                    var role = '<li class="role" data-role-name="' + roles[i].name + '" data-role-code="' + roles[i].code + '">' +
                        '<span class="content">' + roles[i].name + '</span>' +
                        '<span class="role-delete-btn">&#10005;</span>' +
                        '</li>';
                    $('#tags-area').append(role);
                }
                toggleTagScrollBtn();
            },
            toggleTagScrollBtn = function () {
                if (parseInt($('#tags-area').get(0).scrollWidth, 10) - 1 > parseInt($('#tags-area').innerWidth(), 10)) {
                    $('.tag-scroll-btn').removeClass('zion-hide');
                } else {
                    $('.tag-scroll-btn').addClass('zion-hide');
                }
            },
            capitalFirstLetter = function (text) {
                return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
            },
            getFeedsList = function (pageToken, orderCode) {
                isLoading = true;
                loading.show();
                var data = {'resultSize': 20},
                    filterTags = commonService.getFeedFilterTags(),
                    selectedUserRolesTags = commonService.getUserRolesTags(),
                    tempTags = [],
                    tempRoles = [];
                if (pageToken) {
                    data['nextPageToken'] = nextPageToken;
                }
                if (filterTags.length !== 0) {
                    for (var i = 0; i < filterTags.length; i++) {
                        tempTags.push(filterTags[i].code);
                    }
                    data['tags'] = tempTags.join(',');
                }
                if (selectedUserRolesTags.length > 0) {
                    for (var j = 0; j < selectedUserRolesTags.length; j++) {
                        tempRoles.push(selectedUserRolesTags[j].code);
                    }
                    data["authorRoles"] = tempRoles.join(',');
                }
                if (orderCode) {
                    data["orderBy"] = orderCode;
                }
                data['systags'] = 'top,good,fair';
                zionWebService.getPub(listFeedsUrl, data, function (response) {
                    nextPageToken = response['nextPageToken'];
                    if (response.data) {
                        var feeds = response.data;
                        if (feeds.length === 0 && pageToken === null) {
                            $('#feed-list').hide();
                            $('.empty-message').show();
                        } else {
                            $('#feed-list').show();
                            $('.empty-message').hide();
                            var newData = [];
                            for (var i = 0; i < feeds.length; i++) {
                                var feed = feeds[i];
                                if (feed && feed.coverImage && feed.coverImage.url) {
                                    feed.coverImage.url = feedService.getImageUrl(feed.coverImage.url);
                                }
                                feed.isShowAuthor = true;
                                feed.likeIcon = feed.like ? 'fa-heart like' : 'fa-heart-o dislike';
                                newData.push(feed);
                            }
                            var itemsHTML = Handlebars.templates.feedcard({data: newData});
                            var $items = $(itemsHTML);
                            $items.find('.zion-feed-like-plugin-container').each(function( index ) {
                                $(this).zionFeedLikePlugin({
                                    id: $(this).data('feed-like-id'),
                                    likeCount: $(this).data('feed-like-count'),
                                    like: $(this).data('feed-like'),
                                    fireToggleFeedLikeStatus: false
                                });
                            });

                            if (!pageToken) {
                                var listItems = jQuery('#feed-list .grid-item');
                                $grid.masonry('remove', listItems);
                                $grid.masonry('layout');
                            }
                            $grid.masonryImagesReveal($items);
                        }
                    }

                    loading.hide();
                    isLoading = false;
                }, function (response) {
                    loading.hide();
                    isLoading = false;
                    if (console && console.log) {
                        console.log(data);
                    }
                });
            };
        loadTags();
        loadUserRolesFilter();
        getFeedsList(null);
        setFilterNum();

        if (isIE()) {
            $('#tag-area-wrap').addClass('ie');
        }

        commonService.winScroll(function () {
            if (!isLoading) {
                if (nextPageToken === null || nextPageToken) {
                    getFeedsList(nextPageToken);
                }
            }
        });

        $grid.on('click', '.feed-image', $.debounce(1000, true, function () {
            var url = getFeedUrl + "?id=" + $(this).attr("id"),
                feed = feedService.getfeedById(url);
        }));

        zionFeedLikePluginUtils.addChangeFeedLikeStatusEvent($('#feed-list'));

        $grid.on('click', '.author-info-container', function () {
            var userId = $(this).data('user-id');
            if (userId) {
                window.location.href = stylistUrl + '?userId=' + userId;
            }
        });

        $('body').on('click', '.modal-author-info-container', function () {
            var userId = $(this).attr("id");
            if (userId) {
                window.location.href = stylistUrl + '?userId=' + userId;
            }
        });

        $('#left-button').click(function (event) {
            event.preventDefault();
            $('#tags-area').animate({
                scrollLeft: "-=" + 500 + "px"
            }, "fast");
        });

        $('#right-button').click(function (event) {
            event.preventDefault();
            $('#tags-area').animate({
                scrollLeft: "+=" + 500 + "px"
            }, "fast");
        });

        $('#tag-filter-container').on('click', '.zion-order-tag', function () {
            var $radio = $(this);
            // if this was previously checked
            if ($radio.data('waschecked') === true) {
                $radio.prop('checked', false);
                $radio.data('waschecked', false);
            }
            else {
                $radio.prop('checked', true);
                $radio.data('waschecked', true);
            }

            // remove was checked from other radios
            // $('#tag-filter-container').find('.zion-order-tag').data('waschecked', false);
            $.each($('#tag-filter-container').find('.zion-order-tag'), function () {
                if ($(this).data('order-code') !== $radio.data('order-code')) {
                    $(this).data('waschecked', false);
                }
            });
        });

        $('#reset-filter-button').on('click', $.debounce(1000, true, function () {
            $('#tags-area').empty();
            $('.filter-num').text('').removeClass('filter-num-show');
            $('.user-roles-filter').find('input').prop('checked', false);
            $('#tag-filter-container').find('.zion-order-tag').data('waschecked', false);
            commonService.removeAllUserRoleTag();
            commonService.removeAllFeedFilterTag();
            toggleTagScrollBtn();
            getFeedsList(null);
        }));

        $('#tag-filter-container').on("change", ".author-roles-item", function () {
            var target = $(this);
            if (this.checked) {
                if (target.data('role-code') === "all") {
                    $('#author-roles-filter').find('input').prop('checked', true);
                } else {
                    var checkedNum = $('#author-roles-filter').find('input:checked').length;
                    if (checkedNum === $('#author-roles-filter').find('input').length - 1) {
                        $('#all').prop('checked', true);
                    }
                }
            } else {
                if (target.data('role-code') === "all") {
                    $('#author-roles-filter').find('input:checked').prop('checked', false);
                } else {
                    $('#all').prop('checked', false);
                }
            }
        });

        $('#filter-toggle-button').on('click', function () {
            setFilterStatus();
        });

        $('#tag-filter-container').on('click', '.title', function () {
            $(this).parents('.filter-container').toggleClass('style-filter-show');
            $(this).find('i').toggleClass('fa-plus-square-o fa-minus-square-o');
        });

        $('#tag-filter-container').on('click', '#filter-select-cancel-btn', function () {
            setFilterStatus();
        });

        $('#tag-filter-container').on('click', '#filter-submit-btn', $.debounce(1000, true, function () {
            var orderCode = $('#order-filter').find('input[name=order]:checked').data('order-code');
            $('#tags-area').empty();
            commonService.removeAllFeedFilterTag();
            commonService.removeAllUserRoleTag();

            $.each($('#author-roles-filter').find('.author-roles-item:checked'), function () {
                if ($(this).data('role-code') !== 'all') {
                    var code = $(this).data('role-code'),
                        name = $(this).data('role-name'),
                        role = {
                            code: code,
                            name: name
                        },
                        displayRole = '<li class="role" data-role-name="' + name + '" data-role-code="' + code + '">' +
                            '<span class="content">' + name + '</span>' +
                            '<span class="role-delete-btn">&#10005;</span>' +
                            '</li>';
                    $('#tags-area').append(displayRole);
                    commonService.addUserRolesTag(role);
                }
            });

            $.each($('.user-roles-filter').find('.zion-filter-tag:checked'), function () {
                var code = $(this).data('tag-code'),
                    name = $(this).data('tag-name'),
                    tag = {
                        code: code,
                        name: name
                    },
                    displayTag = '<li class="tag" data-tag-name="' + name + '" data-tag-code="' + code + '">' +
                        '<span class="content">#' + name + '</span>' +
                        '<span class="filter-delete-btn">&#10005;</span>' +
                        '</li>';
                $('#tags-area').append(displayTag);
                commonService.addFeedFilterTag(tag);
            });
            if (orderCode !== undefined && orderCode !== '') {
                getFeedsList(null, orderCode);
            }
            else {
                getFeedsList(null);
            }
            setFilterNum();
            setFilterStatus();
        }));

        $('.filter-panel-container').on('click', '.role-delete-btn', $.debounce(500, true, function () {
            var deleteRoleCode = $(this).parents('.role').data('role-code'),
                deleteRoleElement = $(this).parents('.role');
            deleteFilter(deleteRoleCode, deleteRoleElement, 'role');
        }));

        $('.filter-panel-container').on('click', '.filter-delete-btn', $.debounce(500, true, function () {
            var deleteTagCode = $(this).parents('.tag').data('tag-code'),
                deleteTagElement = $(this).parents('.tag');
            deleteFilter(deleteTagCode, deleteTagElement, 'tag');
        }));
    });
})(jQuery);