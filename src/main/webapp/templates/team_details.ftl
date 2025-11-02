<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Результаты команды | ${team.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-team_details.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    <div class="collapse navbar-collapse justify-content-end">
        <div class="navbar-nav">
            <a class="nav-link text-danger fw-bold d-flex align-items-center" href="${contextPath}/teams" role="button">
                &#8592;
                <span class="ms-2">Назад</span>
            </a>
        </div>
    </div>
</nav>

<section class="hero">
    <div class="hero-flex">
        <div class="team-logo-hero">
            <#if team.photo??>
                <img src="${team.photo}" class="team-logo"/>
            <#else>
                <img src="${contextPath}/assets/images/team-logo/default.jpg" class="team-logo"/>
            </#if>
        </div>
        <div class="hero-content">
            <h1 class="hero-title">Результаты команды ${team.name}</h1>
            <p class="hero-desc">Позиции пилотов в каждой из прошедших гонок</p>
        </div>
    </div>
</section>

<main class="container">
    <div class="results-table">
        <table class="table">
            <thead>
            <tr>
                <th>Пилот</th>
                <#list races as r>
                    <th>${r.location}
                        <br/>
                        <#assign raceDate = r.raceDate?date("yyyy-MM-dd")>
                        ${raceDate?string("dd.MM.yyyy")}
                    </th>
                </#list>
            </tr>
            </thead>
            <tbody>
            <#if drivers?size == 0>
                <tr>
                    <td class="text-muted text-center mt-4">Гонщики отсутствуют</td>
                </tr>
            <#else>
                <#list drivers as driver>
                    <tr>
                        <td class="driver-cell">
                            <#if driver.photo??>
                                <img src="${driver.photo}" class="driver-photo"/>
                            <#else>
                                <img src="${contextPath}/assets/images/driver-profile/default.jpg" class="driver-photo"/>
                            </#if>
                            ${driver.firstName} ${driver.lastName}
                        </td>
                        <#list races as r>
                            <#assign res = "">
                            <#list results as result>
                                <#if result.race?? && result.race.id == r.id && result.driver.driverNumber == driver.driverNumber>
                                    <#assign res = result>
                                    <#break>
                                </#if>
                            </#list>
                            <td>
                                <#if res?has_content>
                                    ${res.position}
                                <#else>
                                    —
                                </#if>
                            </td>
                        </#list>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
