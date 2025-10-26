$(document).ready(function() {
    function renderMessage(msg) {
        $('#messages').append(
            '<div class="msg">' +
            '<span class="msg-user">' + (msg.username) + '</span>' +
            '<span>' + (msg.content) + '</span>' +
            '<span class="msg-time">' + (msg.createdAt.replace('T', ' ').substring(0,16)) + '</span>' +
            '</div>'
        );
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
        if (!text) return;
        $.post(contextPath + '/chat/handle', {message: text}, function(data) {
            if (data.success) {
                renderMessage({
                    username: data.username,
                    content: text,
                    createdAt: new Date().toISOString()
                });
                $('#messageInput').val('');
            }
        }, 'json');
    });

    loadMessages();
});