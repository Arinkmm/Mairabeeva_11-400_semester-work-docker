<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Вход</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-login.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/">RaceTalk</a>
</nav>

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


<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
