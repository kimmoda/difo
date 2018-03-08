jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        if (window.location.pathname.indexOf('/action/auth/') !== -1) {
        	if (!commonService.isLoggedIn()) {
        		window.location.replace(commonService.getZionErrorUrl());
        	}
        }
    });
})(jQuery);
