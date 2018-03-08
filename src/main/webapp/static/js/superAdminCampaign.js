jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var newPageToken,
            jwtToken = localStorage.getItem('zion-Jwt-Token'),
            formatDate = function(date) {
                var month = date.getMonth() + 1;
                return date.getDate() + '-' + month + '-' + date.getFullYear() + ' ' + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
            },
            listCampaigns = function (nextPageToken) {
                var data = {'resultSize': 20};
                if (nextPageToken) {
                    data['nextPageToken'] = nextPageToken;
                }
                $.ajax({
                    dataType: "json",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    data: data,
                    type: "GET",
                    url: listCampaignUrl,
                    cache: false,
                    success: function (response) {
                        var data = response['data'];
                        if (!nextPageToken) {
                            $('#Campaign-list').empty();
                        }
                        newPageToken = response['nextPageToken'];
                        var newData = [];
                        $.each(data, function (index, task) {
                            var linkPreview = task['postUrlPreview'],
                                coverImageUrl,
                                creationDate = new Date(task['creationDate']),
                                expiredDate = new Date(task['expiredDate']),
                                data = {
                                    'id': task['id'],
                                    'title': task['title'],
                                    'postUrl': task['postUrl'],
                                    'creationDate': formatDate(creationDate),
                                    'expiredDate': formatDate(expiredDate),
                                    'updateCampaignUrl': createOrUpdateCampaignUrl + "?id=" + task['id'] + "&token=" + jwtToken,
                                    'authorName': task.creator.displayName,
                                    'taskStatus': task.taskStatus
                                };
                            if (linkPreview) {
                                if (linkPreview['image']) {
                                    data['coverImageUrl'] = linkPreview['image'];
                                }else if (linkPreview['favicon']) {
                                    data['coverImageUrl'] = linkPreview['favicon'];
                                }
                            }
                            newData.push(data);
                        });
                        var itemsHTML = Handlebars.templates.superAdminCampaignList({data: newData});
                        $('#Campaign-list').append(itemsHTML);
                    },
                    error: function (data) {
                        if (console && console.log) {
                            console.log(data);
                        }
                    }
                });
            };
            
        listCampaigns(null);
        $('#show-more').on('click', function (e) {
            e.preventDefault();
            if (newPageToken) {
                listCampaigns(newPageToken);
            } else {
                $(this).remove();
            }
        });

        $('#Campaign-list').on('click', '.zion-delete-campaign', function (e) {
            e.preventDefault();
            var taskId = $(this).data('campaignid'),
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
                data: taskId
            }).done(function (e) {
                if (e.status === 200) {
                    liEl.remove();
                }
            });
        });
        
    });
})(jQuery);