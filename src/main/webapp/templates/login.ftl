<#include "/templates/base.ftl">

<#macro title>RaceTalk — Вход</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/login.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/">RaceTalk</a>
    </nav>
</#macro>

<#macro hero>
    <section class="hero d-flex justify-content-start align-items-center hero-login">
        <div class="hero-content">
            <h1 class="hero-title">Вход</h1>
            <p class="hero-desc">Авторизуйтесь, чтобы вернуться в мир Формулы 1 и общаться с фанатами!</p>

            <form action="${contextPath}/login" method="post" class="p-4 rounded shadow-sm login-form">
                <div class="mb-3">
                    <label class="form-label fw-bold text-danger">Имя пользователя</label>
                    <input type="text" class="form-control" name="username" placeholder="Ваше имя" required>
                </div>
                <div class="mb-4">
                    <label class="form-label fw-bold text-danger">Пароль</label>
                    <input type="password" class="form-control" name="password" placeholder="Введите пароль" required>
                </div>
                <div class="d-grid">
                    <button type="submit" class="btn btn-main">Войти</button>
                </div>
                <p class="mt-3 text-muted">
                    Нет аккаунта? <a href="${contextPath}/sign_up" class="text-danger fw-bold">Зарегистрируйтесь</a>
                </p>

                <#if LoginErrorMessage??>
                    <div class="text-danger fw-bold mt-3">${LoginErrorMessage}</div>
                <#else>
                    <div class="text-danger fw-bold mt-3 hidden"></div>
                </#if>
            </form>
        </div>
    </section>
</#macro>