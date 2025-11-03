<#include "/templates/base.ftl">

<#macro title>RaceTalk — Результаты гонки | ${race.location}</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/race_details.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
        <div class="collapse navbar-collapse justify-content-end">
            <div class="navbar-nav">
                <a class="nav-link text-danger fw-bold d-flex align-items-center" href="${contextPath}/races" role="button">
                    &#8592;
                    <span class="ms-2">Назад</span>
                </a>
            </div>
        </div>
    </nav>
</#macro>

<#macro hero>
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
</#macro>

<#macro content>
    <h2 class="section-title mb-4">Результаты гонки</h2>
    <#if results?size == 0>
        <p class="text-muted text-center mt-4">Результаты гонки отсутствуют</p>
    <#else>
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
    </#if>
</#macro>