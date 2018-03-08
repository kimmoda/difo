jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var feed = null,
            feedTags = [],
            $easyInstance,
            annotations,
            $progressBar,
            tags = {},
            limitedCreateTag = 20,
            limitedAutocompleteTag = 20,
            tagOversizeMessage = 'You can add up to 20 different tags related to your look.',
            validationService = zionValidationService(),
            fields = [
                {
                    'target': '#feed-title',
                    'error': '#title-error-indicator',
                    'minLength': 5,
                    'maxLength': 120
                },
                {
                    'target': '#feed-description',
                    'error': '#desc-error-indicator',
                    'minLength': 10,
                    'maxLength': 300
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
                initProgressBar();
                $('.image-container').removeClass('hidden');
                autosize(document.querySelectorAll('textarea'));
                if (feedId) {
                    loadFeed(feedId);
                }
            },
            updatePhoto = function (photoInfo) {
                if (feed && photoInfo) {
                    var photo = {
                        publicId: photoInfo.public_id,
                        version: photoInfo.version,
                        format: photoInfo.format,
                        url: photoInfo.secure_url
                        // "source": "INSTAGRAM" or "FACEBOOK"
                    };
                    var date = {
                        feedId: feed.id,
                        photo: photo
                    };
                    zionLoadingService().show('body');
                    zionWebService.post(zionUrls.rest_photoChange, date, function (resp) {
                        zionLoadingService().stop('body');
                        var feedId = resp.data.id;
                        window.location.replace(zionUrls.action_addLooks + "?feedId=" + feedId);
                    })
                }
            },
            changePhoto = function () {
                // zionUploadPhotoWidget().update(feed);
                zionUploadPhotoWidget().uploadFeedWithCallback(1,function (photoInfo) {
                    if (photoInfo.length === 1) {
                        updatePhoto(photoInfo[0]);
                    }
                })

            },
            initProgressBar = function () {
                var $progressDiv = $("#progressBar");
                $progressBar = $progressDiv.progressStep({
                    activeColor: "#4a9a49",
                    strokeColor: "#d4d4d4",
                    fillColor: "#ffffff",
                    visitedFillColor: "#dddddd",
                    margin: 50,
                    radius: 25,
                    labelOffset: 40,
                    "font-family": "'Helvetica Neue', 'Helvetica', Arial, sans-serif",
                    "font-size": 15,
                    "font-weight": "normal"
                });
                $progressBar.addStep("Tag items");
                $progressBar.addStep("Publish");
                $progressBar.setCurrentStep(0);
                $progressBar.refreshLayout();

            },
            loadFeed = function (feedId) {
                addFeedService.getFeed(feedId, function (resp) {
                    feed = resp.data;
                    readImage();
                    initFeedInfo(feed);
                })
            },
            initFeedInfo = function (feed) {
                if (feed.title) {
                    $('#feed-title').val(feed.title);
                }
                if (feed.content) {
                    $('#feed-description').val(feed.content);
                }
                feedTags = feed.feedTags;
                $.each(feedTags, function (index, value) {
                    if (value['source'] === 'shuser') {
                        var tagHtml = '<li class="custom-tag-container"><span class="zion-filter-tag custom-tag active">#' + value['code'] + '<span class="tag-delete-btn" data-tag-code="' + value['name'] + '">&#10005;</span></span></li>';
                        $('#custom-tag-container').append(tagHtml);
                    }
                })
            },
            readImage = function () {
                zionLoadingService().stop('.image-container');
                $('#photo-viewer').removeClass('hidden');
                $('.change-photo-button-container').removeClass('hidden');
                $('.image-container').removeClass('dash-border');
                if ($easyInstance) {
                    $('.pinParent').remove();
                    var photoElement = '<img id="photo-viewer" src="#" class="img-responsive center-block pin" easypin-id="pin-image"/>';
                    $('.image-container').append(photoElement);
                }
                $('#photo-viewer').attr('src', feed.photo.url);
                $progressBar.setCurrentStep(0);
                $easyInstance = $('#photo-viewer').easypin({
                    init: feed.photo && feed.photo.annotationData ? feed.photo.annotationData : '{}',
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
                        checkAndSaveFeed(false);
                    },
                    afterInit: function () {
                        annotationIndicator();
                    }
                });
                $easyInstance.easypin.event("get.coordinates", function ($instance, data, params) {
                    if (data) {
                        for (var index in data["pin-image"]) {
                            if (index === "canvas") {
                                continue;
                            }
                            var marker = data["pin-image"][index];
                            if (!marker.title) {
                                delete data["pin-image"][index];
                            }
                        }
                        annotations = JSON.stringify(data);
                    } else {
                        annotations = null;
                    }
                });
            },
            annotationIndicator = function () {
                if (feed && feed.photo && (!feed.photo.annotationData || feed.photo.annotationData.length === 0)) {
                    var imageWidth = $('.pinParent').width(),
                        imageHeight = $('.pinParent').height();
                    $easyInstance.easypin.addPin(imageWidth * 0.5, imageHeight * 0.5);
                }
            },
            setTag = function (id, resp) {
                var data = resp.data,
                    filterHtml;
                var feedTagsObj = {};
                if (feedTags) {
                    $.each(feedTags, function (index, value) {
                        feedTagsObj[value.code] = value;
                    })
                }
                jQuery.each(data, function (index, value) {
                    // Deep copy the value, not add the prop 'active' below
                    tags[value.code] = JSON.parse(JSON.stringify(value));
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
                addFeedService.loadTags(function (resp) {
                    setTag('tag-container', resp);
                });
            },
            checkAndSaveFeed = function (check) {
                var isCheck = check;
                $('#server-side-error').addClass('zion-hide');
                if (!feed) {
                    $('.no-image-error').removeClass('zion-hide');
                    // zionNotifacationService.error(zionMessage.error_no_feed.content);
                    return;
                }
                if ($easyInstance) {
                    $easyInstance.easypin.fire("get.coordinates", function (data) {
                        return data;
                    });
                }
                feed['photo']['annotationData'] = annotations;
                if (feedTags) {
                    feed.feedTags = feedTags;
                }
                feed.content = $.trim($('#feed-description').val());
                feed.title = $.trim($('#feed-title').val());

                if (isCheck && !validationService.validFields(fields)) {
                    return;
                }

                if (isCheck && feedTags.length === 0) {
                    $('.tag-error-indicator').removeClass('zion-hide');
                    $('.tag-no-select-error').removeClass('zion-hide');
                    return;
                }

                addFeedService.saveFeed(feed, function (resp) {
                    if (resp.status === 200) {
                        if (resp.data) {
                            feed = resp.data;
                        }
                        $('.no-image-error').addClass('zion-hide');
                        if (isCheck) {
                            window.location.replace(commonService.getZionActionUrl('/auth/looks'));
                        }
                    } else {
                        $('#server-side-error').removeClass('zion-hide');
                    }
                })
            };
        init();

        $("#change-photo-button")
            .on("click", function () {
                if (feed && feed.photo && feed.photo.url && feed.photo.annotationData && feed.photo.annotationData.length > 0) {
                    swal({
                        title: "Warning",
                        text: "By changing this photo, the link tags will be deleted as well.",
                        icon: "warning",
                        buttons: true,
                        dangerMode: true
                    })
                        .then(function (willDelete) {
                            if (willDelete) {
                                if ($easyInstance) {
                                    $easyInstance.easypin.clear();
                                }
                                changePhoto()
                            }

                        });

                } else {
                    changePhoto();
                }
            });

        $('#save-button').on('click', $.debounce(1000, true, function () {
            checkAndSaveFeed(true);
        }));

        validationService.registerFieldEvents(fields);

        $('#delete-button, #final-step-delete-btn').on('click', function () {
            swal({
                title: "Warning!",
                text: "Do you want to delete this photo? This action cannot be undone.\n" +
                "If you applied tags or links already, these will be deleted too.",
                icon: "warning",
                buttons: true,
                dangerMode: true
            })
                .then(function (willDelete) {
                    if (willDelete) {
                        addFeedService.deleteFeed(feed.id, function (resp) {
                            window.location.replace(commonService.getZionActionUrl('/auth/looks'));
                        })
                    }
                });
        });

        $('#look-thumbnail').click(function () {
            var feedId = feed.id;
            if (feedId) {
                var url = commonService.getZionActionUrl('/feed') + "?feedId=" + feedId;
                window.open(url, "_blank");
            }
        });

        $('#next-button').on('click', function () {
            if ($easyInstance) {
                $easyInstance.easypin.fire("get.coordinates", function (data) {
                    return data;
                });
            }
            if (annotations) {
                feed['photo']['annotationData'] = annotations;
            }

            initTags();
            $('#look-thumbnail').attr('src', feed.photo.url);
            $('.step-1').animateCss('slideOutLeft', function () {
                $('.step-1').addClass('zion-hide');
            });
            $('.step-2').removeClass('zion-hide').animateCss('slideInRight', function () {
                $('.step-2').removeClass('absolute');
                // $('.step-2').addClass('flex');
                $progressBar.setCurrentStep(1);
            });

            checkAndSaveFeed(false)
        });

        $('#previous-button').on('click', function () {
            $('.step-2').addClass('absolute');
            $('.step-2').removeClass('flex');
            $('.step-2').animateCss('slideOutRight', function () {
                $('.step-2').addClass('zion-hide');
            });
            $('.step-1').removeClass('zion-hide').animateCss('slideInLeft', function () {
                $progressBar.setCurrentStep(0);
            });
            $('.annotation-container').empty().append("<img class=\"img-responsive center-block pin\" src=\"#\" alt=\"look image preview\"\n" +
                "                            id=\"look-image-preview\" easypin-id=\"pin-image\">");
        });

        $('#new-button').on('click', function () {
            swal({
                title: "Are you sure?",
                text: "Do you want to create another looks?",
                icon: "warning",
                buttons: true,
                dangerMode: true
            })
                .then(function (willDelete) {
                    if (willDelete) {
                        window.location.replace(commonService.getZionActionUrl('/auth/looks/add'));
                    }
                });
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
    });
})(jQuery);
