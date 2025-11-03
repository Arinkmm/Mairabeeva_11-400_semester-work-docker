<#include "/templates/base.ftl">

<#macro title>
    RaceTalk — Профиль и результаты пилота | ${driver.firstName} ${driver.lastName}
</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/driver_profile.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
        <div class="collapse navbar-collapse justify-content-end">
            <div class="navbar-nav">
                <a class="nav-link text-danger fw-bold d-flex align-items-center" href="${contextPath}/drivers" role="button">
                    &#8592;
                    <span class="ms-2">Назад</span>
                </a>
            </div>
        </div>
    </nav>
</#macro>

<#macro hero>
    <section class="hero d-flex align-items-center justify-content-start">
        <#if driver.photo??>
            <img src="${driver.photo}" class="photo"/>
        <#else>
            <img src="${contextPath}/assets/images/driver-profile/default.jpg" class="photo"/>
        </#if>
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

            <#if isAdmin>
                <button class="btn btn-main" type="button" data-bs-toggle="collapse" data-bs-target="#editTeamForm" aria-expanded="false" aria-controls="editTeamForm">
                    Редактировать команду
                </button>

                <div class="collapse mt-3" id="editTeamForm">
                    <form method="post" action="${contextPath}/driver/${driver.driverNumber}">
                        <div class="mb-3">
                            <label class="form-label">Выберите новую команду</label>
                            <select id="teamSelect" name="team" class="form-select" required>
                                <#list teams as teamOption>
                                    <option value="${teamOption.id}" <#if teamOption.id == driver.team.id>selected</#if>>
                                        ${teamOption.name}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-main">Сохранить</button>
                    </form>
                </div>
            </#if>
        </div>
    </section>
</#macro>

<#macro content>
    <h2 class="section-title mb-4">Результаты гонок</h2>
    <#if raceResults?size == 0>
        <p class="text-muted text-center mt-4">Нет данных по результатам этого пилота</p>
    <#else>
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
    </#if>
</#macro>yy