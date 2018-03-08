jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var jwtToken = localStorage.getItem('zion-Jwt-Token');
        if (!jwtToken){
            window.location.replace(homePagePath);
        }
        $('#changePwdBtn').on('click', function (e) {
            $('#warning-message').addClass('zion-hide');
            e.preventDefault();
            var oldPassword = $('#old_password').val(),
                newPassword = $('#new_password').val(),
                newPassword2 = $('#new_password2').val();
            if (!(oldPassword && oldPassword.trim().length > 1)) {
                $('#warning-message').removeClass('alert-success').addClass('alert-danger');
                $('#warning-message').removeClass('zion-hide').text("Old password invalid");
                return;
            }
            if (!(newPassword && newPassword.trim().length > 1 && newPassword === newPassword2)) {
                $('#warning-message').removeClass('alert-success').addClass('alert-danger');
                $('#warning-message').removeClass('zion-hide').text("The new password does not match.");
                return;
            }
            $.ajax({
                url: changePwdPath,
                method: "POST",
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Zion-Jwt-Token':jwtToken
                },
                data: JSON.stringify({
                    'oldPs': oldPassword,
                    'newPs': newPassword
                })
            }).done(function (e) {
                if (e.status === 200) {
                    $('#warning-message').removeClass('alert-danger').addClass('alert-success');
                    $('#warning-message').removeClass('zion-hide').text("Update password successful.");
                    window.location.replace(homePagePath + "?token=" + jwtToken);
                } else{
                    $('#warning-message').removeClass('alert-success').addClass('alert-danger');
                    $('#warning-message').removeClass('zion-hide').text(e.msg);
                }
            });

        });
    });
})(jQuery);