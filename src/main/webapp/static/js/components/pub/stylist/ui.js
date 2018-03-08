jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var countryDict = {},
            config = {
                container: '#stylist-list',
                showMore: '#show-more',
                showSummary: true,
                showRating: false,
                url: listUsersUrl,
                customCss: '',
                grid: 'col-md-4'
            },
            initFilter = function () {
                var filterHtml = Handlebars.templates.trendsetterFilter();
                $('#tag-filter-container').append(filterHtml);
                setSelectedTag();
                toggleTagScrollBtn();
            },
            setFilterStatus = function () {
                if ($('#tag-filter-container').hasClass('zion-closed')) {
                    $('#tag-filter-container').removeClass('zion-closed');
                } else {
                    $('#tag-filter-container').addClass('zion-closed');
                }
                $('#tag-filter-container').slideToggle(500);
                $('#filter-toggle-button').find('i').toggleClass('fa-angle-down fa-angle-up');
                $('.filter-display').toggleClass('filter-hide');
                toggleTagScrollBtn();
            },
            toggleTagScrollBtn = function () {
                if (parseInt($('#tags-area').get(0).scrollWidth, 10) - 1 > parseInt($('#tags-area').innerWidth(), 10)) {
                    $('.tag-scroll-btn').removeClass('zion-hide');
                } else {
                    $('.tag-scroll-btn').addClass('zion-hide');
                }
            },
            setFilterNum = function () {
                var roleNumber = commonService.getTrendsetterFilterRoles().length;
                if (!$.isEmptyObject(commonService.getTrendsetterSelectedCountry())) {
                    roleNumber = roleNumber + 1;
                }
                if (roleNumber > 0) {
                    $('.filter-num').text(roleNumber).addClass('filter-num-show');
                } else {
                    $('.filter-num').removeClass('filter-num-show');
                }
            },
            setSelectedTag = function () {
                $('#tags-area').empty();
                $('#author-roles-filter').find('input').prop('checked', false);
                var roles = commonService.getTrendsetterFilterRoles();
                for (var i = 0; i < roles.length; i++) {
                    var role = '<li class="role" data-role-name="' + roles[i] + '" data-role-code="' + roles[i] + '">' +
                        '<span class="content">' + roles[i] + '</span>' +
                        '<span class="role-delete-btn">&#10005;</span>' +
                        '</li>';
                    $('#tags-area').append(role);
                    $('#' + roles[i].toLowerCase()).prop('checked', true);
                }

                if (roles.length === $('#author-roles-filter').find('input').length - 1) {
                    $('#all').prop('checked', true);
                }

                var selectedCountry = commonService.getTrendsetterSelectedCountry();
                if (selectedCountry.code && selectedCountry.code.toUpperCase() !== "ALL") {
                    role = '<li class="role" data-country-code="' + selectedCountry.code + '">' +
                        '<span class="content">' + selectedCountry.value + '</span>' +
                        '<span class="country-delete-btn">&#10005;</span>' +
                        '</li>';
                    $('#tags-area').append(role);
                }
                toggleTagScrollBtn();
            },
            deleteFilter = function (code, deleteElement) {
                commonService.removeTrendsetterFilterRole(code);
                deleteElement.remove();
                setFilterNum();
                toggleTagScrollBtn();
            },
            initCountryFilter = function () {
                zionWebService.getPub(zionUrls.rest_trendsetter_country, null, function (resp) {
                    if (resp.status === 200) {
                        $('.location-options').empty();
                        var countryList = resp.data,
                            selectedCountry = commonService.getTrendsetterSelectedCountry();
                        countryList.splice(0, 0, {
                            code: 'ALL',
                            value: 'All'
                        });
                        $.each(resp.data, function (index, value) {
                            var optionHtml,
                                countryName = value.value,
                                countryCode = value.code;
                            if (value.code === selectedCountry.code) {
                                optionHtml = '<option value="' + countryCode + '" selected>' + countryName + '</option>';
                            } else {
                                optionHtml = '<option value="' + countryCode + '">' + countryName + '</option>';
                            }
                            $('.location-options').append(optionHtml);
                            countryDict[countryCode] = countryName;
                        })
                    }
                })
            },
            init = function () {
                layoutStylist(config,function () {
                    $('.empty-message').show();
                });
                initFilter();
                initCountryFilter();
                setFilterNum();
            };

        $('#tag-filter-container').on("change", ".author-roles-item", function () {
            var target = $(this);
            if (this.checked) {
                if (target.data('role-code') === "all") {
                    $('#author-roles-filter').find('input').prop('checked', true);
                } else {
                    var checkedNum = $('#author-roles-filter').find('input:checked').length;
                    if (checkedNum === $('#author-roles-filter').find('input').length - 1) {
                        $('#all').prop('checked', true);
                    }
                }
            } else {
                if (target.data('role-code') === "all") {
                    $('#author-roles-filter').find('input:checked').prop('checked', false);
                } else {
                    $('#all').prop('checked', false);
                }
            }
        })
            .on('click', '#filter-submit-btn', $.debounce(1000, true, function () {
                $('#tags-area').empty();
                commonService.removeAllTrendsetterFilterRole();
                $.each($('#author-roles-filter').find('.author-roles-item:checked'), function () {
                    if ($(this).data('role-code') !== 'all') {
                        var code = $(this).data('role-code');
                        commonService.addTrendsetterFilterRole(code);
                    }
                });

                var selectCountry = $('.location-options').val();
                if (selectCountry.toUpperCase() === "ALL") {
                    commonService.removeTrendsetterSelectedCountry();
                } else {
                    var selectedCountry = {
                        code: selectCountry,
                        value: countryDict[selectCountry]
                    };
                    commonService.setTrendsetterSelectedCountry(selectedCountry);
                }
                window.location.replace(zionUrls.action_trendsettersList);
            }))
            .on('click', '#filter-select-cancel-btn', function () {
                setFilterStatus();
                setSelectedTag();
                initCountryFilter();
            })

            .on('click', '.title', function () {
                $(this).parents('.filter-container').toggleClass('style-filter-show');
                $(this).find('i').toggleClass('fa-plus-square-o fa-minus-square-o');
            });


        $('#role-filter-container').on('click', '.zion-filter-tag', $.debounce(1000, true, function (e) {
            var target = $(this),
                role = target.data('role-code');
            if (target.hasClass('active')) {
                target.removeClass('active');
                commonService.removeTrendsetterFilterRole(role);
            } else {
                target.addClass('active');
                commonService.addTrendsetterFilterRole(role);
            }
            window.location.replace(zionUrls.action_trendsettersList);
        }));

        $('#filter-toggle-button').on('click', function () {
            setFilterStatus();
        });

        $('.filter-panel-container').on('click', '.role-delete-btn', $.debounce(500, true, function () {
            var deleteRoleCode = $(this).parents('.role').data('role-code'),
                deleteRoleElement = $(this).parents('.role');
            deleteFilter(deleteRoleCode, deleteRoleElement);
            window.location.replace(zionUrls.action_trendsettersList);
        }))
            .on('click', '.country-delete-btn', $.debounce(500, true, function () {
                commonService.removeTrendsetterSelectedCountry();
                window.location.replace(zionUrls.action_trendsettersList);
            }));

        $('#reset-filter-button').on('click', $.debounce(1000, true, function () {
            $('#tags-area').empty();
            $('.filter-num').text('').removeClass('filter-num-show');
            $('.user-roles-filter').find('input').prop('checked', false);
            commonService.removeAllTrendsetterFilterRole();
            commonService.removeTrendsetterSelectedCountry();
            window.location.replace(zionUrls.action_trendsettersList);
        }));

        init();
    });
})(jQuery);
