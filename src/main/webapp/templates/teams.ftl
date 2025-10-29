<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Все команды</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-teams.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
</nav>

<section class="hero d-flex align-items-center justify-content-start">
    <div class="hero-content">
        <h1 class="hero-title">Все команды</h1>
        <p class="hero-desc">Для просмотра статистики команды — нажмите</p>
    </div>
</section>

<main class="container">
    <div class="team-list">
        <#list teams as team>
            <a href="${contextPath}/team/${team.id}" class="team-card-link">
                <div class="team-card">
                    <img src="${contextPath}/assets/images/team-logo/${team.photo!'default.png'}" class="team-logo" />
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
