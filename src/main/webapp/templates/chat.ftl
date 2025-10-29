<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Чат</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-chat.css" rel="stylesheet" />
</head>

<body>
<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
</nav>

<main class="container my-5">
    <div class="chat-container">
        <h2 class="chat-title mb-3">Чат</h2>
        <div id="messages" class="msg-list"></div>
        <form id="chatForm" class="chat-form d-flex gap-2 align-items-center mt-4">
            <input id="messageInput" class="form-control flex-grow-1" autocomplete="off" type="text" placeholder="Введите сообщение..." required />
            <button class="btn px-4" type="submit">Отправить</button>
        </form>
    </div>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>const contextPath = '${contextPath}';</script>
<script src="${contextPath}/assets/js/chat.js"></script>

</body>
</html>
