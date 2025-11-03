<#include "/templates/base.ftl">

<#macro title>RaceTalk — Добавить гонку</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/style-edit.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    </nav>
</#macro>

<#macro content>
    <h1 class="section-title mb-4">Добавить новую гонку</h1>

    <form action="${contextPath}/race/create" method="post" class="mb-5 p-4 border rounded shadow-sm bg-light">
        <div class="mb-3">
            <label for="location" class="form-label fw-bold">Место проведения</label>
            <input type="text" class="form-control" id="location" name="location" required />
        </div>

        <div class="mb-3">
            <label for="raceDate" class="form-label fw-bold">Дата гонки</label>
            <input type="date" class="form-control" id="raceDate" name="raceDate" required />
        </div>

        <button type="submit" class="btn btn-main">Добавить гонку</button>
    </form>
</#macro>