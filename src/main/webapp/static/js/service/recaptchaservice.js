var g_recaptchaSuccess,
    g_recaptchaFailed,
    grecaptchaID,

    // Callback function to be executed when the user submits a successful CAPTCHA response.
    // The user's response, g-recaptcha-response, will be the input for your callback function.
    recaptchaCallback = function (resp) {
        if (grecaptchaID !== undefined){
            recaptchaService().reset();
        }
        if (resp && commonService.isString(resp)) {
            zionWebService.post(zionUrls.rest_reCaptcha, resp, function (resp) {
                var result = resp.data;
                if (result) {
                    if (g_recaptchaSuccess && jQuery.isFunction(g_recaptchaSuccess)) {
                        g_recaptchaSuccess(resp);
                    }
                } else {
                    if (g_recaptchaFailed && jQuery.isFunction(g_recaptchaFailed)) {
                        g_recaptchaFailed(resp);
                    }
                }
            })
        }
    },

    // Callback function to be executed once g-rechaptcha library have loaded.
    recaptchaInit = function () {

        // Renders the container as a reCAPTCHA widget and returns the ID of the newly created widget.
        // More information at https://developers.google.com/recaptcha/docs/invisible
        grecaptchaID = grecaptcha.render(
            "grecaptcha-id",
            {
                "sitekey": '6LfJAjcUAAAAAEQytFcroYb7r9idrE-lK1VXf23b',
                "callback": "recaptchaCallback",
                "size": "invisible"
            });
    },
    recaptchaService = function () {
        var success = function (success) {
                if (success && jQuery.isFunction(success)) {
                    g_recaptchaSuccess = success;
                }
            },
            failed = function (failedCallback) {
                if (failedCallback && jQuery.isFunction(failedCallback)) {
                    g_recaptchaFailed = failedCallback;
                }
            },
            resetService = function () {
                grecaptcha.reset(grecaptchaID);
            },
            fire = function () {
                // Programatically invoke the reCAPTCHA check.
                // Used if the invisible reCAPTCHA is on a div instead of a button.
                grecaptcha.execute(grecaptchaID);
            };
        return {
            success: success,
            failed: failed,
            reset: resetService,
            fire: fire
        }
    };

