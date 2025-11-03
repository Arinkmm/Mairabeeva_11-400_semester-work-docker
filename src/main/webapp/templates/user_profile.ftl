<#include "/templates/base.ftl">

<#macro title>RaceTalk — Профиль</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/user_profile.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    </nav>
</#macro>

<#macro hero>
    <section class="hero">
        <div class="hero-content">
            <#if isOwner>
                <h1 class="hero-title">Ваш профиль</h1>
            <#else>
                <h1 class="hero-title">Профиль пользователя</h1>
            </#if>

            <#if user.photo?? && user.photo?has_content>
                <a href="${user.photo}">
                    <img src="${user.photo}" class="photo" />
                </a>
            <#else>
                <img src="${contextPath}/assets/images/user-default.png" class="photo" />
            </#if>

            <p class="username">${user.username}</p>
            <p class="status text-muted fst-italic mb-3">${user.status!''}</p>
            <#if canEdit>
                <a href="${contextPath}/user/edit" class="btn btn-main mt-2">Редактировать</a>
            </#if>
            <#if canDelete>
                <form method="post" action="${contextPath}/user/delete">
                    <button type="submit" class="btn btn-main mt-2">Удалить профиль</button>
                </form>
            </#if>
        </div>
    </section>
</#macro>