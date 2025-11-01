<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Профиль пользователя</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-user_profile.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
</nav>

<section class="hero">
    <div class="hero-content">
        <h1 class="hero-title">Ваш профиль</h1>

        <#if user.photo?? && user.photo?has_content>
            <a href="${user.photo}">
                <img src="${user.photo}" class="photo" />
            </a>
        <#else>
            <img src="${contextPath}/assets/images/driver-profile/default.png" class="photo" />
        </#if>

        <p class="username">${user.username}</p>
        <p class="status text-muted fst-italic mb-3">${user.status!''}</p>

        <a href="${contextPath}/user/edit/${user.id}" class="btn btn-main mt-2">Редактировать</a>
    </div>
</section>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
