<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Профиль и результаты пилота | ${driver.firstName} ${driver.lastName}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-driver_profile.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/">RaceTalk</a>
    <div class="collapse navbar-collapse justify-content-end">
        <div class="navbar-nav">
            <a class="nav-link text-danger fw-bold d-flex align-items-center" href="${contextPath}/drivers" role="button">
                &#8592;
                <span class="ms-2">Назад</span>
            </a>
        </div>
    </div>
</nav>

<section class="hero d-flex align-items-center justify-content-start">
    <img src="${contextPath}/assets/images/driver-profile/${driver.photo!'default.jpg'}" class="driver-photo"/>
    <div class="hero-content">
        <h1 class="hero-title">
            Пилот №${driver.driverNumber}: ${driver.firstName} ${driver.lastName}
        </h1>
        <p><strong>Команда:</strong> ${driver.team.name}</p>
        <p><strong>Национальность:</strong> ${driver.country}</p>
        <p><strong>Дата рождения:</strong>
            <#assign dobDate = driver.dateOfBirth?date("yyyy-MM-dd")>
            ${dobDate?string("dd.MM.yyyy")}
        </p>
    </div>
</section>

<main class="container my-5">
    <h2 class="section-title mb-4">Результаты гонок</h2>
    <#if raceResults?? && raceResults?size &gt; 0>
        <div class="table-responsive">
            <table class="table table-hover align-middle shadow-sm">
                <thead class="table-danger">
                <tr>
                    <th scope="col">Гран-при</th>
                    <th scope="col">Дата</th>
                    <th scope="col">Финиш</th>
                    <th scope="col">Очки</th>
                    <th scope="col">Статус</th>
                </tr>
                </thead>
                <tbody>
                <#list raceResults as r>
                    <tr>
                        <td>${r.race.location}</td>
                        <#assign rDate = r.race.raceDate?date("yyyy-MM-dd")>
                        <td>${rDate?string("dd.MM.yyyy")}</td>
                        <td>
                            <#if r.position == 0>
                                —
                            <#else>
                                ${r.position}
                            </#if>
                        </td>

                        <td>${r.points}</td>
                        <td>
                            <#if r.position == 0>
                                Не финишировал
                            <#else>
                                ${r.status!'Финишировал'}
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    <#else>
        <p class="text-muted">Нет данных по результатам этого пилота</p>
    </#if>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
