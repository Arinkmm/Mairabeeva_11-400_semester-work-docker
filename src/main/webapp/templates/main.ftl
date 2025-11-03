<#include "/templates/base.ftl">

<#macro title>RaceTalk — Главная</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/main.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
        <div class="collapse navbar-collapse justify-content-end">
            <div class="navbar-nav d-flex align-items-center gap-3">
                <a class="nav-link text-danger fw-bold" href="${contextPath}/user/profile/${user.id}">Мой профиль</a>
                <a class="nav-link text-danger fw-bold" href="${contextPath}/logout">Выйти</a>
            </div>
        </div>
    </nav>
</#macro>

<#macro hero>
    <section class="hero d-flex justify-content-start align-items-center">
        <div class="hero-content">
            <h1 class="hero-title">Добро пожаловать, ${user.username}!</h1>
            <p class="hero-desc">Следите за предстоящими гонками Формулы 1 и обсуждайте их с сообществом фанатов</p>
            <a href="${contextPath}/chat" class="btn btn-main mt-2">Перейти в чат</a>
        </div>
    </section>
</#macro>

<#macro linksSection>
    <div class="links-section">
        <a href="${contextPath}/drivers" class="btn btn-link">Гонщики</a>
        <a href="${contextPath}/teams" class="btn btn-link">Команды</a>
        <a href="${contextPath}/races" class="btn btn-link">Прошедшие гонки</a>
        <#if !isAdmin>
            <a href="${contextPath}/notes" class="btn btn-link">Заметки</a>
        </#if>
    </div>
</#macro>

<#macro content>
    <h2 class="section-title mb-4">Предстоящие гонки</h2>
    <#if races?size == 0>
        <p class="text-muted text-center mt-4">Предстоящих гонок пока нет</p>
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
                        <td>${race.location}</td>
                        <#assign raceDateAsDate = race.raceDate?date("yyyy-MM-dd")>
                        <td>${raceDateAsDate?string("dd.MM.yyyy")}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </#if>
    <#if isAdmin?? && isAdmin>
        <div class="d-flex justify-content-center mt-2">
            <a href="${contextPath}/race/create" class="btn btn-main">Добавить гонку</a>
        </div>
    </#if>
</#macro>