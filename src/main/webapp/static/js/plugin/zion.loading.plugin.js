(function ($) {
    var target,
        template_spiner = '<div class="page-load-status">\n' +
            '            <div class="spinner">\n' +
            '                <div class="bounce1"></div>\n' +
            '                <div class="bounce2"></div>\n' +
            '                <div class="bounce3"></div>\n' +
            '            </div>\n' +
            '        </div>',
        show = function () {
            if (target) {
                $(target).show();
            }
        },
        hide = function () {
            if (target) {
                $(target).hide();
            }
        },
        options = {
            effect: 'spinner'
        };

    $.fn.zionLoading = function (config) {
        options = $.extend({}, options, config);
        target = this;
        if (options.effect.toLocaleLowerCase() === 'spinner') {
            $(this).empty().append(template_spiner);
        }
        $(this).hide();
        return {
            show: show,
            hide: hide
        }
    }
}(jQuery));
