$(document).ready(function() {
    function updateSubmitButton() {
        if ($('#username-feedback').is(':visible') || $('#password-feedback').is(':visible')) {
            $('#signup-button').prop('disabled', true);
        } else {
            $('#signup-button').prop('disabled', false);
        }
    }

    $('#username').on('input', function() {
        let username = $(this).val();
        if (username.length < 3) {
            $('#username-feedback').text('Имя пользователя должно быть не менее 3 символов').show();
            updateSubmitButton();
            return;
        }
        $.getJSON(contextPath + '/validate/username', {username: username}, function(data) {
            if (!data.valid) {
                $('#username-feedback').text('Имя пользователя должно содержать от 3 до 20 латинских букв или цифры от 0 до 9').show();
                updateSubmitButton();
            } else {
                $.getJSON(contextPath + '/validate/username-unique', {username: username}, function(resp) {
                    if (!resp.unique) {
                        $('#username-feedback').text('Это имя пользователя уже занято').show();
                    } else {
                        $('#username-feedback').hide().text('');
                    }
                    updateSubmitButton();
                });
            }
        });
    });

    $('#password').on('input', function() {
        let password = $(this).val();
        $.getJSON(contextPath + '/validate/password', {password: password}, function(data) {
            if (!data.valid) {
                $('#password-feedback').text('Пароль должен содержать от 6 до 30 букв и цифр').show();
            } else {
                $('#password-feedback').hide().text('');
            }
            updateSubmitButton();
        });
    });

    $('#signup-form').on('submit', function(e) {
        if ($('#username-feedback').is(':visible') || $('#password-feedback').is(':visible')) {
            e.preventDefault();
        }
    });
});
