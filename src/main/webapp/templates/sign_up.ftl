<#include "/templates/base.ftl">

<#macro title>RaceTalk — Регистрация</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/sign_up.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/">RaceTalk</a>
    </nav>
</#macro>

<#macro hero>
    <section class="hero d-flex justify-content-start align-items-center hero-login">
        <div class="hero-content">
            <h1 class="hero-title">Регистрация</h1>
            <p class="hero-desc">Создайте аккаунт, чтобы присоединиться к сообществу фанатов Формулы-1!</p>

            <form id="signup-form" method="post" action="${contextPath}/sign_up" class="p-4 rounded shadow-sm login-form">
                <div class="mb-3">
                    <label class="form-label fw-bold text-danger">Имя пользователя</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="Придумайте имя" required />
                    <div id="username-feedback" class="text-danger fw-bold mt-2"></div>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-bold text-danger">Пароль</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Введите пароль" required />
                    <div id="password-feedback" class="text-danger fw-bold mt-2"></div>
                </div>

                <div class="d-grid">
                    <button type="submit" id="signup-button" class="btn btn-main">Зарегистрироваться</button>
                </div>

                <p class="mt-3 text-muted">
                    Уже есть аккаунт? <a href="${contextPath}/login" class="text-danger fw-bold">Войдите</a>
                </p>
            </form>
        </div>
    </section>
</#macro>

<#macro additionalScripts>
    <script>const contextPath = '${contextPath}';</script>
    <script src="${contextPath}/assets/js/sign_up.js"></script>
</#macro>