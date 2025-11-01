<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Главная</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-main.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    <div class="collapse navbar-collapse justify-content-end">
        <div class="navbar-nav d-flex align-items-center gap-3">
            <a class="nav-link text-danger fw-bold" href="${contextPath}/user/profile/${user.id}">Мой профиль</a>
            <a class="nav-link text-danger fw-bold" href="${contextPath}/logout">Выйти</a>
        </div>
    </div>
</nav>


<section class="hero d-flex justify-content-start align-items-center">
    <div class="hero-content">
        <h1 class="hero-title">Добро пожаловать, ${user.username}!</h1>
        <p class="hero-desc">Следите за предстоящими гонками Формулы 1 и обсуждайте их с сообществом фанатов</p>
        <a href="${contextPath}/chat" class="btn btn-main mt-2">Перейти в чат</a>
    </div>
</section>

<div class="links-section">
    <a href="${contextPath}/drivers" class="btn btn-link">Гонщики</a>
    <a href="${contextPath}/teams" class="btn btn-link">Команды</a>
    <a href="${contextPath}/races" class="btn btn-link">Прошедшие гонки</a>
    <a href="${contextPath}/notes" class="btn btn-link">Заметки</a>
</div>

<main class="container my-5">
    <h2 class="section-title mb-4">Предстоящие гонки</h2>
    <#if races?? && races?size gt 0>
        <div class="table-responsive">
            <table class="table table-hover align-middle shadow-sm">
                <thead class="table-danger">
                <tr>
                    <th scope="col">Место проведения</th>
                    <th scope="col">Дата</th>
                </tr>
                </thead>
                <tbody>
                <#list races as race>
                    <tr>
                        <td>${race.location}</td>
                        <#assign raceDateAsDate = race.raceDate?date("yyyy-MM-dd")>
                        <td>${raceDateAsDate?string("dd.MM.yyyy")}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    <#else>
    <p class="text-muted">Нет запланированных гонок</p>
    </#if>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
