var user = {
    init : function() {
        var _this = this;

        $('#btn-join').on('click', function() {
            _this.join();
        });

/*
        $('#btn-update').on('click', function() {
            _this.update();
        });

        $('#btn-delete').on('click', function() {
            _this.delete();
        });
*/
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
    },
/*
    update : function() {
        var data = {
            title : $('#title').val(),
            content : $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/board/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function() {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/board/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
*/
};

user.init();