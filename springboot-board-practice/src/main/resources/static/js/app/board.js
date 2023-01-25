const board = {
    init : function() {
        const _this = this;

        $('#btn-save').on('click', function() {
            _this.save();
        });

        $('#btn-update').on('click', function() {
            _this.update();
        });

        $('#btn-delete').on('click', function() {
            _this.delete();
        });
    },
    save : function() {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        const data = {
            title : $('#title').val(),
            content : $('#content').val(),
            author : $('#author').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v2/board',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token)
            }
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            const errorMessage = error['responseJSON']['message'];

            if (typeof errorMessage === "string") {
                $('#board-save-exception').text(errorMessage);
            } else {
                $('#board-save-exception').text('');
            }

            if (errorMessage.hasOwnProperty('title')) {
                $('#board-save-title-exception').text(errorMessage['title']);
            } else {
                $('#board-save-title-exception').text('');
            }

            if (errorMessage.hasOwnProperty('author')) {
                $('#board-save-author-exception').text(errorMessage['author']);
            } else {
                $('#board-save-author-exception').text('');
            }

            if (errorMessage.hasOwnProperty('content')) {
                $('#board-save-content-exception').text(errorMessage['content']);
            } else {
                $('#board-save-content-exception').text('');
            }
        });
    },
    update : function() {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        const data = {
            title : $('#title').val(),
            content : $('#content').val()
        };

        const id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v2/board/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token)
            }
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = "/";
        }).fail(function (error) {
            const errorMessage = error['responseJSON']['message'];

            if (typeof errorMessage === "string") {
                $('#board-update-exception').text(errorMessage);
            } else {
                $('#board-update-exception').text('');
            }

            if (errorMessage.hasOwnProperty('title')) {
                $('#board-update-title-exception').text(errorMessage['title']);
            } else {
                $('#board-update-title-exception').text('');
            }

            if (errorMessage.hasOwnProperty('content')) {
                $('#board-update-content-exception').text(errorMessage['content']);
            } else {
                $('#board-update-content-exception').text('');
            }
        });
    },
    delete : function() {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        const id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/board/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token)
            }
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = "/";
        }).fail(function (error) {
            alert('글을 삭제하는데 실패했습니다.');
        });
    }
};

board.init();