jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var userProfile,
            init = function () {
                var fields,
                    validationService = zionValidationService();
                //initial date picker
                jQuery('#zion-dob').datepicker({
                    'autoclose': true,
                    'enableOnReadonly': true,
                    'format': 'dd/M/yyyy'
                });

                //set date start display time
                // jQuery('#zion-dob').datepicker('setDate', '01/01/1990');

                //init the error events
                fields = [{
                    'target': '#zion-firstName',
                    'error': '#firstName-error-indicator',
                    'minLength': 0,
                    'maxLength': 50
                },
                    {'target': '#zion-lastName', 'error': '#lastName-error-indicator', 'minLength': 0, 'maxLength': 50},
                    {
                        'target': '#zion-introduce',
                        'error': '#introduce-error-indicator',
                        'minLength': 10,
                        'maxLength': 500
                    },
                    {'target': '#zion-email', 'error': '#email-error-indicator', 'minLength': 0, 'maxLength': 50},
                    {
                        'target': '#zion-address1',
                        'error': '#address1-error-indicator',
                        'minLength': 0,
                        'maxLength': 200
                    },
                    {'target': '#zion-phone1', 'error': '#phone1-error-indicator', 'minLength': 0, 'maxLength': 50},
                    {
                        'target': '#zion-facebook',
                        'error': '#facebook-error-indicator',
                        'minLength': 0,
                        'maxLength': 200
                    },
                    {
                        'target': '#zion-instagram',
                        'error': '#instagram-error-indicator',
                        'minLength': 0,
                        'maxLength': 200
                    },
                    {'target': '#zion-youtube', 'error': '#youtube-error-indicator', 'minLength': 0, 'maxLength': 200},
                    {'target': '#zion-website', 'error': '#website-error-indicator', 'minLength': 0, 'maxLength': 200}
                ];

                validationService.registerFieldEvents(fields);

                userProfileDetail.loadUser(function (user) {
                    userProfile = user;
                    // person
                    $('#zion-wallet-address').val(userProfile.walletAddress);
                    $('#zion-title').val(userProfile.person.title);
                    $('#zion-firstName').val(userProfile.person.firstName);
                    $('#zion-lastName').val(userProfile.person.lastName);
                    if (userProfile.person.gender) {
                        $('#zion-gender-code').val(userProfile.person.gender.code);
                        $("#zion-" + userProfile.person.gender.value.toLowerCase()).prop('checked', true);
                    }
                    if (userProfile.person.dob) {
                        $('#zion-dob').datepicker('setDate', new Date(userProfile.person.dob));
                    }
                    $('#zion-introduce').val(userProfile.person.introduce);
                    $('#zion-avatar').val(userProfile.person.avatar);
                    // contact
                    $('#zion-address1').val(userProfile.contact.address1);
                    $('#zion-email').val(userProfile.contact.email);
                    $('#zion-suburb').val(userProfile.contact.suburb);
                    $('#zion-city').val(userProfile.contact.city);
                    $('#zion-state').val(userProfile.contact.state);
                    $('#zion-country').val(userProfile.contact.country.value);
                    $('#zion-country-code').val(userProfile.contact.country.code);
                    $('#zion-postcode').val(userProfile.contact.postcode);
                    $('#zion-phone1').val(userProfile.contact.phone1);
                    $('#zion-facebook').val(userProfile.contact.facebook);
                    $('#zion-instagram').val(userProfile.contact.instagram);
                    $('#zion-youtube').val(userProfile.contact.youtube);
                    $('#zion-website').val(userProfile.contact.website);
                });

                zionGoogleLocationService.changePlaceListener(function () {
                    var addressComponents = zionGoogleLocationService.getAddress();
                    if (addressComponents && addressComponents.length !== 0) {
                        $('#zion-country').val(addressComponents.country);
                        $('#zion-country-code').val(addressComponents.countryCode);
                        $('#zion-city').val(addressComponents.city);
                        $('#zion-suburb').val(addressComponents.suburb);
                        $('#zion-state').val(addressComponents.state);
                        $('#zion-postcode').val(addressComponents.postcode);
                    }
                });

                autosize($('textarea'));


                //submit user profile detail
                $('#zion-update-user-profile').on('click', $.debounce(1000, true, function (e) {
                    e.preventDefault();

                    if (!userProfile.id || !userProfile.username) {
                        zionNotifacationService.error('Internal service error.')
                        return;
                    }
                    // TODO: validate mandatory fields
                    if (validationService.validFields(fields)) {

                        userProfile.person.title = $('#zion-title').val();
                        userProfile.person.firstName = $('#zion-firstName').val();
                        userProfile.person.lastName = $('#zion-lastName').val();
                        userProfile.walletAddress = $('#zion-wallet-address').val();
                        // convert to date
                        userProfile.person.dob = new Date($('#zion-dob').val()).getTime();
                        userProfile.person.introduce = $('#zion-introduce').val();
                        userProfile.person.avatar = $('#zion-avatar').val();
                        userProfile.person.introduce = $('#zion-introduce').val();
                        if ($("input[name='gender']:checked").val()) {
                            userProfile.person.gender = {
                                code: $("input[name='gender']:checked").val().toUpperCase(),
                                value: $("input[name='gender']:checked").val()
                            };
                        }
                        // contact
                        userProfile.contact.address1 = $('#zion-address1').val();
                        userProfile.contact.email = $('#zion-email').val();
                        userProfile.contact.suburb = $('#zion-suburb').val();
                        userProfile.contact.city = $('#zion-city').val();
                        userProfile.contact.state = $('#zion-state').val();
                        userProfile.contact.country.value = $('#zion-country').val();
                        userProfile.contact.country.code = $('#zion-country-code').val();
                        userProfile.contact.postcode = $('#zion-postcode').val();
                        userProfile.contact.placeId = zionGoogleLocationService.getPlaceId();
                        userProfile.contact.phone1 = $('#zion-phone1').val();
                        userProfile.contact.facebook = $('#zion-facebook').val();
                        userProfile.contact.instagram = $('#zion-instagram').val();
                        userProfile.contact.youtube = $('#zion-youtube').val();
                        userProfile.contact.website = $('#zion-website').val();

                        userProfileDetail.updateUser(userProfile, function () {
                            jQuery('.success-check').removeClass('zion-hide').addClass('message-display');
                            setTimeout(function () {
                                jQuery('.success-check').addClass('zion-hide').removeClass('message-display');
                            }, 1000);

                        });
                    } else {
                        $('#submit-fail-message').removeClass('zion-hide');
                        setTimeout(function () {
                            $('#submit-fail-message').addClass('zion-hide');
                        }, 10000);
                    }
                }));
            };

        init();

        $('#zion-dob').on('focus', function () {
            $('body').addClass('body-relative');
        });

        $('#zion-address1').on('focus', function () {
            $('body').removeClass('body-relative');
        });

        $('#change-avatar-button').click(function () {
            zionUploadPhotoWidget().uploadAvatarWithCallback(function (photoInfo) {
                if (photoInfo && photoInfo[0] && photoInfo[0].secure_url) {
                    var url = zionImageUtils().avatarUrl(photoInfo[0].secure_url);
                    userProfileDetail.changeAvatar(url, function (resp) {
                        location.reload();
                    })
                }
            });
        });
    });
})(jQuery);