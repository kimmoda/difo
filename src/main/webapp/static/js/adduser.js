jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var jwtToken,
            userID,
            updateUserTopRecommend = function() {
                var userTags = [];
                if ($('#top-recommend-selection').is(":checked")) {
                    userTags.push({
                        "name": "TOP",
                        "code": "top",
                        "codeSystem": "trendsetterorder",
                        "source": "stylehubsys"
                    });
                }
                var data = {
                    userId: userID,
                    tags: userTags
                };
                $.ajax({
                    url: addUserTagPath,
                    method: "POST",
                    dataType: "json",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Zion-Jwt-Token': jwtToken
                    },
                    data: JSON.stringify(data)
                }).done(function (e) {
                    if (e.status === 200) {

                    }
                });

            },
            testEmail = function (email) {
                if (validateEmail(email)) {
                    $("#email").removeClass('zion-input-error-indicator');
                    $('#email-notice').addClass('hidden');
                    return true;
                } else {
                    $("#email").addClass('zion-input-error-indicator');
                    $('#email-notice').removeClass('hidden');
                    return false;
                }
            },
            isUrl = function (urlStr) {
                var isHttp = urlStr.match(/http:\/\/.+/);
                var isHttps = urlStr.match(/https:\/\/.+/);
                return !(isHttp === null && isHttps === null);
            }
        ;

        $(function () {
            jwtToken = localStorage.getItem('zion-Jwt-Token');
            var params = url('?');
            if (params) {
                userID = params.userID;
                if (userID) {
                    $('#role-info').removeClass('hidden');
                    $('#account-info').addClass('hidden');
                    loadUserInformation(userID);
                }

                $('.experience').addel({});
                $('.certification').addel({});
                $('.award').addel({});
                $('.partnership').addel({});
                $('.client').addel({});
                $('.service').addel({
                    events: {
                        added: function (event) {
                            autosize(document.querySelectorAll('textarea'));
                        }
                    }
                });
                autosize(document.querySelectorAll('textarea'));
            }
            zionGoogleLocationService.changePlaceListener(function () {
                $('#suburb').val("");
                $('#city').val("");
                $('#country').val("");
                var addressComponents = zionGoogleLocationService.getAddress();
                if (addressComponents && addressComponents.length !== 0) {
                    $('#country').data("countryCode", addressComponents.countryCode)
                    $('#country').val(addressComponents.country);
                    $('#city').val(addressComponents.city);
                    $('#suburb').val(addressComponents.sublocality);
                    $('#state').val(addressComponents.administrative_area1);
                }
            });
        });

        $('#email').on('input', function () {
            var email = $('#email').val();
            if (email && email.trim().length > 0) {
                if (!validateEmail(email)) {
                    $('#email-container').addClass('has-error');
                } else {
                    $('#email-container').removeClass('has-error');
                }
            } else {
                $('#email-container').removeClass('has-error');
            }
        });

        $('#add-user-button').on('click', function (e) {
            e.preventDefault();
            if (!jwtToken) {
                $('#message').text("Login first, please.");
                $('#modal').modal('show');
                return;
            }
            var title = $('#title').val(),
                avatar = $('#avatar').val(),
                preferName = $('#prefer-name').val(),
                firstName = $('#first-name').val(),
                middleName = $('#middle-name').val(),
                lastName = $('#last-name').val(),
                validate = true,
                username = $('#username').val();
            if (username.trim().length === 0) {
                $('#message').text("Username is empty.");
                $('#modal').modal('show');
                return;
            }
            var gender;
            if ($('input[name = gender]:checked').length > 0) {
                gender = $('input[name = gender]:checked').val();
            }
            var email = $('#email').val(),
                address = $('#address').val(),
                suburb = $('#suburb').val(),
                city = $('#city').val(),
                state = $('#state').val(),
                phone = $('#phone').val(),
                mobile = $('#mobile').val(),
                website = $('#website').val(),
                facebook = $('#facebook').val(),
                youtube = $('#youtube').val(),
                instagram = $('#instagram').val(),
                introduce = $('#introduce').val();

            // Added experience
            var experience = [];
            $("input[name='experience[]']").each(function (index, item) {
                    if (!$(this).prop("disabled")) {
                        if ($.trim($(this).val()).length > 0) {
                            experience.push($(this).val());
                        }
                    }
                }
            );

            // Added certification
            var certification = [];
            $("input[name='certification[]']").each(function (index, item) {
                    if (!$(this).prop("disabled")) {
                        if ($.trim($(this).val()).length > 0) {
                            certification.push($(this).val());
                        }
                    }
                }
            );

            // Added award
            var award = [];
            $("input[name='award[]']").each(function (index, item) {
                    if (!$(this).prop("disabled")) {
                        if ($.trim($(this).val()).length > 0) {
                            award.push($(this).val());
                        }
                    }
                }
            );

            // Added partnership
            var partnership = [];
            $("input[name='partnership[]']").each(function (index, item) {
                    if (!$(this).prop("disabled")) {
                        if ($.trim($(this).val()).length > 0) {
                            partnership.push($(this).val());
                        }
                    }
                }
            );

            // Added client
            var client = [];
            $("input[name='client[]']").each(function (index, item) {
                    if (!$(this).prop("disabled")) {
                        if ($.trim($(this).val()).length > 0) {
                            client.push($(this).val());
                        }
                    }
                }
            );

            // Added service
            var services = [];
            var serviceTargets = $("input[name='service[]']");
            var priceTargets = $("input[name='price[]']");
            var durationTargets = $("input[name='duration[]']");
            var descTargets = $("textarea[name='desc[]']");
            var serviceEnable = true;
            serviceTargets.each(function (index, item) {
                    if (!$(this).prop("disabled")) {
                        var service = {};
                        var serviceName = $(this).val();
                        if (serviceName && $.trim(serviceName).length > 0) {
                            service["serviceName"] = serviceName;
                            var price = Number($(priceTargets[index]).val());
                            if (isNaN(price)) {
                                serviceEnable = false;
                                $(priceTargets[index]).addClass('red-border');
                            } else {
                                $(priceTargets[index]).removeClass('red-border');
                            }
                            if (price && $.trim(price).length > 0) {
                                service["price"] = {
                                    price: price,
                                    currency: "NZD"
                                };
                            }
                            var duration = Number($(durationTargets[index]).val());
                            if (isNaN(duration)) {
                                serviceEnable = false;
                                $(durationTargets[index]).addClass('red-border');
                            } else {
                                $(durationTargets[index]).removeClass('red-border');
                            }
                            if ($.trim($(durationTargets[index]).val()).length > 0) {
                                service["duration"] = $(durationTargets[index]).val();
                            }
                            if ($.trim($(descTargets[index]).val()).length > 0) {
                                service["describe"] = $(descTargets[index]).val();
                            }
                            services.push(service);
                        }
                    }
                }
            );

            if (!serviceEnable) {
                $('#message').text("Service field error.");
                $('#modal').modal('show');
                return false;
            }

            if (email && email.length > 0) {
                if (!testEmail(email)) {
                    validate = false;
                }
            }

            if (website && website.length > 0) {
                if (!isUrl(website)) {
                    $('#website-container').addClass('has-error');
                    $('#website-notice').removeClass('hidden');
                    validate = false;
                } else {
                    $('#website-container').removeClass('has-error');
                    $('#website-notice').addClass('hidden');
                }
            }

            if (facebook && facebook.length > 0) {
                if (!isUrl(facebook)) {
                    $('#facebook-container').addClass('has-error');
                    $('#facebook-notice').removeClass('hidden');
                    validate = false;
                } else {
                    $('#facebook-container').removeClass('has-error');
                    $('#facebook-notice').addClass('hidden');
                }
            }

            if (youtube && youtube.length > 0) {
                if (!isUrl(youtube)) {
                    $('#youtube-container').addClass('has-error');
                    $('#youtube-notice').removeClass('hidden');
                    validate = false;
                } else {
                    $('#youtube-container').removeClass('has-error');
                    $('#youtube-notice').addClass('hidden');
                }
            }

            if (instagram && instagram.length > 0) {
                if (!isUrl(instagram)) {
                    $('#instagram-container').addClass('has-error');
                    $('#instagram-notice').removeClass('hidden');
                    validate = false;
                } else {
                    $('#instagram-container').removeClass('has-error');
                    $('#instagram-notice').addClass('hidden');
                }
            }

            var personInfo = {
                title: title,
                avatar: avatar,
                firstName: firstName,
                middleName: middleName,
                lastName: lastName,
                gender: {
                    code: gender.toUpperCase(),
                    value: gender
                },
                experience: experience,
                certification: certification,
                award: award,
                partnership: partnership,
                client: client,
                introduce: $.trim(introduce),
                preferName: $.trim(preferName)
            };

            var contactInfo = {
                email: email,
                address1: address,
                suburb: suburb,
                city: city,
                state: state,
                phone1: phone,
                website: website,
                facebook: facebook,
                youtube: youtube,
                instagram: instagram,
                mobile: mobile
            };
            if ($("#country").data("countryCode")) {
                contactInfo.country = {'code': $("#country").data("countryCode")};
            }
            var person = formatPersonInformation(personInfo);
            var contact = formatContactInformation(contactInfo);

            var data = {
                username: username
            };

            if (person) {
                data.person = person;
            }

            if (contact) {
                data.contact = contact;
            }

            if (services.length > 0) {
                data['service'] = services;
            }
            var url = adduserPath;
            if (userID) {
                url = updatePath;
                data.id = userID;
            }
            if (!validate) {
                return;
            }
            $.ajax({
                url: url,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                data: JSON.stringify(data)
            }).done(function (e) {
                $('#message').text(e.msg);
                $('#modal').modal('show');
                if (e.status === 200) {
                    if (userID) {
                        updateStatus();
                    } else {
                        window.location.href = homePagePath + "?token=" + jwtToken;
                    }
                }
            });
        });

        function formatPersonInformation(personInfo) {
            var person = {};
            if (personInfo.title && $.trim(personInfo.title).length > 0) {
                person['title'] = personInfo.title;
            }
            if (personInfo.avatar && $.trim(personInfo.avatar).length > 0) {
                person['avatar'] = personInfo.avatar;
            }
            if (personInfo.firstName && $.trim(personInfo.firstName).length > 0) {
                person['firstName'] = personInfo.firstName;
            }
            if (personInfo.middleName && $.trim(personInfo.middleName).length > 0) {
                person['middleName'] = personInfo.middleName;
            }
            if (personInfo.lastName && $.trim(personInfo.lastName).length > 0) {
                person['lastName'] = personInfo.lastName;
            }
            if (personInfo.experience && personInfo.experience.length > 0) {
                person['experience'] = personInfo.experience;
            }
            if (personInfo.certification && personInfo.certification.length > 0) {
                person['certification'] = personInfo.certification;
            }
            if (personInfo.award && personInfo.award.length > 0) {
                person['award'] = personInfo.award;
            }
            if (personInfo.partnership && personInfo.partnership.length > 0) {
                person['partnership'] = personInfo.partnership;
            }
            if (personInfo.client && personInfo.client.length > 0) {
                person['client'] = personInfo.client;
            }
            if (personInfo.introduce && personInfo.introduce.length > 0) {
                person['introduce'] = personInfo.introduce;
            }
            if (personInfo.preferName && personInfo.preferName.length > 0) {
                person['preferName'] = personInfo.preferName;
            }
            person['gender'] = personInfo.gender;
            return person;
        }

        function formatContactInformation(contactInfo) {
            var contact = {};
            if (contactInfo.email && $.trim(contactInfo.email).length > 0) {
                contact['email'] = contactInfo.email;
            }
            if (contactInfo.address1 && $.trim(contactInfo.address1).length > 0) {
                contact['address1'] = contactInfo.address1;
            }
            if (contactInfo.suburb && $.trim(contactInfo.suburb).length > 0) {
                contact['suburb'] = contactInfo.suburb;
            }
            if (contactInfo.city && $.trim(contactInfo.city).length > 0) {
                contact['city'] = contactInfo.city;
            }
            if (contactInfo.country) {
                contact['country'] = contactInfo.country;
            }
            if (contactInfo.state) {
                contact['state'] = contactInfo.state;
            }
            if (contactInfo.phone1 && $.trim(contactInfo.phone1).length > 0) {
                contact['phone1'] = contactInfo.phone1;
            }
            if (contactInfo.website && $.trim(contactInfo.website).length > 0) {
                contact['website'] = contactInfo.website;
            }
            if (contactInfo.facebook && $.trim(contactInfo.facebook).length > 0) {
                contact['facebook'] = contactInfo.facebook;
            }
            if (contactInfo.youtube && $.trim(contactInfo.youtube).length > 0) {
                contact['youtube'] = contactInfo.youtube;
            }
            if (contactInfo.instagram && $.trim(contactInfo.instagram).length > 0) {
                contact['instagram'] = contactInfo.instagram;
            }
            if (contactInfo.mobile && $.trim(contactInfo.mobile).length > 0) {
                contact['mobile'] = contactInfo.mobile;
            }

            if (isEmptyObject(contact)) {
                return null;
            }

            return contact;


        }

        function updateRoles() {
            var roles = [];
            if ($('#admin').is(":checked")) {
                roles.push('ADMIN');
            }
            if ($('#stylist').is(":checked")) {
                roles.push('STYLIST');
            }
            if ($('#designer').is(":checked")) {
                roles.push('DESIGNER');
            }
            if ($('#influencer').is(":checked")) {
                roles.push('INFLUENCER');
            }
            var data = {
                id: userID,
                username: $('#username').val(),
                userRoles: roles
            };
            $.ajax({
                url: updateRolesPath,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                data: JSON.stringify(data)
            }).done(function (e) {
                if (e.status === 200) {
                    updateUserTopRecommend();
                }
            });

        }

        function updateStatus() {
            var status = $("input[type=radio][name=status]:checked").val();
            var data = {
                id: userID,
                username: $('#username').val(),
                enabled: status === "enable"
            };
            $.ajax({
                url: updateStatusPath,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                data: JSON.stringify(data)
            }).done(function (e) {
                if (e.status === 200) {
                    updateRoles();
                }
            });
        }

        function loadUserInformation(userID) {
            $.ajax({
                url: loadUserPath + "?id=" + userID,
                method: "GET",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).done(function (e) {
                if (e.status === 200) {
                    var u = e.data;
                    $('.page-header').text(u.displayName);
                    $('#username').val(u.username);
                    $('#registration-social-media-info').val(u.registrationSocialMedias);
                    $('#preview-request-info').val(u.requestedInfo);
                    if (u.person) {
                        $('#user-name-display').val(u.username);
                        $('#title').val(u.person.title);
                        $('#avatar').val(u.person.avatar);
                        $('#first-name').val(u.person.firstName);
                        $('#middle-name').val(u.person.middleName);
                        $('#last-name').val(u.person.lastName);
                        $('#introduce').val(u.person.introduce);
                        $('#prefer-name').val(u.person.preferName);
                    }
                    if (u.contact) {
                        $('#email').val(u.contact.email);
                        $('#address').val(u.contact.address1);
                        $('#suburb').val(u.contact.suburb);
                        $('#city').val(u.contact.city);
                        if (u.contact.country) {
                            $('#country').val(u.contact.country.value);
                            $('#country').data('countryCode', u.contact.country.code);
                        }
                        $('#state').val(u.contact.state);
                        $('#phone').val(u.contact.phone1);
                        $('#mobile').val(u.contact.mobile);
                        $('#website').val(u.contact.website);
                        $('#facebook').val(u.contact.facebook);
                        $('#youtube').val(u.contact.youtube);
                        $('#instagram').val(u.contact.instagram);
                    }
                    $("input[name=mygroup]").val([5]);
                    $("input[name=roles]").val(u.userRoles);
                    if (u.tags && u.tags.length ===1 && u.tags[0].code === 'top') {
                        $('#top-recommend-selection').prop("checked", true );
                    }else {
                        $('#top-recommend-selection').prop("checked", false );
                    }
                    if (u.userRoles) {
                        if (u.registrationStatus === "PENDING") {
                            $('#apply-user-button').removeClass('hidden');
                        }
                        $('#reject-user-button').removeClass('hidden');
                    }
                    $("input[name=status]").val([u.enabled ? "enable" : "disable"]);
                    if (u.person.gender) {
                        $("input[name=gender]").val([u.person.gender.value]);
                    }

                    var experience = u.person.experience ? u.person.experience : [];
                    for (var i = 0; i < experience.length; i++) {
                        $("#add-experience").click();
                    }
                    $("input[name='experience[]']").each(function (index, item) {
                            $(this).val(experience[index]);
                        }
                    );

                    var certification = u.person.certification ? u.person.certification : [];
                    for (var i = 0; i < certification.length; i++) {
                        $("#add-certification").click();
                    }
                    $("input[name='certification[]']").each(function (index, item) {
                            $(this).val(certification[index]);
                        }
                    );

                    var award = u.person.award ? u.person.award : [];
                    for (var i = 0; i < award.length; i++) {
                        $("#add-award").click();
                    }
                    $("input[name='award[]']").each(function (index, item) {
                            $(this).val(award[index]);
                        }
                    );

                    var partnership = u.person.partnership ? u.person.partnership : [];
                    for (var i = 0; i < partnership.length; i++) {
                        $("#add-partnership").click();
                    }
                    $("input[name='partnership[]']").each(function (index, item) {
                            $(this).val(partnership[index]);
                        }
                    );

                    var client = u.person.client ? u.person.client : [];
                    for (var i = 0; i < client.length; i++) {
                        $("#add-client").click();
                    }
                    $("input[name='client[]']").each(function (index, item) {
                            $(this).val(client[index]);
                        }
                    );

                    var services = u.service ? u.service : [];
                    for (var i = 0; i < services.length; i++) {
                        $("#add-service").click();
                    }
                    var serviceTargets = $("input[name='service[]']");
                    var priceTargets = $("input[name='price[]']");
                    var durationTargets = $("input[name='duration[]']");
                    var descTargets = $("textarea[name='desc[]']");
                    serviceTargets.each(function (index, item) {
                            var service = services[index];
                            //The widget contains an empty input service by default. However, services may be empty array.
                            //So the service can be undefined.
                            if (service) {
                                $(this).val(service.serviceName);
                                if (service.price) {
                                    $(priceTargets[index]).val(service.price.price);
                                }
                                $(durationTargets[index]).val(service["duration"]);
                                $(descTargets[index]).val(service["describe"]);
                            }
                        }
                    );
                    autosize.update($('textarea'));
                }
            });
        }

        $('#apply-user-button').click(function () {
            zionLoadingService().show();
            var data = {
                userId: userID,
                redirectUrl: webConfig.actionUrl + '/signin'
            };
            $.ajax({
                url: stylistApplyUrl,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                data: JSON.stringify(data)
            }).done(function (e) {
                zionLoadingService().stop();
                $('#apply-user-button').addClass('hidden');
            });
        });

        $('#reject-user-button').click(function () {
            var requestedInfo = $('#requested-registration-info').val(),
                data = {
                    'rejectedUserId': userID
                };
            if (requestedInfo && $.trim(requestedInfo)) {
                data['requestedInfo'] = $.trim(requestedInfo);
            }
            $.ajax({
                url: stylistRejectUrl,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token': jwtToken
                },
                data: JSON.stringify(data)
            }).done(function (e) {
                $('#stylist').prop('checked', false);
                $('#designer').prop('checked', false);
                $('#influencer').prop('checked', false);
            });
        });

        $("textarea").on('paste', function () {
            autosize.update($('textarea'));
        });

        function validateEmail(email) {
            var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(email);
        }

        function isEmptyObject(e) {
            var t;
            for (t in e)
                return !1;
            return !0
        }


    });
})(jQuery);
