<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Результаты гонки | ${race.location}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-race_details.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/">RaceTalk</a>
    <div class="collapse navbar-collapse justify-content-end">
        <div class="navbar-nav">
            <a class="nav-link text-danger fw-bold d-flex align-items-center" href="${contextPath}/races" role="button">
                &#8592;
                <span class="ms-2">Назад</span>
            </a>
        </div>
    </div>
</nav>

<section class="hero d-flex align-items-center justify-content-start">
    <div class="hero-content">
        <h1 class="hero-title">
            Гран-при ${race.location}
        </h1>
        <div class="hero-desc">
            <#assign raceDate = race.raceDate?date("yyyy-MM-dd")>
            ${raceDate?string("dd.MM.yyyy")}
        </div>
    </div>
</section>

<main class="container my-5">
    <h2 class="section-title mb-4">Результаты гонки</h2>
    <#if results?? && results?size &gt; 0>
        <div class="table-responsive">
            <table class="table table-hover align-middle shadow-sm">
                <thead class="table-danger">
                <tr>
                    <th scope="col">Место</th>
                    <th scope="col">Гонщик</th>
                    <th scope="col">Очки</th>
                </tr>
                </thead>
                <tbody>
                <#list results as r>
                    <tr>
                        <td>
                            <#if r.position == 0>
                                —
                            <#else>
                                ${r.position}
                            </#if>
                        </td>
                        <td>${r.driver.firstName} ${r.driver.lastName}</td>
                        <td>${r.points}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    <#else>
        <p class="text-muted">Результаты гонки отсутствуют</p>
    </#if>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
