var messageService = function () {
    var $ = jQuery,
        show = function (title, message, okCallback) {
            if (!title) {
                title = "Message";
            }
            $('.message_title').html(title);
            $('.message_content').html(message);
            $('#message_modal').modal('show');
            $('.button_ok').click(function () {
                if (okCallback && $.isFunction(okCallback)) {
                    okCallback();
                }
            })
        },
        hide = function () {
            $('#message_modal').modal('hide');
        };


    return {
        show: show,
        hide: hide
    }

}();