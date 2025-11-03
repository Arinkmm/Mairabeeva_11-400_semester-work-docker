<#include "/templates/base.ftl">

<#macro title>RaceTalk — Гонщики сезона</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/drivers.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    </nav>
</#macro>

<#macro hero>
    <section>
        <div class="hero-content">
            <h1 class="hero-title">Гонщики сезона</h1>
            <p class="hero-desc">Для просмотра статистики гонщика — нажмите</p>
        </div>
        <#if drivers?size == 0>
            <p class="text-muted text-center mt-4">Гонщики отсутствуют</p>
        <#else>
            <div class="table-responsive">
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
                            <td>
                                <#if driver.photo??>
                                    <img src="${driver.photo}" class="photo"/>
                                <#else>
                                    <img src="${contextPath}/assets/images/driver-profile/default.jpg" class="photo"/>
                                </#if>
                            </td>
                            <td class="driver-number">${driver.driverNumber}</td>
                            <td>${driver.firstName}</td>
                            <td>${driver.lastName}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <#if isAdmin?? && isAdmin>
                <div class="d-flex justify-content-center mt-2">
                    <a href="${contextPath}/driver/create" class="btn btn-main">
                        Добавить гонщика
                    </a>
                </div>
            </#if>
        </#if>
    </section>
</#macro>
