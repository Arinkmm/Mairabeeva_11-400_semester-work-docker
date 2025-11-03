<#include "/templates/base.ftl">

<#macro title>
    RaceTalk — Мои заметки
</#macro>

<#macro additionalCss>
    <link href="${contextPath}/assets/css/notes.css" rel="stylesheet" />
</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    </nav>
</#macro>

<#macro content>
    <h1 class="section-title mb-4">Мои заметки</h1>

    <form method="POST" action="${contextPath}/notes" class="mb-5 p-4 border rounded shadow-sm bg-light">
        <h4 class="mb-3 text-danger">Добавить новую заметку</h4>
        <div class="mb-3">
            <label class="form-label fw-bold">Заголовок</label>
            <input type="text" name="title" class="form-control" placeholder="Введите заголовок" required />
        </div>
        <div class="mb-3">
            <label class="form-label fw-bold">Содержание</label>
            <textarea name="content" class="form-control" rows="4" placeholder="Введите текст заметки"></textarea>
        </div>
        <button type="submit" class="btn btn-main">Добавить заметку</button>
    </form>

    <#if notes?size == 0>
        <p class="text-muted text-center mt-4">У вас пока нет заметок</p>
    <#else>
        <div class="list-group">
            <#list notes as note>
                <div class="list-group-item border rounded mb-3 shadow-sm d-flex justify-content-between align-items-start">
                    <div>
                        <h5 class="mb-1">${note.title}</h5>
                        <p class="mb-1">${note.content}</p>
                        <small class="text-muted">Создано:
                            <#if note.createdAt??>
                                ${note.createdAt?replace("T", " ")?substring(0,16)}
                            </#if>
                        </small>
                    </div>
                    <div class="d-flex gap-2">
                        <a href="${contextPath}/notes/edit/${note.id}" class="btn btn-outline-danger btn-sm">Редактировать</a>

                        <form method="POST" action="${contextPath}/notes/delete/${note.id}">
                            <button type="submit" class="btn btn-outline-danger btn-sm">✕ Удалить</button>
                        </form>
                    </div>
                </div>
            </#list>
        </div>
    </#if>
</#macro>