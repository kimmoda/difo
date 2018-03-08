var zionValidationService = function () {
    var textValidStatus = {
            valid: 'valid',
            less: 'less',
            over: 'over'
        },
        email = function (element) {
            if (commonService.isString(element)) {
                return _testEmail(element);
            }
            var email = element.email,
                containerId = element.id;
            return (_testEmail(email, containerId));
        },
        _testEmail = function (email, containerId) {
            var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if (re.test(email)) {
                if (containerId) {
                    jQuery(containerId).removeClass('zion-input-error-indicator');
                }
                return true;
            } else {
                if (containerId) {
                    jQuery(containerId).addClass('zion-input-error-indicator');
                }
                return false;
            }
        },
        _isValidTextLength = function (targetElement, errorElement, minLength, maxLength) {
            var text = jQuery.trim(targetElement.val()),
                valid = textValidStatus.valid;
            if (minLength) {
                if (text.length < minLength) {
                    valid = textValidStatus.less;
                }
            }
            if (maxLength) {
                if (text.length > maxLength) {
                    valid = textValidStatus.over;
                }
            }
            return valid;
        };
    var addTextLengthValidationEvent = function (targetElement, errorElement, minLength, maxLength) {
        targetElement.on('change keyup paste', function () {
            var valid = _isValidTextLength(targetElement, errorElement, minLength, maxLength);
            if (valid === textValidStatus.less) {
                targetElement.addClass('zion-input-error-indicator');
                errorElement.text('You must input at least ' + minLength + ' characters.');
                errorElement.removeClass('zion-hide');
            } else if (valid === textValidStatus.over) {
                targetElement.addClass('zion-input-error-indicator');
                errorElement.text('Please shorter your content less than ' + maxLength + ' characters.');
                errorElement.removeClass('zion-hide');
            } else {
                targetElement.removeClass('zion-input-error-indicator');
                errorElement.addClass('zion-hide');
            }
        });
    };
    var validFields = function (fields) {
        var validStatus = true;
        for (var i = 0; i < fields.length; i++) {
            var field = fields[i],
                targetElement = jQuery(field.target),
                errorElement = jQuery(field.error),
                valid = _isValidTextLength(targetElement, errorElement, field.minLength, field.maxLength);
            if (valid === textValidStatus.less) {
                targetElement.addClass('zion-input-error-indicator');
                errorElement.text('You must input at least ' + field.minLength + ' characters.');
                errorElement.removeClass('zion-hide');
                validStatus = false;
            } else if (valid === textValidStatus.over) {
                targetElement.addClass('zion-input-error-indicator');
                errorElement.text('Please shorter your content less than ' + field.maxLength + ' characters.');
                errorElement.removeClass('zion-hide');
                validStatus = false;
            } else {
                targetElement.removeClass('zion-input-error-indicator');
                errorElement.addClass('zion-hide');
            }
        }
        return validStatus;
    };
    var registerFieldEvents = function (fields) {
        jQuery.each(fields, function (index, field) {
            addTextLengthValidationEvent(jQuery(field.target), jQuery(field.error), field.minLength, field.maxLength);
        });
    };

    return {
        email: email,
        addTextLengthValidationEvent: addTextLengthValidationEvent,
        registerFieldEvents: registerFieldEvents,
        validFields: validFields
    }
}