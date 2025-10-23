<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Ошибка ${statusCode!500}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-error.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3" aria-label="Главное меню">
    <a class="navbar-brand" href="${contextPath}/">RaceTalk</a>
</nav>

<main class="container text-center mt-5">
    <h1 class="display-3 text-danger">${statusCode!500}</h1>
    <p class="lead">${errorMessage!'Внутренняя ошибка сервера'}</p>
    <a href="${contextPath}/main" class="btn btn-main mt-3">На главную</a>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
