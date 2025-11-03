<#include "/templates/base.ftl">

<#macro title>RaceTalk — Все команды</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/teams.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    </nav>
</#macro>

<#macro hero>
    <section class="hero d-flex align-items-center justify-content-start">
        <div class="hero-content">
            <h1 class="hero-title">Все команды</h1>
            <p class="hero-desc">Для просмотра статистики команды — нажмите</p>
        </div>
    </section>
</#macro>

<#macro content>
    <main class="container">
        <#if teams?size == 0>
            <p class="text-muted text-center mt-4">Команды отсутствуют</p>
        <#else>
            <div class="team-list">
                <#list teams as team>
                    <a href="${contextPath}/team/${team.id}" class="team-card-link">
                        <div class="team-card">
                            <#if team.photo??>
                                <img src="${team.photo}" class="team-logo"/>
                            <#else>
                                <img src="${contextPath}/assets/images/team-logo/default.jpg" class="team-logo"/>
                            </#if>
                            <div class="team-name">${team.name}</div>
                            <div class="team-country">${team.country!""}</div>
                        </div>
                    </a>
                </#list>
            </div>
        </#if>

        <#if isAdmin?? && isAdmin>
            <div class="d-flex justify-content-center mt-2">
                <a href="${contextPath}/team/create" class="btn btn-main">Добавить команду</a>
            </div>
        </#if>
    </main>
</#macro>