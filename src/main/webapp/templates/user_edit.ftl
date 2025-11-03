<#include "/templates/base.ftl">

<#macro title>RaceTalk — Редактировать профиль</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/style-edit.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
        <div class="collapse navbar-collapse justify-content-end">
            <div class="navbar-nav">
                <a class="nav-link text-danger fw-bold d-flex align-items-center" href="${contextPath}/user/profile/${user.id}" role="button">
                    &#8592;
                    <span class="ms-2">Назад</span>
                </a>
            </div>
        </div>
    </nav>
</#macro>

<#macro content>
    <div class="container-edit-profile">
        <h1 class="section-title mb-4">Редактировать профиль</h1>

        <#if EditErrorMessage??>
            <div class="validation-error">${EditErrorMessage}</div>
        <#else>
            <div class="validation-error hidden"></div>
        </#if>

        <form method="POST" action="${contextPath}/user/edit" enctype="multipart/form-data" novalidate>
            <div class="mb-3">
                <label class="form-label fw-bold text-danger">Изменить фото</label>
                <input class="form-control" type="file" name="photo" accept="image/*" />
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold text-danger">Имя пользователя</label>
                <input type="text" class="form-control" name="username" value="${user.username! ''}" required />
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold text-danger">Статус пользователя</label>
                <input type="text" class="form-control" name="status" value="${user.status! ''}" />
            </div>

            <hr />

            <div class="mb-3">
                <label class="form-label fw-bold text-danger">Текущий пароль</label>
                <input type="password" class="form-control" name="currentPassword" placeholder="Введите текущий пароль для смены" />
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold text-danger">Новый пароль</label>
                <input type="password" class="form-control" name="newPassword" placeholder="Новый пароль" />
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold text-danger">Подтвердите новый пароль</label>
                <input type="password" class="form-control" name="confirmPassword" placeholder="Подтверждение пароля" />
            </div>

            <button type="submit" class="btn-main">Сохранить изменения</button>
        </form>
    </div>
</#macro>