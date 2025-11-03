<#include "/templates/base.ftl">

<#macro title>RaceTalk — Чат</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/chat.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    </nav>
</#macro>

<#macro content>
    <div class="chat-container">
        <h2 class="chat-title mb-3">Чат</h2>
        <div id="messages" class="msg-list"></div>
        <form id="chatForm" class="chat-form d-flex gap-2 align-items-center mt-4">
            <input id="messageInput" class="form-control flex-grow-1" autocomplete="off" type="text" placeholder="Введите сообщение..." required />
            <button class="btn px-4" type="submit">Отправить</button>
        </form>
    </div>
</#macro>

<#macro additionalScripts>
    <script>const contextPath = '${contextPath}';</script>
    <script>var isAdmin = ${isAdmin?string("true", "false")};</script>
    <script src="${contextPath}/assets/js/chat.js"></script>
</#macro>