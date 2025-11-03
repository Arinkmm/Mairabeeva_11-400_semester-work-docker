$(document).ready(function() {
    function renderMessage(msg) {
        var html =
            '<div class="msg d-flex align-items-center mb-2" data-id="' + msg.id + '">' +
            '<a href="' + contextPath + '/user/profile/' + msg.userId + '" class="d-flex align-items-center text-decoration-none">' +
            '<img src="' + (msg.photo || (contextPath + '/assets/images/user-default.png')) + '" class="rounded-circle me-2 photo" />' +
            '<span class="msg-user">' + msg.username + '</span>' +
            '</a>' +
            '<span class="msg-content ms-2 flex-grow-1">' + msg.content + '</span>' +
            '<span class="msg-time ms-2">' + msg.createdAt.replace('T', ' ').substring(0, 16) + '</span>';

        if (isAdmin === true) {
            html += '<button class="btn btn-danger btn-sm ms-2 delete-msg" data-id="' + msg.id + '">Удалить</button>';
        }

        html += '</div>';

        $('#messages').append(html);
        $('#messages').scrollTop($('#messages')[0].scrollHeight);
    }

    function loadMessages() {
        $('#messages').empty();
        $.getJSON(contextPath + '/chat/handle', function(data) {
            $.each(data, function(i, msg) {
                renderMessage(msg);
            });
        });
    }

    $('#chatForm').submit(function(e) {
        e.preventDefault();
        var text = $('#messageInput').val().trim();
        $.post(contextPath + '/chat/handle', { message: text }, function(data) {
            if (data.success) {
                $('#messageInput').val('');
                loadMessages();
            } else {
                alert(data.error);
            }
        }, 'json');
    });

    $('#messages').on('click', '.delete-msg', function() {
        var msgId = $(this).data('id');
        $.post(contextPath + '/chat/handle', { action: 'delete', id: msgId }, function(data) {
            if (data.success) {
                loadMessages();
            } else {
                alert(data.error);
            }
        }, 'json');
    });

    loadMessages();
});