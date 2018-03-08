/**
 * @param config
 *
 * config = config ? config : {
            container: '#stylist-list',
            showMore: '#show-more',
            showSummary: true,
            showRating: true,
            url : listUsersUrl,
            customCss: '',
            grid: 'col-md-4'
        }
 */
var layoutStylist = function (config, emptyCallback) {
    var isLoading = false,
        loading = jQuery('.zion-loading').zionLoading(),
        nextPageToken = null,
        config = config ? config : {
            pub: true,
            container: '#stylist-list',
            showMore: '#show-more',
            showSummary: true,
            showRating: true,
            url: listUsersUrl,
            customCss: '',
            grid: 'col-md-4'
        },
        listUsers = function (pageToken) {
            isLoading = true;
            loading.show();
            var data = {'resultSize': 20},
                roles = commonService.getTrendsetterFilterRoles();
            if (pageToken) {
                data['nextPageToken'] = pageToken;
            }
            if (config.pub === false) {
                zionWebService.get(config.url, data, function (response) {
                    processData(response);
                }, function (data) {
                    loading.hide();
                    isLoading = false;
                    if (console && console.log) {
                        console.log(data);
                    }
                });
            } else {
                if (roles.length !== 0) {
                    data['roles'] = roles.join(',');
                    data['minWorkCount'] = 0;
                }
                if (!jQuery.isEmptyObject(commonService.getTrendsetterSelectedCountry())) {
                    data['countryCode'] = commonService.getTrendsetterSelectedCountry().code;
                    data['minWorkCount'] = 0;
                }

                zionWebService.getPub(config.url, data, function (response) {
                    processData(response);
                }, function (data) {
                    loading.hide();
                    isLoading = false;
                    if (console && console.log) {
                        console.log(data);
                    }
                });
            }
        },
        processData = function (response) {
            var data = response['data'],
                users = [],
                userList;
            loading.hide();
            if (data.length === 0 && nextPageToken === null) {
                if (emptyCallback && jQuery.isFunction(emptyCallback)) {
                    emptyCallback();
                    return;
                }
            }
            jQuery.each(data, function (index, user) {
                var summary = {
                    'stylistUrl': stylistUrl + '?userId=' + user.id,
                    'avatar': user.person.avatar,
                    'displayName': user.displayName,
                    'location': user.location,
                    'serviceSummary': user.serviceSummary,
                    'introSummary': user.introSummary,
                    'feedCount': user.workCount,
                    'roles': user.userRoles,
                    'approvedTrendsetter': user.approvedTrendsetter
                };
                users.push(summary);
            });
            userList = Handlebars.templates.stylistcard({'users': users, 'config': config});
            nextPageToken = response['nextPageToken'];
            jQuery(config.container).append(userList);
            if (!nextPageToken) {
                jQuery(config.showMore).remove();
            }

            isLoading = false;
        };
    listUsers(null);

    commonService.winScroll(function () {
        if (!isLoading) {
            if (nextPageToken === null || nextPageToken) {
                listUsers(nextPageToken);
            }
        }
    });
}