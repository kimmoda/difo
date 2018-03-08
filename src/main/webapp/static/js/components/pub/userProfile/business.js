var userProfileDetail = (function () {
    var user = {
            id: '',
            walletAddress: '',
            userRoles: [],
            username: '',
            contact: {
                'email': '',
                'address1': '',
                'suburb': '',
                'city': '',
                'country': {
                    code: '',
                    value: '',
                },
                'state': '',
                'postcode': '',
                'phone1': '',
                'mobile': '',
                'website': '',
                'facebook': '',
                'instagram': '',
                'youtube': '',
                'blog': ''
            },
            person: {
                'title': '',
                'firstName': '',
                'lastName': '',
                'gender': {
                    code: '',
                    value: '',
                },
                'avatar': '',
                'dob': '',
                'introduce': ''
            }
        },
        loadUser = function (callback) {
            zionWebService.get(zionUrls.rest_me, callback, function (response) {
                if (response.data) {
                    user.id = response.data.id;
                    user.userRoles = response.data.userRoles;
                    user.username = response.data.username;
                    user.walletAddress = response.data.walletAddress;
                    var person = response.data.person;
                    if (person) {
                        user.person = {
                            'title': person.title,
                            'firstName': person.firstName,
                            'lastName': person.lastName,
                            'gender': {
                                'code': person.gender ? person.gender.code : '',
                                'value': person.gender ? person.gender.value : ''
                            },
                            'avatar': person.avatar,
                            'dob': person.dob,
                            'introduce': person.introduce
                        }
                    }
                    var contact = response.data.contact;
                    if (contact) {
                        user.contact = {
                            'email': contact.email,
                            'address1': contact.address1,
                            'suburb': contact.suburb,
                            'city': contact.city,
                            'country': {
                                'code': contact.country ? contact.country.code : '',
                                'value': contact.country ? contact.country.value : ''
                            },
                            'state': contact.state,
                            'postcode': contact.postcode,
                            'phone1': contact.phone1,
                            'mobile': contact.mobile,
                            'website': contact.website,
                            'facebook': contact.facebook,
                            'instagram': contact.instagram,
                            'youtube': contact.youtube,
                            'blog': contact.blog
                        }
                    }
                    callback(user);
                }
            });
        },
        updateUser = function (userProfile, callback) {
            zionWebService.post(zionUrls.rest_updateUser, userProfile, function (resp) {
                // zionNotifacationService.success("Your profile updated.");
                commonService.saveUserInfo(resp.data);
                callback();
            });
        },
        //TODO: requires to be changed
        validate = function (userProfile) {
            var isValid = true;
            if (userProfile.firstName.value) {
                jQuery('#' + userProfile.firstName.id).removeClass('zion-input-warning');
            } else {
                isValid = false;
                jQuery('#' + userProfile.firstName.id).addClass('zion-input-warning');
            }
            if (userProfile.lastName.value) {
                jQuery('#' + userProfile.lastName.id).removeClass('zion-input-warning');
            } else {
                isValid = false;
                jQuery('#' + userProfile.lastName.id).addClass('zion-input-warning');
            }

            if (userProfile.location.value) {
                jQuery('#' + userProfile.location.id).removeClass('zion-input-warning');
                jQuery('.google-location-server-error').addClass('hidden');
            } else {
                isValid = false;
                jQuery('#' + userProfile.location.id).addClass('zion-input-warning');
                jQuery('.google-location-server-error').removeClass('hidden');
            }

            if (userProfile.gender.value) {
                jQuery('#' + userProfile.gender.id).removeClass('zion-input-warning');
            } else {
                isValid = false;
                jQuery('#' + userProfile.gender.id).addClass('zion-input-warning');
            }

            return isValid;
        },
        changeAvatar = function (photoUrl, callback) {
            zionWebService.post(zionUrls.rest_avatarChange, photoUrl, function (resp) {
                if (callback && jQuery.isFunction(callback)){
                    callback(resp);
                }
            })
        };

    return {
        validate: validate,
        loadUser: loadUser,
        updateUser: updateUser,
        changeAvatar: changeAvatar
    }

})();