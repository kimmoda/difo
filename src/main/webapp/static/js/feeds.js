jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var newPageToken,
            selectUserId,
            jwtToken = localStorage.getItem('zion-Jwt-Token'),
            // listTemplate = function () {
            //     var listItems = $('#listItem-template').html();
            //     return Handlebars.compile(listItems);
            // }(),
            listFeeds = function (nextPageToken) {
                var data = {'resultSize': 20, 'authorRoles': "DESIGNER,STYLIST,INFLUENCER,DEFAULT"};
                if (nextPageToken) {
                    data['nextPageToken'] = nextPageToken;
                }
                if (selectUserId && selectUserId !== 'All') {
                    data['authorId'] = selectUserId;
                }
                zionLoadingService().show('body');
                $.ajax({
                    dataType: "json",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    data: data,
                    type: "GET",
                    url: listFeedsUrl,
                    cache: false,
                    success: function (response) {
                        zionLoadingService().stop('body');
                        var data = response['data'];
                        if (!nextPageToken) {
                            $('#feed-list').empty();
                        }
                        newPageToken = response['nextPageToken'];
                        var newData = [];
                        $.each(data, function (index, feed) {
                            var coverImage = feed['coverImage'] ? feed['coverImage'] : {},
                                creationDate = new Date(feed['creationDate']),
                                data = {
                                    'id': feed['id'],
                                    'title': feed['title'],
                                    'publicId': coverImage['publicId'],
                                    'version': coverImage['version'],
                                    'format': coverImage['format'],
                                    'coverImageUrl': zionImageUtils().smallSquareImage(coverImage.url),
                                    'creationDate': creationDate.getDate() + '-' + creationDate.getMonth() + '-' + creationDate.getFullYear() + ' ' + creationDate.getHours() + ":" + creationDate.getMinutes() + ":" + creationDate.getSeconds(),
                                    'topFeedChecked': feed['sysTags'] && feed['sysTags'].length === 1 && feed['sysTags'][0].code === 'top' ? "selected='selected'" : '',
                                    'goodFeedChecked': feed['sysTags'] && feed['sysTags'].length === 1 && feed['sysTags'][0].code === 'good' ? "selected='selected'" : '',
                                    'fairFeedChecked': feed['sysTags'] && feed['sysTags'].length === 1 && feed['sysTags'][0].code === 'fair' ? "selected='selected'" : '',
                                    'poorFeedChecked': feed['sysTags'] && feed['sysTags'].length === 1 && feed['sysTags'][0].code === 'poor' ? "selected='selected'" : '',
                                    'feedUrl': getFeedUrl + "?id=" + feed['id'] + "&token=" + jwtToken,
                                    'authorName': feed.author.displayName,
                                    'feedStatus': feed.status,
                                    'authorRoles': feed.authorRoles
                                };
                            newData.push(data);
                        });
                        var itemsHTML = Handlebars.templates.superAdminFeedList({data: newData, fileClouldName: fileClouldName});
                        $('#feed-list').append(itemsHTML);
                    },
                    error: function (data) {
                        if (console && console.log) {
                            console.log(data);
                        }
                    }
                });
            },
            initUserList = function () {
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
                        $('#author-select').on('change', function () {
                            selectUserId = this.value;
                            listFeeds(null);

                        })
                    }
                });
            };
        initUserList();
        listFeeds(null);
        $('#show-more').on('click', function (e) {
            e.preventDefault();
            if (newPageToken) {
                listFeeds(newPageToken);
            } else {
                $(this).remove();
            }
        });

        $('#feed-list').on('click', '.zion-delete-feed', function (e) {
            e.preventDefault();
            var feedId = $(this).data('feedid'),
                liEl = $(this).closest('.list-group-item');
            $.ajax({
                url: deleteUrl,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                data: feedId
            }).done(function (e) {
                if (e.status === 200) {
                    liEl.remove();
                }
            });
        });
        
        $('#feed-list').on('change', ".feed-rank-selection", function (e) {
            e.preventDefault();
            var radioButtonGroup = $(this),
                feedId = $(this).data('feedid'),
                code = $(this).val(),
                tags = [
                    {
                        "name": code,
                        "code": code,
                        "codeSystem": "lookorder",
                        "source": "stylehubsys"
                    }
                ];
                data = {
                    "feedId": feedId,
                    "tags": tags
                };
            $.ajax({
                url: updateSysTags,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                data: JSON.stringify(data)
            }).done(function (e) {
                if (e.status === 200) {
                    var feed = e.data;
                    if(feed && feed['sysTags'] && feed['sysTags'].length === 1 && feed['sysTags'][0].code) {
                        radioButtonGroup.val(feed['sysTags'][0].code);
                    }
                    
                }
            });
        });
        
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