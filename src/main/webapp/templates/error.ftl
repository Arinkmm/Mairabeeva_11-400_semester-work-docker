<#include "/templates/base.ftl">

<#macro title>RaceTalk — Ошибка ${statusCode}</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/error.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    </nav>
</#macro>

<#macro content>
    <main class="container text-center mt-5">
        <h1 class="display-3 text-danger">${statusCode}</h1>
        <p class="lead">${errorMessage}</p>
    </main>
</#macro>