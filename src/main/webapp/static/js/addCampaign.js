jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var jwtToken = localStorage.getItem('zion-Jwt-Token'),
            updateFields = function (task) {
                hideErrorBanner();
                $('#taskId').val(task.id);
                $('#title').val(task.title);
                $('#authorname').val(task.creator.username);
                $('#content').val(task.description);
                $('#campaign-objective-select').val(task.taskType.id);
                $('#post-url').val(task.postUrl);
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
                    fieldNodes = [$('#title'), $('#authorname'), $('#post-url'), $('#campaign-objective-select'), $('#content')];
                for(var i=0; i < fieldNodes.length; i++) {
                    if (!fieldNodes[i].val()) {
                        fieldNodes[i].parent().addClass('has-error');
                        error = true;
                    } else {
                        fieldNodes[i].parent().removeClass('has-error');
                    }
                }
                if (error) {
                    showErrorBanner('Please fix required fields.');
                } else {
                    hideErrorBanner();
                }
                return error;
            },
            initTask = function() {
                if (updatedTaskId) {
                    $.ajax({
                        url: getTaskUrl + '?id=' + updatedTaskId,
                        method: "GET",
                        dataType: "json",
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json',
                            'Zion-Jwt-Token': jwtToken
                        },
                        success: function(e) {
                            var task = e.data;
                            if(e.status === 200) {
                                updateFields(task);
                            }else {
                                showErrorBanner(e.msg);
                            }
                        },
                        error: function(e) {
                            $('#action-throbber').addClass('zion-hide');
                            $('#save-task-button').attr("disabled", false);
                            showErrorBanner(e.msg);
                        }
                    });
                }
            },
            init = function () {
                $.ajax({
                    url: getTaskTypeUrl,
                    method: "GET",
                    dataType: "json",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Zion-Jwt-Token': jwtToken
                    },
                    success: function(e) {
                        if (e.status === 200) {
                            hideErrorBanner();
                            var types = e.data.subTypes;
                            $('#campaign-objective-select').empty();
                            for (var index = 0; index < types.length; index++) {
                                var item = types[index];
                                $('#campaign-objective-select').append("<option value='" + item.id + "'>" + item.description + "</option>")
                            }
                            initTask();
                        }else {
                            showErrorBanner(e.msg);
                        }
                    },
                    error: function(e) {
                        $('#action-throbber').addClass('zion-hide');
                        $('#save-task-button').attr("disabled", false);
                        showErrorBanner(e.msg);
                    }
                });
            },
            saveOrUpdateTask = function (task) {
                $('#action-throbber').removeClass('zion-hide');
                $('#save-task-button').attr("disabled", true);
                $.ajax({
                    dataType: "json",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Zion-Jwt-Token': jwtToken
                    },
                    data: JSON.stringify(task),
                    type: "POST",
                    cache: false,
                    url: createOrUpdateTaskUrl,
                    success: function (response) {
                        var task = response.data;
                        $('#action-throbber').addClass('zion-hide');
                        $('#save-task-button').attr("disabled", false);
                        if (response.status === 200) {
                            updateFields(task);
                        } else {
                            showErrorBanner(response.msg);
                        }
                    },
                    error: function (response) {
                        $('#action-throbber').addClass('zion-hide');
                        $('#save-task-button').attr("disabled", false);
                        showErrorBanner(response.msg);
                    }
                });
            };
        init();

        $('#save-task-button').on('click', function (e) {
            e.preventDefault();
            if (!hasError()) {
                var taskId = $('#taskId').val(),
                    title = $('#title').val(),
                    content = $('#content').val(),
                    postUrl = $('#post-url').val(),
                    authorname = $('#authorname').val(),
                    taskTypeId = $('#campaign-objective-select').val(),
                    task = {
                        'id': taskId,
                        'title': $.trim(title),
                        'authorname': $.trim(authorname),
                        'description': $.trim(content),
                        'postUrl': $.trim(postUrl),
                        'taskTypeId': taskTypeId
                    };
                saveOrUpdateTask(task);
            }
        });

    });
})(jQuery);