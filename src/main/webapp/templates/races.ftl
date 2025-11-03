<#include "/templates/base.ftl">

<#macro title>RaceTalk — Прошедшие гонки</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/races.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    </nav>
</#macro>

<#macro hero>
    <section>
        <div class="hero-content">
            <h1 class="hero-title">Прошедшие гонки</h1>
            <p class="hero-desc">Для просмотра полных результатов гонки — нажмите</p>
        </div>
    </section>
</#macro>

<#macro content>
    <main class="container my-4">
            <#if races?size == 0>
                <p class="text-muted text-center mt-4">Прошедших гонок пока нет</p>
            <#else>
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
            </#if>
        </main>
</#macro>