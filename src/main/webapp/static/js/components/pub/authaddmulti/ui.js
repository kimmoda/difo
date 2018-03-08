jQuery.noConflict();
(function ($) {
    $('.add-post').removeClass('zion-hide');
    var promptAnnotation = false,
        $easyInstance,
        feedInfo = {},
        feedService = zionFeedService(),
        annotations,
        feedTags = [],
        coverImageId = null,
        validationService = zionValidationService(),
        currentFocus,
        lastEditMediaId,
        limitedCreateTag = 20,
        limitedAutocompleteTag = 20,
        tagOversizeMessage = 'You can add up to 20 different tags related to your look.',
        noImageError = "Please upload at least one image.",
        requiredFiledError = "Please fill all required filed.",
        maxMediaNum = 5,
        mediaCountNum = 0,
        tags = {},
        fields = [
            {
                'target': '#feed-title',
                'error': '#title-error-indicator',
                'minLength': 5,
                'maxLength': 120
            },
            {
                'target': '#feed-content',
                'error': '#desc-error-indicator',
                'minLength': 10
            }
        ],
        customTagFields = [
            {
                'target': '#tag-input',
                'error': '.tag-error-indicator',
                'minLength': 0,
                'maxLength': 30
            }
        ],
        init = function () {
            autosize($('textarea'));
            if (feedId) {
                multiImageFeedService.getFeedInfoById(feedId, function (resp) {
                    feedInfo = resp;
                    setDefaultValue(feedInfo);
                    annotations = resp.annotationData;
                    reInitMediaContent();
                    initTags();
                });
            } else {
                initTags();
                checkAndSaveFeed(false, true);
            }
        },
        reInitMediaContent = function () {
            mediaCountNum = 0;
            var images;
            if (feedInfo.coverImage) {
                images = $.merge([feedInfo.coverImage], feedInfo.mediaContent);
            } else {
                images = feedInfo.mediaContent.slice();
            }
            appendMedia(images);
            if (feedInfo.coverImage) {
                setCoverImage(feedInfo.coverImage.publicId);
            }
            autosize($('textarea'));
        },
        setDefaultValue = function (feedInfo) {
            $('#feed-title').val(feedInfo.title);
            $('#feed-content').val(feedInfo.content);
            autosize.update($('textarea'));
            feedTags = feedInfo.feedTags;
            $.each(feedTags, function (index, value) {
                if (value['source'] === 'shuser') {
                    var tagHtml = '<li class="custom-tag-container"><span class="zion-filter-tag custom-tag active">#' + value['code'] + '<span class="tag-delete-btn" data-tag-code="' + value['name'] + '">&#10005;</span></span></li>';
                    $('#custom-tag-container').append(tagHtml);
                }
            })

        },
        appendMedia = function (photoList) {
            if (photoList && photoList.length > 0) {
                $.each(photoList, function (index, value) {
                    if (value.mediaType !== "TEXT") {
                        mediaCountNum++;
                    }
                    value.description = filterXSS(value.description);
                    var cardComponent = Handlebars.templates.multiimagefeedcomponent(value);
                    $('.feed-image-container').append(cardComponent);
                    autosize($('textarea'));
                });
                initEasyPin();
            }
        },
        initEasyPin = function () {
            var target = '.look-thumbnail';
            $easyInstance = $(target).easypin({
                init: annotations,
                markerSrc: markIconURL,
                editSrc: markEditURL,
                deleteSrc: markRemoveURL,
                popoverStyle: {
                    'display': 'none'
                },
                done: function (element) {
                    var title = $.trim(element.find('#tag_title').val()),
                        url = $.trim(element.find('#tag_url').val()),
                        urlRegex = new RegExp("^(http|https)://", "i");
                    element.find('#tag_url').val(url);
                    element.find('#tag_title').val(title);
                    element.find('#tag_title').keyup(function () {
                        $(this).removeClass('zion-input-error-indicator');
                        element.find('#empty-easypin-title-error').addClass('zion-hide');
                    });

                    element.find('#tag_url').keyup(function () {
                        $(this).removeClass('zion-input-error-indicator');
                        element.find('#empty-easypin-url-error').addClass('zion-hide');
                        element.find('#invalid-easypin-url-error').addClass('zion-hide');
                    });
                    if (title === '') {
                        element.find('#tag_title').addClass('zion-input-error-indicator');
                        element.find('#empty-easypin-title-error').removeClass('zion-hide');
                        return false;
                    } else {
                        element.find('#tag_title').removeClass('zion-input-error-indicator');
                        element.find('#empty-easypin-title-error').addClass('zion-hide');
                    }

                    if (!urlRegex.test(url)) {
                        if ($.trim(url).length > 0) {
                            element.find('#tag_url').val("http://" + url);
                        } else {
                            var singleUrl = zionUrls.action_stylist_single.replace("{userId}", commonService.getUserInfo().id);
                            element.find('#tag_url').val(singleUrl);
                        }
                    }
                    return true;
                },
                afterDone: function (data) {
                    checkAndSaveFeed(false, true);
                },
                afterInit: function () {
                    if (feedInfo && !feedInfo.annotationData) {
                        if (!promptAnnotation) {
                            var firstImage = $('.pinParent')[0],
                                imageWidth = $(firstImage).width(),
                                imageHeight = $(firstImage).height();
                            $easyInstance.easypin.addPin(imageWidth * 0.5, imageHeight * 0.5);
                            promptAnnotation = true;
                        }
                    }
                }
            });
            $easyInstance.easypin.event("get.coordinates", function ($instance, data, params) {
                if (data) {
                    var newData = {};
                    for (var image in data) {
                        var imageObj = data[image];
                        var newImageObj = {};
                        for (var annotation in imageObj) {
                            var content = imageObj[annotation];
                            if (annotation === "canvas") {
                                newImageObj[annotation] = content;
                            } else {
                                if (content.title && content.title.length > 0) {
                                    newImageObj[annotation] = content;
                                }
                            }
                        }
                        if (!$.isEmptyObject(newImageObj)) {
                            newData[image] = newImageObj;
                        }
                    }
                    annotations = JSON.stringify(newData);
                }
            });
        },
        findMediaById = function (mediaId) {
            var found = false,
                feedSet = [];

            // search media from mediaContent
            if (feedInfo && feedInfo.mediaContent) {
                feedSet = feedInfo.mediaContent;
                $.each(feedSet, function (index, media) {
                    if (media.publicId === mediaId + "") {
                        found = media;
                    }
                })
            } else {
                feedInfo.mediaContent = [];
            }

            // search media from coverImage
            if (!found && feedInfo.coverImage) {
                if (feedInfo.coverImage.publicId === mediaId + "") {
                    found = feedInfo.coverImage;
                }
            }
            return found
        },
        hascoverImage = function () {
            var result = false;
            if (feedInfo.coverImage && feedInfo.coverImage.coverImage === true) {
                return true;
            }
            if (feedInfo.mediaContent) {
                $.each(feedInfo.mediaContent, function (index, media) {
                    if (media.mediaType === "IMAGE") {
                        result = true;
                    }
                })
            }
            return result;
        },
        checkField = function () {
            var result = true;
            if (!validationService.validFields(fields)) {
                $("html, body").animate({scrollTop: $('#feed-title').offset().top - 120}, 500);
                $('#server-side-error').text(requiredFiledError);
                $('#server-side-error').removeClass('zion-hide');
                result = false;
            } else {
                $('#server-side-error').addClass('zion-hide');
            }

            if (feedTags.length === 0) {
                $('#server-side-error').text(requiredFiledError);
                $('#server-side-error').removeClass('zion-hide');
                $('.tag-error-indicator').removeClass('zion-hide');
                $('.tag-no-select-error').removeClass('zion-hide');
                $('.tag-container').addClass('zion-input-error-indicator');
                if (result) {
                    $("html, body").animate({scrollTop: $('.tag-area').offset().top - 75}, 500);
                }
                result = false;
            } else {
                if (result) {
                    $('#server-side-error').addClass('zion-hide');
                }
                $('.tag-error-indicator').addClass('zion-hide');
                $('.tag-no-select-error').addClass('zion-hide');
                $('.tag-container').removeClass('zion-input-error-indicator');
            }

            if (!hascoverImage()) {
                $('#server-side-error').text(noImageError);
                $('#server-side-error').removeClass('zion-hide');
                result = false
            } else {
                if (result) {
                    $('#server-side-error').addClass('zion-hide');
                }
            }
            return result;
        },
        checkAndSaveFeed = function (isCheck, isSave, callback) {

            // 0. check
            if (feedInfo.status === "FINAL") {
                if (!checkField()) {
                    feedInfo.status = "DRAFT";
                }
            } else {
                feedInfo.status = "DRAFT";
                if (isCheck) {
                    if (!checkField()) {
                        return;
                    }
                    feedInfo.status = "FINAL";
                }
            }

            feedInfo.title = $.trim($('#feed-title').val());
            feedInfo.content = $.trim($('#feed-content').val());


            // 1. annotation
            if ($easyInstance) {
                $easyInstance.easypin.fire("get.coordinates", function (data) {
                    return data;
                });
            }
            feedInfo['annotationData'] = annotations;

            // 2. description
            var descArea = $('.feed-description');
            $.each(descArea, function (index, target) {
                var mediaId = $(target).data("mediaid"),
                    descHtml = $('#feed-description-' + mediaId).html(),
                    media = findMediaById(mediaId);
                if (media) {
                    media.description = descHtml;
                }
            });


            // 3. tags
            if (feedTags) {
                feedInfo.feedTags = feedTags;
            }

            // 4. Save
            if (isSave && isSave === true) {
                if (feedInfo.id) {
                    if (isCheck) {
                        zionLoadingService().show('body');
                    }
                } else {
                    zionLoadingService().show('body', 'initializing...');
                }
                multiImageFeedService.saveFeed(feedInfo, function (resp) {
                    if (resp.status === 200) {
                        if (resp.data) {
                            feedInfo = resp.data;
                        }
                        $('.no-image-error').addClass('zion-hide');
                        if (isCheck) {
                            window.location.replace(commonService.getZionActionUrl('/auth/looks'));
                        }
                    } else {
                        $('#server-side-error').removeClass('zion-hide');
                    }
                    zionLoadingService().stop('body');
                    if (callback && $.isFunction(callback)) {
                        callback();
                    }
                });
            }

        },
        setTag = function (id, resp) {
            var data = resp.data,
                filterHtml,
                feedTagsObj = {},
                feedTags = feedInfo.feedTags;
            if (feedTags) {
                $.each(feedTags, function (index, value) {
                    feedTagsObj[value.code] = value;
                })
            }
            jQuery.each(data, function (index, value) {
                // Deep copy the value, not add the prop 'active' below
                tags[value.code] = $.extend(true, {}, value);
                if (feedTagsObj[value.code]) {
                    value['active'] = 'active';
                } else {
                    value['active'] = '';
                }
            });
            filterHtml = Handlebars.templates.tagFilter({'tags': data});
            $('#' + id).empty().append(filterHtml);
        },
        initTags = function () {
            multiImageFeedService.loadTags(function (resp) {
                setTag('tag-container', resp);
            });
        },
        setCoverImage = function (photoId) {
            coverImageId = photoId;
            $("#cover-image-" + photoId).prop('checked', true);
            $('#component-' + photoId).addClass('current-cover-image');
            $("#feed-description-" + photoId).addClass('cover-image-background');
        },
        generateNewMedia = function (type, mediaInfo) {
            var returnMedia = [],
                media;
            if (!feedInfo.mediaContent) {
                feedInfo.mediaContent = [];
            }
            if (type.toLowerCase() === "image") {
                $.each(mediaInfo, function (index, item) {
                    media = {
                        mediaType: "IMAGE",
                        publicId: item.public_id,
                        url: item.secure_url,
                        description: ""
                    };
                    feedInfo.mediaContent.push(media);
                    returnMedia.push(media);
                });
            }
            if (type.toLowerCase() === "video") {
                media = {
                    mediaType: 'VIDEO',
                    publicId: mediaInfo.publicId,
                    url: mediaInfo.url,
                    description: ""
                };
                feedInfo.mediaContent.push(media);
                returnMedia.push(media);
            }
            if (type.toLowerCase() === 'text') {
                media = {
                    mediaType: 'TEXT',
                    publicId: commonService.guid(),
                    description: ""
                };
                feedInfo.mediaContent.push(media);
                returnMedia.push(media);
            }
            return returnMedia;
        };
    init();

    $('#feed-content').on('focusout', function () {
        checkAndSaveFeed(false, true);
    });

    $('#feed-title').on('focusout', function () {
        checkAndSaveFeed(false, true);
    });

    $('.plus-button').click(function () {
        $(".action-buttons").fadeToggle();
    });

    // insert text
    $('.add-text').click(function () {
        var newMedia = generateNewMedia("TEXT");
        appendMedia(newMedia);
    });


    // insert photo
    $('.add-photos').click(function () {
        if (mediaCountNum < maxMediaNum) {
            zionUploadPhotoWidget().uploadFeedWithCallback(maxMediaNum - mediaCountNum, function (photos) {
                generateNewMedia("IMAGE", photos);
                $('.feed-image-container').empty();
                reInitMediaContent();
                checkAndSaveFeed(false, true);
            })
        } else {
            $('.overcount-error-indicator').removeClass('zion-hide');
        }
    });


    // insert video link
    $('.add-video').click(function () {
        if (mediaCountNum < maxMediaNum) {
            $('#youtube-url-error').addClass('zion-hide');
            $('#insert-video').modal();
        } else {
            $('.overcount-error-indicator').removeClass('zion-hide');
        }
    });

    $('.video-confirmed').click(function () {
        var url = $.trim($('#video-url').val()),
            desc = $.trim($('#video-desc').val()),
            videoId = commonService.getYoutubeIdFromUrl(url),
            guid = commonService.guid();
        if (!videoId) {
            $('#youtube-url-error').removeClass('zion-hide');
            return;
        }
        $('#insert-video').modal('hide');
        var videoInfo = {
            url: "https://www.youtube.com/embed/" + videoId,
            description: desc,
            mediaType: "VIDEO",
            publicId: guid
        };

        var newMedia = generateNewMedia("VIDEO", videoInfo);
        appendMedia(newMedia);
        checkAndSaveFeed(false, true);
        window.setTimeout(function () {
            $('#feed-description-' + guid).focus();
            document.execCommand("formatBlock", false, "div");
        }, 1000);
    });

    $('.feed-image-container')
        .on('focus', '.feed-description', function () {
            var mediaId = $(this).data('mediaid');
            $('#tools-area-' + mediaId).removeClass('zion-hide');
            if (lastEditMediaId && lastEditMediaId !== mediaId) {
                $('#tools-area-' + lastEditMediaId).addClass('zion-hide');
            }
            lastEditMediaId = mediaId;

        })

        .on('focusout', '.feed-description', function () {
            currentFocus = window.getSelection().getRangeAt(0);
            // checkAndSaveFeed(false, true);

        })
        .on("input", ".feed-description", function () {
            var htmlString = $.trim($(this).html());
            if (htmlString.length < 2 || htmlString === "<br>") {
                document.execCommand("formatBlock", false, "div");
            }
        });


// Insert Link
    $('.link-confirmed').click(function () {
        if (!currentFocus) {
            return;
        }
        var url = $.trim($('#link-url').val()),
            title = $.trim($('#link-desc').val()),
            urlRegex = new RegExp("^(http|https)://", "i");
        if (!urlRegex.test(url)) {
            if ($.trim(url).length > 0) {
                url = "http://" + url;
            }
        }
        var atag = " <a href=" + url + " target='_blank'>" + title + "</a> ";
        if (url.length > 0 && title.length > 0) {
            window.setTimeout(function () {
                window.getSelection().addRange(currentFocus);
                pasteHtmlAtCaret(atag);
            }, 500);
        } else {
            if (currentFocus) {
                var start = currentFocus.startOffset,
                    end = currentFocus.endOffset,
                    startContent = currentFocus.startContainer.wholeText,
                    endContent = currentFocus.endContainer.wholeText;
                if (startContent === endContent && start === end) {
                } else {
                    window.setTimeout(function () {
                        window.getSelection().addRange(currentFocus);
                        document.execCommand("createLink", false, url);
                    }, 500);
                }
            }
        }
    });

// clear the input content before shown the modal
    $('#insert-video').on('show.bs.modal', function (e) {
        $('#video-url').val("");
        $('#video-desc').val("");
    });

    $('#insert-link').on('show.bs.modal', function (e) {
        $('#link-url').val("");
        $('#link-desc').val("");
        if (currentFocus) {
            var start = currentFocus.startOffset,
                end = currentFocus.endOffset,
                startContent = currentFocus.startContainer.wholeText,
                endContent = currentFocus.endContainer.wholeText;
            if (startContent === endContent && start !== end) {
                // 1. selected area in the same container
                $('.link-desc-container').show();
                $('#link-desc').val(startContent.substring(start, end));
            } else if (startContent !== endContent) {
                // 2. selected area in the different container
                $('.link-desc-container').hide();
            } else {
                // 3. no selected area
                $('.link-desc-container').show();
            }
        }
    });


    $('.feed-image-container').on('click', '.trash-icon', function () {
        var mediaId = $(this).data("mediaid");
        $('#component-' + mediaId).fadeOut(500, function () {
            $('#component-' + mediaId).remove();
            var mediaInfo = findMediaById(mediaId);
            if (mediaInfo.mediaType !== "TEXT") {
                mediaCountNum--;
            }
            if (feedInfo.coverImage && feedInfo.coverImage.publicId === mediaId) {
                feedInfo.coverImage = null
            } else {
                var media = feedInfo.mediaContent;
                feedInfo.mediaContent = commonService.removeObjectFromArray(media, "publicId", mediaId);
                if (annotations) {
                    delete annotations[mediaId];
                }
            }
            if (mediaInfo.mediaType === "VIDEO" || mediaInfo.mediaType === "IMAGE") {
                $('.overcount-error-indicator').addClass('zion-hide');
            }
            checkAndSaveFeed(false, true);

        });
    })
        .on('change', '.cover-image', function () {
            var currentImageId = $(this).data('mediaid'),
                currentCoverImage = findMediaById(currentImageId);
            if (this.checked) {
                if (coverImageId) {
                    $("#cover-image-" + coverImageId).prop('checked', false);
                    $('#component-' + coverImageId).removeClass('current-cover-image');
                    $("#feed-description-" + coverImageId).removeClass('cover-image-background');
                    var lastCoverImage = findMediaById(coverImageId);
                    lastCoverImage.coverImage = false;

                }
                setCoverImage(currentImageId);
                currentCoverImage.coverImage = true;
            } else {
                $('#component-' + currentImageId).removeClass('current-cover-image');
                $("#feed-description-" + currentImageId).removeClass('cover-image-background');
                currentCoverImage.coverImage = false;
            }
        });


    $('#tag-container').on('click', '.zion-filter-tag', function (e) {
        var target = $(this),
            code = target.data('tag-code');
        target.toggleClass('active');
        $('.tag-container').removeClass('zion-input-error-indicator');
        $('.tag-no-select-error').addClass('zion-hide');
        if (target.hasClass('active')) {
            $('.tag-input').removeClass('zion-input-error-indicator');
            $('.tag-error-indicator').addClass('zion-hide').text('');
            feedTags.push(tags[code]);
        } else {
            feedTags = commonService.removeObjectFromArray(feedTags, 'code', code);
            if (feedTags.length === 0) {
                $('.tag-container').addClass('zion-input-error-indicator');
                $('.tag-no-select-error').removeClass('zion-hide');
            }
        }
    });

    $('#add-tag-button').on('click', function () {
        var tagInput = zionStringUtils.normalizeToIdString($('#tag-input').val()),
            tag = {
                code: tagInput,
                codeSystem: "pstyle",
                name: tagInput,
                source: "shuser"
            },
            tagCount = 0,
            tagRepeat = false,
            tagHtml = '<li class="custom-tag-container"><span class="zion-filter-tag custom-tag active">#' + tagInput + '<span class="tag-delete-btn" data-tag-code="' + tagInput + '">&#10005;</span></span></li>';

        $('.tag-error-indicator').addClass('zion-hide').text('');
        if (!validationService.validFields(customTagFields)) {
            return;
        }
        if (tagInput === '') {
            return;
        }
        $.each(feedTags, function (index, value) {
            if (value['source'] === 'shuser') {
                tagCount++;
                if (value['code'] === tagInput) {
                    tagRepeat = true;
                }
            }
        });
        if (tagRepeat) {
            $('#tag-input').val('').removeClass('zion-input-error-indicator');
            return;
        }
        if (tagCount >= limitedCreateTag) {
            $('.tag-input').addClass('zion-input-error-indicator');
            $('.tag-error-indicator').removeClass('zion-hide').text(tagOversizeMessage);
            return;
        }
        feedTags.push(tag);
        $('.tag-container').removeClass('zion-input-error-indicator');
        $('.tag-no-select-error').addClass('zion-hide');
        $('#custom-tag-container').append(tagHtml);
        $('#tag-input').val('').removeClass('zion-input-error-indicator');
    });

    validationService.registerFieldEvents(customTagFields);
    validationService.registerFieldEvents(fields);

    $('#custom-tag-container').on('click', '.tag-delete-btn', function () {
        var code = $(this).attr('data-tag-code');
        feedTags = commonService.removeObjectFromArray(feedTags, 'code', code);
        $(this).parents('.custom-tag-container').remove();
        $('.tag-input').removeClass('zion-input-error-indicator');
        $('.tag-container').removeClass('zion-input-error-indicator');
        $('.tag-no-select-error').addClass('zion-hide');
        $('.tag-error-indicator').addClass('zion-hide').text('');
        if (feedTags.length === 0) {
            $('.tag-container').addClass('zion-input-error-indicator');
            $('.tag-no-select-error').removeClass('zion-hide');
        }
        feedInfo.feedTags = feedTags;
    });

    $('#tag-input').autoComplete({
        minChars: 1,
        source: function (term, response) {
            var data = {
                label: term,
                resultSize: limitedAutocompleteTag
            };
            zionWebService.getPub(zionUrls.rest_getCustomTags, data, function (resp) {
                if (resp.status === 200) {
                    var searchResult = [];
                    jQuery.each(resp.data, function (index, value) {
                        searchResult.push(value.code);
                    });
                    response(searchResult);
                }
            });
        }
    });

    $('#delete-btn').click(function () {
        if (feedInfo && feedInfo.id) {
            swal({
                title: "Warning!",
                text: "Do you want to delete this post? This action cannot be undone.\n" +
                "If you applied tags or links already, these will be deleted too.",
                icon: "warning",
                buttons: true,
                dangerMode: true
            })
                .then(function (willDelete) {
                    if (willDelete) {
                        multiImageFeedService.deleteFeed(feedInfo.id, function (resp) {
                            window.location.replace(commonService.getZionActionUrl('/auth/looks'));
                        })
                    }
                });
        }
    });

//publish feed
    $('#save-button').click(function () {
        checkAndSaveFeed(true, true);
    });

    $('.preview-button').click(function () {
        checkAndSaveFeed(false, true, function () {
            feedService.showModalByFeedinfo(feedInfo);
        });
    });

    function pasteHtmlAtCaret(html, selectPastedContent) {
        var sel, range;
        sel = window.getSelection();
        if (sel.getRangeAt && sel.rangeCount) {
            range = sel.getRangeAt(0);
            range.deleteContents();

            // Range.createContextualFragment() would be useful here but is
            // only relatively recently standardized and is not supported in
            // some browsers (IE9, for one)
            var el = document.createElement("div");
            el.innerHTML = html;
            var frag = document.createDocumentFragment(), node, lastNode;
            while ((node = el.firstChild)) {
                lastNode = frag.appendChild(node);
            }
            var firstNode = frag.firstChild;
            range.insertNode(frag);

            // Preserve the selection
            if (lastNode) {
                range = range.cloneRange();
                range.setStartAfter(lastNode);
                if (selectPastedContent) {
                    range.setStartBefore(firstNode);
                } else {
                    range.collapse(true);
                }
                sel.removeAllRanges();
                sel.addRange(range);
            }
        }
    }

})
(jQuery);