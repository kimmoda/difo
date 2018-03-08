jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var updateStatData = function(statName) {
            var jwtToken = localStorage.getItem('zion-Jwt-Token');
            $.ajax({
                url: upgradeSystemDataStatUrl,
                method: "POST",
                data: JSON.stringify(statName),
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                success: function (response) {
                    $('#upgrade-data-message').text("update " + statName + " count, please check log file for results.");
                },
                error: function (response) {
                    $('#upgrade-data-message').text("update fail. please check the log in the server.");
                }
            });
        };

        $('#upgrade-db-button').on('click', function (e) {
            e.preventDefault();
            var jwtToken = localStorage.getItem('zion-Jwt-Token');
            $.ajax({
                url: upgradeDbUrl,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                success: function (response) {
                	$('#upgrade-message').text('DB upgrading scheduled. please check the log for progress in AWS server instance.');
                },
                error: function (response) {
                	$('#upgrade-message').text('DB upgrading fail. please check the log in the server.');
                }
            });
        });
        
        $('#update-username-button').on('click', function (e) {
            e.preventDefault();
            var oldname = $('#oldUserName').val(),
            newname = $('#newUserName').val();
            var jwtToken = localStorage.getItem('zion-Jwt-Token');
            $.ajax({
                url: updateUserNameUrl,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                data: JSON.stringify({
                    'oldName': oldname,
                    'newName': newname
                }),
                success: function (response) {
                    var msg;
                    if(response.status === 200) {
                        msg = 'Successfully changed user name.';
                    }else {
                        msg = response.msg;
                    }
                    $('#update-username-message').text(msg);
                },
                error: function (response) {
                    $('#update-username-message').text('Update user name fail.' + response.msg);
                }
            });
        });
        
        $('#upgrade-shared-button').on('click', function (e) {
            e.preventDefault();
            updateStatData("SHARED");
        });
        
        $('#upgrade-viewed-button').on('click', function (e) {
            e.preventDefault();
            updateStatData("VIEWED");
        });
        
        $('#upgrade-short-url-button').on('click', function (e) {
            e.preventDefault();
            updateStatData("SHORT_LINK_CLICKED");
        });
        
        $('#upgrade-like-button').on('click', function (e) {
            e.preventDefault();
            updateStatData("LIKED");
        });
        
        $('#upgrade-all-data-button').on('click', function (e) {
            e.preventDefault();
            updateStatData("ALL");
        });
        
        $('#upgrade-fans-count-button').on('click', function (e) {
            e.preventDefault();
            updateStatData("FANS");
        })
        
        $('#generate-feed-comments-button').on('click', function (e) {
            e.preventDefault();
            var jwtToken = localStorage.getItem('zion-Jwt-Token'),
                startDay = $('#start-day').val(),
                endDay = $('#end-day').val(),
                data = {
                    'startDay': startDay ? parseInt(startDay) : 0,
                    'endDay': endDay ? parseInt(endDay) : 0
                };
            
            $.ajax({
                url: generateFeedCommentsUrl,
                method: "POST",
                data: JSON.stringify(data),
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                success: function (response) {
                    $('#generate-feed-comments').text("update feed comments, please check log file for results.");
                },
                error: function (response) {
                    $('#generate-feed-comments').text("update fail. please check the log in the server.");
                }
            });
            
        });
        
        $('#update-predefined-task-type').on('click', function (e) {
            e.preventDefault();
            var jwtToken = localStorage.getItem('zion-Jwt-Token');
            $.ajax({
                url: updateTaskTypeUrl,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                success: function (response) {
                    $('#predefined-task-json').text(JSON.stringify(response));
                },
                error: function (response) {
                    $('#predefined-task-json').text("update fail. please check the log in the server.");
                }
            });
        });
        
        $('#update-common-config').on('click', function (e) {
            e.preventDefault();
            var jwtToken = localStorage.getItem('zion-Jwt-Token');
            $.ajax({
                url: updateCommonConfigUrl,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                success: function (response) {
                    $('#common-config-json').text(JSON.stringify(response));
                },
                error: function (response) {
                    $('#common-config-json').text("update fail. please check the log in the server.");
                }
            });
        });
        
        $('#acc-update-button').on('click', function (e) {
            e.preventDefault();
            var jwtToken = localStorage.getItem('zion-Jwt-Token'),
                username = $('#acc-username').val(),
                income = $('#acc-income').val(),
                expense = $('#acc-expense').val(),
                data = {
                    'username': username,
                    'income': income ? income : 0,
                    'expense': expense ? expense: 0
                };
            
            $.ajax({
                url: updateUserAcc,
                method: "POST",
                data: JSON.stringify(data),
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                success: function (response) {
                    var data = response.data;
                    $('#acc-update-comments').text(JSON.stringify(data));
                },
                error: function (response) {
                    $('#acc-update-comments').text("update fail. please check the log in the server.");
                }
            });
            
        });
    });
})(jQuery);
