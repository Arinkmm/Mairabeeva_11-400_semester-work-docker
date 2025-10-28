<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Все команды</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-teams.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3" aria-label="Главное меню">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
</nav>

<section class="hero">
    <div>
        <h1 class="hero-title">Все команды</h1>
        <p class="hero-desc">Для просмотра статистики команды — нажмите</p>
    </div>
</section>

<main class="container">
    <div class="team-list">
        <#list teams as team>
            <a href="${contextPath}/team/${team.id}" class="team-card-link">
                <div class="team-card">
                    <img src="${contextPath}/assets/images/team-logo/${team.photo!'default.png'}" alt="Логотип команды ${team.name}" class="team-logo" />
                    <div class="team-name">${team.name}</div>
                    <div class="team-country">${team.country!""}</div>
                </div>
            </a>
        </#list>
    </div>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
