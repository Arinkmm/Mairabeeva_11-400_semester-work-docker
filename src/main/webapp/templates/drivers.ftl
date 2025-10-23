<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>RaceTalk — Гонщики сезона</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-drivers.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3" aria-label="Главное меню">
    <a class="navbar-brand" href="${contextPath}/">RaceTalk</a>
    <div class="collapse navbar-collapse justify-content-end">
        <div class="navbar-nav">
            <a class="nav-link text-danger fw-bold" href="${contextPath}/main">На главную</a>
        </div>
    </div>
</nav>

<section>
    <div class="hero-content">
    <h1 class="hero-title">Гонщики сезона</h1>
    <p class="hero-desc">Для просмотра статистики гонщика — нажмите</p>
    </div>

    <div class="table-wrapper">
        <table class="table table-hover align-middle shadow-sm">
            <thead class="table-danger">
            <tr>
                <th scope="col">Фото</th>
                <th scope="col">Гоночный номер</th>
                <th scope="col">Имя</th>
                <th scope="col">Фамилия</th>
            </tr>
            </thead>
            <tbody>
            <#list drivers as driver>
                <tr onclick="window.location.href='${contextPath}/driver/${driver.driverNumber}';">
                    <td><img src="${contextPath}/assets/images/${driver.photo!'background-page-index.jpg'}"alt="${driver.firstName} ${driver.lastName}" class="driver-photo"/></td>
                    <td class="driver-number">${driver.driverNumber}</td>
                    <td>${driver.firstName}</td>
                    <td>${driver.lastName}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</section>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
