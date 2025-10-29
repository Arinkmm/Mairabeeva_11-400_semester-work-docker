<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Прошедшие гонки</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-races.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
</nav>


<section>
    <div class="hero-content">
        <h1 class="hero-title">Прошедшие гонки</h1>
        <p class="hero-desc">Для просмотра полных результатов гонки — нажмите</p>
    </div>
<main class="container my-4">
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
                    <td>
                        <a class="race-location" href="${contextPath}/race/${race.id}">
                            ${race.location}
                        </a>
                    </td>
                    <td>
                        <#assign raceDate = race.raceDate?date("yyyy-MM-dd")>
                        ${raceDate?string("dd.MM.yyyy")}
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</main>
</section>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
