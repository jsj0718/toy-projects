const user = {
    init : function() {
        const _this = this;

        $('#btn-join').on('click', function() {
            _this.join();
        });
    },
    join : function() {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        const data = {
            username : $('#username').val(),
            password : $('#password').val(),
            email : $('#email').val(),
            role : $('input[name=role]:checked').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v2/joinProc',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token)
            }
        }).done(function() {
            alert('회원가입이 완료되었습니다.');
            window.location.href = '/login';
        }).fail(function (error) {
            const errorMessage = error['responseJSON']['message'];

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
    },
    userDelete : function(id) {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/user/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            beforeSend : function(xhr) {
              xhr.setRequestHeader(header, token)
            }
        }).done(function() {
            alert('계정이 삭제되었습니다.');
            window.location.href = "/admin";
        }).fail(function (error) {
            alert('계정을 삭제하는데 실패했습니다.');
        });
    }
};

user.init();