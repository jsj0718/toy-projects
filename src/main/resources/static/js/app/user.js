var user = {
    init : function() {
        var _this = this;

        $('#btn-join').on('click', function() {
            _this.join();
        });
    },
    join : function() {
        var data = {
            username : $('#username').val(),
            password : $('#password').val(),
            email : $('#email').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/joinProc',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('회원가입이 완료되었습니다.');
            window.location.href = '/login';
        }).fail(function (error) {
            var errorMessage = error['responseJSON']['message'];

            if (typeof errorMessage === "string") {
                $('#join-exception').text(errorMessage);
            } else {
                $('#join-exception').text('');
            }

            if (errorMessage.hasOwnProperty('username')) {
                $('#username-exception').text(errorMessage['username']);
            } else {
                $('#username-exception').text('');
            }

            if (errorMessage.hasOwnProperty('password')) {
                $('#password-exception').text(errorMessage['password']);
            } else {
                $('#password-exception').text('');
            }

            if (errorMessage.hasOwnProperty('email')) {
                $('#email-exception').text(errorMessage['email']);
            } else {
                $('#email-exception').text('');
            }
        });
    }
};

user.init();