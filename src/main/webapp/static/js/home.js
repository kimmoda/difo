jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var jwtToken = localStorage.getItem('zion-Jwt-Token'),
            list = 'all',
            currentUserList = [],
            searchKeySet = {},
            columns = [
                {title: "#"},
                {title: "creationDate"},
                {title: "Display Name"},
                {title: "Real Name"},
                {title: "Title"},
                {title: "Email"},
                {title: "Roles"},
                {title: "Status"}
            ],
            table = $('#user-list-table').DataTable({
                columns: columns,
                pagingType: "full_numbers",
                paging: true,
                order: [[0, "desc"]],
                info: false,
                lengthMenu: [[10, 25, 50, 100, 200, 250, 300, 350, 400, 450, 500, -1], [10, 25, 50, 100, 200, 250, 300, 350, 400, 450, 500, "All"]]
            }),
            loadData = function (q) {
                var url;
                currentUserList.splice(0, currentUserList.length);
                if (list === 'all') {
                    url = listUsersUrl;
                } else if (list === 'pending') {
                    url = pendingStylistUrl;
                }
                if (q) {
                    url = url + '?q=' + q + '&';
                }
                $.ajax({
                    url: url,
                    method: "GET",
                    dataType: "json",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Zion-Jwt-Token': jwtToken
                    }
                }).done(function (e) {
                    if (e.status === 200) {
                        var users = e.data;
                        table.clear().draw();
                        for (var i = 0; i < users.length; i++) {
                            var currentUser = [],
                                user = users[i],
                                firstName = user.person.firstName ? user.person.firstName : "",
                                middleName = user.person.middleName ? user.person.middleName : "",
                                lastName = user.person.lastName ? user.person.lastName : "",
                                fullName = firstName + " " + middleName + " " + lastName,
                                title = user.person.title ? user.person.title : "Unknown",
                                email = user.contact.email ? user.contact.email : "Unknown",
                                phone = user.contact.phone1 ? user.contact.phone1 : '',
                                address = user.contact.address1 ? user.contact.address1 : '',
                                website = user.contact.website ? user.contact.website : '',
                                facebook = user.contact.facebook ? user.contact.facebook : '',
                                instagram = user.contact.instagram ? user.contact.instagram : '',
                                displayName = user.displayName,
                                creationDate = new Date(user.creationDate),
                                creationDateText = creationDate.getDate() + '-' + (creationDate.getMonth() + 1) + '-' + creationDate.getFullYear(),
                                roleBuilder = '',
                                roles = "None";

                            if ($.trim(fullName).length === 0) {
                                fullName = "Unknown"
                            }

                            if (user.userRoles) {
                                $.each(user.userRoles, function (index, value) {
                                    roleBuilder = roleBuilder + " '" + value + "' ";
                                });
                                if (roleBuilder && user.registrationStatus) {
                                    roles = roleBuilder + ' (' + user.registrationStatus + ') ';
                                }
                            }
                            if (user.userRoles && !user.userRoles.includes("PUPPET")
                                && !user.userRoles.includes("ADMIN")
                                && !user.userRoles.includes("SYSADMIN")) {
                                // populate user data for export csv file
                                currentUser.push(JSON.stringify(creationDateText));
                                if (user.userRoles.includes("INFLUENCER") || user.userRoles.includes("STYLIST") || user.userRoles.includes("DESIGNER")) {
                                    currentUser.push('Trendsetter');
                                } else {
                                    currentUser.push(JSON.stringify('General User'));
                                }
                                if (email !== "Unknown") {
                                    currentUser.push(JSON.stringify(email));
                                } else {
                                    currentUser.push('');
                                }
                                if (firstName) {
                                    currentUser.push(JSON.stringify(firstName));
                                } else {
                                    currentUser.push('');
                                }
                                if (lastName) {
                                    currentUser.push(JSON.stringify(lastName));
                                } else {
                                    currentUser.push('');
                                }
                                if (phone) {
                                    currentUser.push(JSON.stringify(phone));
                                } else {
                                    currentUser.push('');
                                }
                                //status to empty for now
                                currentUser.push("");
                                //date contact
                                currentUser.push("");
                                //ip
                                currentUser.push('');
                                // user agent
                                currentUser.push('');
                                // date subscribed
                                currentUser.push('');
                                // date unsubscribed
                                currentUser.push('');
                                // location
                                if (address) {
                                    currentUser.push(JSON.stringify(address));
                                } else {
                                    currentUser.push('');
                                }
                                // web
                                if (website) {
                                    currentUser.push(JSON.stringify(website));
                                } else {
                                    currentUser.push('');
                                }

                                if (facebook) {
                                    currentUser.push(JSON.stringify(facebook));
                                } else {
                                    currentUser.push('');
                                }
                                if (instagram) {
                                    currentUser.push(JSON.stringify(instagram));
                                } else {
                                    currentUser.push('');
                                }
                                //brand name
                                currentUser.push("");
                                //tags
                                if (user.userRoles && user.userRoles.length > 0) {
                                    var userRoleStr = user.userRoles.join(',');
                                    currentUser.push(JSON.stringify(userRoleStr));
                                }
                                currentUserList.push(currentUser);
                            }
                            table.row.add([
                                i + 1,
                                creationDateText,
                                filterXSS(displayName),
                                filterXSS(fullName),
                                filterXSS(title),
                                filterXSS(email),
                                filterXSS(roles),
                                user.enabled ? "Enable" : "Disable",
                                user.id
                            ]).draw(false);
                        }
                    }
                });
            },
            search = function () {
                var string = "",
                    keys = Object.keys(searchKeySet);
                $.each(keys, function (index, value) {
                    string += " ";
                    string += value;
                });

                table.search($.trim(string)).draw();
            },
            keywordAdd = function (keyword) {
                searchKeySet[keyword] = true;
            },
            keywordDelete = function (keyword) {
                delete searchKeySet[keyword];
            };
        $(".zion-header-check").change(function () {
            var checkbox = $(this).children('input');
            var keyword = checkbox.val().toLowerCase();
            if (checkbox[0].checked) {
                keywordAdd(keyword);
            } else {
                keywordDelete(keyword);
            }
            search();
        });

        $("#export-user-csv").on('click', function () {
            var csv = '"CreationDate", "List Name",Email,"First Name","Last Name","Phone Number",Status,"Date Contact Record Created","IP Address","User Agent","Date Subscribed","Date Unsubscribed","*Location (City, Country)",*Website,*Facebook,*Instagram,"*Brand Name",Tags\n';
            $.each(currentUserList, function (index, user) {
                csv += user.join(',');
                csv += "\n";
            });
            var hiddenElement = document.createElement('a');
            hiddenElement.href = 'data:text/csv;charset=utf-8,' + encodeURI(csv);
            hiddenElement.target = '_blank';
            hiddenElement.download = 'user.csv';
            hiddenElement.click();
        });
        
        $("#export-trendsetter-user-csv").on('click', function () {
            var csv = '"CreationDate", "List Name",Email,"First Name","Last Name","Phone Number",Status,"Date Contact Record Created","IP Address","User Agent","Date Subscribed","Date Unsubscribed","*Location (City, Country)",*Website,*Facebook,*Instagram,"*Brand Name",Tags\n';
            $.each(currentUserList, function (index, user) {
                if (user.indexOf('Trendsetter') > -1) {
                    csv += user.join(',');
                    csv += "\n";
                }
            });
            var hiddenElement = document.createElement('a');
            hiddenElement.href = 'data:text/csv;charset=utf-8,' + encodeURI(csv);
            hiddenElement.target = '_blank';
            hiddenElement.download = 'trendsetter.csv';
            hiddenElement.click();
        });

        $('#user-search-btn').on('click', function (e) {
            var q = $('#user-search-q').val();
            loadData(q);
        });

        $('#user-list-table tbody').on('click', 'td', function () {
            var rowData = table.row(this).data();
            var userID = rowData[rowData.length - 1];
            window.open(updatePath + "?userID=" + userID + '&token=' + jwtToken);

        });
        loadData();

        $('#add-new-user').click(function () {
            window.location.href = updatePath + '?token=' + jwtToken;
        })
    });
})(jQuery);
