<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Регистрация</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/RaceTalk_war/assets/css/page-sign_up.css" rel="stylesheet" />

</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3" aria-label="Главное меню">
    <a class="navbar-brand" href="${contextPath}/">RaceTalk</a>
</nav>

<section class="hero d-flex justify-content-start align-items-center hero-signup">
    <div class="hero-content">
        <h1 class="hero-title">Регистрация</h1>
        <p class="hero-desc">Создайте аккаунт, чтобы присоединиться к сообществу фанатов Формулы-1!</p>

        <form id="signup-form" method="post" action="${contextPath}/sign_up" class="p-4 rounded shadow-sm signup-form">
            <div class="mb-3">
                <label for="username" class="form-label fw-bold text-danger">Имя пользователя</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Придумайте имя" required />
                <div id="username-feedback" class="text-danger fw-bold mt-2"></div>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label fw-bold text-danger">Пароль</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Введите пароль" required />
                <div id="password-feedback" class="text-danger fw-bold mt-2"></div>
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-main">Зарегистрироваться</button>
            </div>

            <p class="mt-3 text-muted">
                Уже есть аккаунт? <a href="${contextPath}/login" class="text-danger fw-bold">Войдите</a>
            </p>
        </form>
    </div>
</section>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>const contextPath = '${contextPath}';</script>
<script src="${contextPath}/assets/js/sign_up.js"></script>

</body>
</html>