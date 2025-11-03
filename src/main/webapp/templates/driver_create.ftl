<#include "/templates/base.ftl">

<#macro title>RaceTalk — Добавить нового гонщика</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/style-edit.css" rel="stylesheet" />
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

<#macro content>
    <h1 class="section-title mb-4">Добавить нового гонщика</h1>
    <form action="${contextPath}/driver/create" method="post" enctype="multipart/form-data" class="mb-5 p-4 border rounded shadow-sm bg-light">
        <#if DriverCreateErrorMessage??>
            <div class="validation-error">${DriverCreateErrorMessage}</div>
        </#if>

        <div class="mb-3">
            <label for="photo" class="form-label fw-bold">Фото гонщика</label>
            <input type="file" id="photo" name="photo" class="form-control" accept="image/*" />
        </div>
        <div class="mb-3">
            <label for="driverNumber" class="form-label fw-bold">Номер гонщика</label>
            <input type="number" id="driverNumber" name="driverNumber" class="form-control" required />
        </div>
        <div class="mb-3">
            <label for="firstName" class="form-label fw-bold">Имя</label>
            <input type="text" id="firstName" name="firstName" class="form-control" required />
        </div>
        <div class="mb-3">
            <label for="lastName" class="form-label fw-bold">Фамилия</label>
            <input type="text" id="lastName" name="lastName" class="form-control" required />
        </div>
        <div class="mb-3">
            <label for="country" class="form-label fw-bold">Страна</label>
            <input type="text" id="country" name="country" class="form-control" required />
        </div>
        <div class="mb-3">
            <label for="dateOfBirth" class="form-label fw-bold">Дата рождения</label>
            <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" required />
        </div>
        <div class="mb-3">
            <label for="teamId" class="form-label fw-bold">Команда</label>
            <select id="teamId" name="teamId" class="form-select" required>
                <#list teams as team>
                    <option value="${team.id}">${team.name}</option>
                </#list>
            </select>
        </div>

        <button type="submit" class="btn btn-main">Добавить гонщика</button>
    </form>
</#macro>