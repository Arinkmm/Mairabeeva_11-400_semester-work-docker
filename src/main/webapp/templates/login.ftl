<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Вход</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/RaceTalk_war/assets/css/race_talk.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3" aria-label="Главное меню">
    <a class="navbar-brand" href="/RaceTalk_war/">RaceTalk</a>
</nav>

<section class="hero d-flex justify-content-start align-items-center" style="background:#f8f8f8; min-height:70vh;">
    <div class="hero-content">
        <h1 class="hero-title">Вход</h1>
        <p class="hero-desc">Авторизуйтесь, чтобы вернуться в мир Формулы 1 и общаться с фанатами!</p>

        <form id="loginForm" action="/RaceTalk_war/login" method="post" class="p-4 rounded shadow-sm" style="background:#fff; max-width:400px;">
            <div class="mb-3">
                <label for="username" class="form-label fw-bold text-danger">Имя пользователя</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Ваше имя" required>
            </div>
            <div class="mb-4">
                <label for="password" class="form-label fw-bold text-danger">Пароль</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Введите пароль" required>
            </div>
            <div class="d-grid">
                <button type="submit" class="btn btn-main">Войти</button>
            </div>
            <p class="mt-3 text-muted">
                Нет аккаунта? <a href="/RaceTalk_war/sign_up" class="text-danger fw-bold">Зарегистрируйтесь</a>
            </p>

            <#if LoginErrorMessage??>
                <div id="loginError" class="text-danger fw-bold mt-3">${LoginErrorMessage}</div>
            <#else>
                <div id="loginError" class="text-danger fw-bold mt-3" style="display:none;"></div>
            </#if>
        </form>
    </div>
</section>


<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
