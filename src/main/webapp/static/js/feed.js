jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var photoData,
            changedPhoto = false,
            selectize,
            feedTags = [],
            $easyInstance,
            annotationData,
            shopTags,
            limitedCreateTag = 20,
            limitedAutocompleteTag = 20,
            tagOversizeMessage = 'You can add up to 20 different tags related to your look.',
            validationService = zionValidationService(),
            customTagFields = [
                {
                    'target': '#tag-input',
                    'error': '.tag-error-indicator',
                    'minLength': 0,
                    'maxLength': 30
                }
            ],
            jwtToken = localStorage.getItem('zion-Jwt-Token'),
            updateFields = function (feed) {
                hideErrorBanner();
                var photo = feed.photo;
                var photo_url = getNewImageUrl(photo.url);
                $('#feedId').val(feed.id);
                $('#title').val(feed.title);
                $('#photo-container').addClass('zion-hide');
                $('#photo-viewer').html('<img alt="' + photo.publicId + '" src="' + photo_url + '" class="pin" easypin-id="pin-image" width="600" />');
                $('#photo-viewer').data('photo', photo);
                $('#author-select').val(feed.author.userId);
                autosize(document.querySelectorAll('textarea'));
                if (feed.content) {
                    $('#content').val(feed.content);
                    autosize.update($('textarea'));
                }
                if (feed.feedTags) {
                    var tags = [];
                    $.each(feed.feedTags, function (index, value) {
                        tags.push(value.code);
                        if (value['source'] === 'shuser') {
                            var tagHtml = '<li class="custom-tag-container"><span class="zion-filter-tag custom-tag active">#' + value['code'] + '<span class="tag-delete-btn" data-tag-code="' + value['name'] + '">&#10005;</span></span></li>';
                            $('#custom-tag-container').append(tagHtml);
                        }
                    });
                    selectize[0].selectize.setValue(tags, false);
                }

                $easyInstance = $('.pin').easypin({
                    init: feed.photo && feed.photo.annotationData ? feed.photo.annotationData : '{}',
                    markerSrc: markIconURL,
                    editSrc: markEditURL,
                    deleteSrc: markRemoveURL,
                    popoverStyle: {
                        'display': 'none'
                    },
                    responsive: true,
                    done: function (element) {
                        var title = element.find('#tag_title').val();
                        element.find('#tag_title').val(filterXSS(title));
                        var url = element.find('#tag_url').val();
                        var isHttp = url.match(/http:\/\/.+/);
                        var isHttps = url.match(/https:\/\/.+/);
                        if (isHttp === null && isHttps=== null ){
                            element.find('#tag_url').addClass('zion-input-error-indicator');
                            return false;
                        }else{
                            element.find('#tag_url').removeClass('zion-input-error-indicator');
                            return true;
                        }
                    }
                });

                $('.change-photo-container').removeClass('zion-hide').on("change", "input[type='file']", function () {
                    photoData = this.files[0];
                    changedPhoto = true;
                    localStorage.removeItem('easypin');
                    $('#save-feed-button').click();
                });


                $easyInstance.easypin.event("get.coordinates", function ($instance, data, params) {

                    if (data) {
                        annotationData = JSON.stringify(data);
                        var annotations = data['pin-image'];
                        shopTags = [];
                        for (var obj in annotations) {
                            var item = annotations[obj];
                            if (item.title && item.shoptagID) {
                                var shopTag = {
                                    title: item.title,
                                    tagID: item.shoptagID.toLowerCase(),
                                    url: item.url
                                };
                                shopTags.push(shopTag);
                            }
                        }
                    }
                });
            },
            initTagsList = function () {
                $.ajax({
                    url: getTagsUrl,
                    method: "GET",
                    dataType: "json",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    }
                }).done(function (resp) {
                    selectize = $('#feed-tags').selectize({
                        plugins: ['remove_button'],
                        maxItems: null,
                        valueField: 'code',
                        labelField: 'name',
                        searchField: 'name',
                        options: resp.data,
                        create: false,
                        onItemAdd: function (value, item) {
                            var tag = findTagFromList(value, resp.data);
                            feedTags.push(tag);
                        },
                        onItemRemove: function (value, item) {
                            feedTags = removeTagFromList(value, feedTags);
                        }
                    });
                })
            },
            initUserList = function (feedId) {
                $.ajax({
                    url: listUsersUrl + '?includeDisabled=false',
                    method: "GET",
                    dataType: "json",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Zion-Jwt-Token': jwtToken
                    }
                }).done(function (e) {
                    if (e.status === 200) {
                        var users = e.data;
                        for (var index = 0; index < users.length; index++) {
                            var item = users[index];
                            item.lowerDisplayName = item.displayName.toLowerCase();
                        }
                        users.sort(function (a, b) {
                            return sortByProps(a, b, {"lowerDisplayName": "asc"});
                        });
                        for (var i = 0; i < users.length; i++) {
                            var user = users[i],
                                id = user.id,
                                displayName = user.displayName + " (" + user.contact.email + ")";
                            $('#author-select').append("<option value='" + id + "'>" + filterXSS(displayName) + "</option>")
                        }
                        if (feedId) {
                            $.ajax({
                                url: getFeedUrl + "?id=" + feedId,
                                method: "GET",
                                dataType: "json",
                                headers: {
                                    'Accept': 'application/json',
                                    'Content-Type': 'application/json'
                                }
                            }).done(function (e) {
                                if (e.status === 200) {
                                    var feed = e.data;
                                    feedInfo = feed;
                                    updateFields(feed);
                                }
                            });
                        }
                    }
                });
            },
            findTagFromList = function (code, list) {
                var tag = null;
                jQuery.each(list, function (index, value) {
                    if (value.code.toLowerCase() === code.toLowerCase()) {
                        tag = value;
                    }
                });
                return tag;
            },
            removeTagFromList = function (code, list) {
                var tags = [];
                jQuery.each(list, function (index, value) {
                    if (value.code.toLowerCase() !== code.toLowerCase()) {
                        tags.push(value);
                    }
                });
                return tags;
            },
            showErrorBanner = function (msg) {
                $('#error-banner').removeClass('zion-hide');
                $('#error-banner').text(msg);
            },
            hideErrorBanner = function () {
                $('#error-banner').addClass('zion-hide');
                $('#error-banner').text('');
            },
            hasError = function () {
                var error = false,
                    photoData;
                if (!$('#title').val()) {
                    $('#title').parent().addClass('has-error');
                    error = true;
                } else {
                    $('#title').parent().removeClass('has-error');
                }
                if (!$('#author-select').val()) {
                    $('#author-select').parent().addClass('has-error');
                    error = true;
                } else {
                    $('#author-select').parent().removeClass('has-error');
                }
                if ($('#feedId').val()) {
                    photoData = $('#photo-viewer').data('photo');
                    if (!photoData) {
                        $('#photo-viewer').addClass('alert alert-danger');
                        $('#photo-viewer').text('Cannot update the feed as missing image.');
                        error = true;
                    } else {
                        $('#photo-viewer').removeClass('alert alert-danger');
                    }
                } else {
                    if (!$('#photo-uploader')[0] || !$('#photo-uploader')[0].files || !$('#photo-uploader')[0].files[0]) {
                        $('#photo-uploader').parent().addClass('has-error');
                        $('#photo-uploader').addClass('form-control');
                        error = true;
                    } else {
                        $('#photo-uploader').parent().removeClass('has-error');
                        $('#photo-uploader').removeClass('form-control');
                    }

                }
                if (error) {
                    showErrorBanner('Please fix required fields.');
                } else {
                    hideErrorBanner();
                }
                return error;
            },
            saveFeed = function (image, feed) {

                var data = new FormData(),
                    feedString = JSON.stringify(feed),
                    url = createFeedUrl;
                if (changedPhoto) {
                    url = uploadPhotoUrl;
                }
                data.append('image', image);
                data.append('feed', feedString);
                $('#action-throbber').removeClass('zion-hide');
                $('#save-feed-button').attr("disabled", true);
                zionLoadingService().show('.pinParent');
                $.ajax({
                    data: data,
                    url: url,
                    method: "POST",
                    headers: {
                        'Zion-Jwt-Token': jwtToken
                    },
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (response) {
                        zionLoadingService().stop('.pinParent');
                        var feed;
                        $('#action-throbber').addClass('zion-hide');
                        $('#save-feed-button').attr("disabled", false);
                        if (response.status === 200) {
                            feed = response.data;
                            updateFields(feed);
                        } else {
                            showErrorBanner(response.msg);
                        }
                    },
                    error: function (response) {
                        zionLoadingService().stop('.pinParent');
                        $('#action-throbber').addClass('zion-hide');
                        $('#save-feed-button').attr("disabled", false);
                        showErrorBanner(response.msg);
                    }
                });
            },
            updateFeed = function (feed) {
                $('#action-throbber').removeClass('zion-hide');
                $('#save-feed-button').attr("disabled", true);
                $.ajax({
                    dataType: "json",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Zion-Jwt-Token': jwtToken
                    },
                    data: JSON.stringify(feed),
                    type: "POST",
                    cache: false,
                    url: updateFeedUrl,
                    success: function (response) {
                        var feed = response.data;
                        $('#action-throbber').addClass('zion-hide');
                        $('#save-feed-button').attr("disabled", false);
                        if (response.status === 200) {
                            hideErrorBanner();
                            $('#photo-container').addClass('zion-hide');
                        } else {
                            showErrorBanner(response.msg);
                        }
                    },
                    error: function (response) {
                        $('#action-throbber').addClass('zion-hide');
                        $('#save-feed-button').attr("disabled", false);
                        showErrorBanner(response.msg);
                    }
                });
            };
        initTagsList();
        if (feedId) {
            initUserList(feedId);
        } else {
            initUserList(null);
        }
        $("textarea").on('paste', function () {
            autosize.update($('textarea'));
        });

        $('#tag-input').autoComplete({
            minChars: 1,
            source: function(term, response){
                var data = {
                    label: term,
                    resultSize: limitedAutocompleteTag
                };
                $.ajax({
                    url: getCustomTagsUrl,
                    method: "GET",
                    dataType: "json",
                    data: data,
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    }
                }).done(function (resp) {
                    var searchResult = [];
                    jQuery.each(resp.data, function (index, value) {
                        searchResult.push(value.code);
                    });
                    response(searchResult);
                });
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
                    tagCount ++;
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
            feedTags = feedTags = removeTagFromList(code, feedTags);
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

        $('#save-feed-button').on('click', function (e) {
            e.preventDefault();
            if (!hasError()) {
                if ($easyInstance) {
                    $easyInstance.easypin.fire("get.coordinates", function (data) {
                        return data;
                    });
                }
                var feedId = $('#feedId').val(),
                    title = $('#title').val(),
                    content = $('#content').val(),
                    feed = {
                        'title': $.trim(title),
                        'author': {'userId': $('#author-select').val()},
                        'content': $.trim(content),
                        'shopTags': shopTags
                    };
                if (feedId) {
                    feed['id'] = feedId;
                    feed['photo'] = $('#photo-viewer').data('photo');
                    if (annotationData) {
                        feed['photo']['annotationData'] = annotationData;
                    }
                    if (feedTags) {
                        feed.feedTags = feedTags;
                    }

                    if (changedPhoto) {
                        if (photoData) {
                            saveFeed(photoData, feed);
                            changedPhoto = false;
                        }
                    }
                    updateFeed(feed);
                } else {
                    if (feedTags) {
                        feed.feedTags = feedTags;
                    }
                    saveFeed($('#photo-uploader')[0].files[0], feed);
                }
            }
        });

        function getNewImageUrl(imageUrl) {
            var photo_url = "";
            if (imageUrl) {
                var url_split = imageUrl.split("/");
                for (var i = 0; i < url_split.length - 2; i++) {
                    photo_url += url_split[i] + "/";
                }
                photo_url += "w_600,c_scale/";
                photo_url += url_split[url_split.length - 1];
            }
            return photo_url;
        }

        sortByProps = function (item1, item2) {
            "use strict";
            var props = [];
            for (var _i = 2; _i < arguments.length; _i++) {
                props[_i - 2] = arguments[_i];
            }

            var cps = [];
            var asc = true;
            if (props.length < 1) {
                for (var p in item1) {
                    if (item1[p] > item2[p]) {
                        cps.push(1);
                        break;
                    } else if (item1[p] === item2[p]) {
                        cps.push(0);
                    } else {
                        cps.push(-1);
                        break;
                    }
                }
            } else {
                for (var i = 0; i < props.length; i++) {
                    var prop = props[i];
                    for (var o in prop) {
                        asc = prop[o] === "asc";
                        if (item1[o] > item2[o]) {
                            cps.push(asc ? 1 : -1);
                            break;
                        } else if (item1[o] === item2[o]) {
                            cps.push(0);
                        } else {
                            cps.push(asc ? -1 : 1);
                            break;
                        }
                    }
                }
            }

            for (var j = 0; j < cps.length; j++) {
                if (cps[j] === 1 || cps[j] === -1) {
                    return cps[j];
                }
            }
            return 0;
        }
    });
})(jQuery);