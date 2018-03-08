jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        $('#loginBt').on('click', function (e) {
            e.preventDefault();
            var username = $('#username').val(),
                password = $('#password').val();
            $.ajax({
                url: loginPath,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: JSON.stringify({
                    'username': username,
                    'password': password
                })
            }).done(function (e) {
                if (e.status === 200 && e.jwtToken) {
                    var jwtToken = e.jwtToken;
                    localStorage.setItem('zion-Jwt-Token', jwtToken);
                    redirectToAdmin(homePagePath);
                } else {
                    $('#warning-message').removeClass('zion-hide');
                    $('#warning-message').text("Username and password are invalid.");
                }
            });
        });
    });
})(jQuery);
