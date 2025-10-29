<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Мои заметки</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-notes.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
</nav>

<main class="container my-5">
    <h1 class="section-title mb-4">Мои заметки</h1>

    <form method="POST" action="${contextPath}/notes" class="mb-5 p-4 border rounded shadow-sm bg-light">
        <h4 class="mb-3 text-danger">Добавить новую заметку</h4>
        <div class="mb-3">
            <label for="title" class="form-label fw-bold">Заголовок</label>
            <input type="text" id="title" name="title" class="form-control" placeholder="Введите заголовок заметки" required />
        </div>
        <div class="mb-3">
            <label for="content" class="form-label fw-bold">Содержание</label>
            <textarea id="content" name="content" class="form-control" rows="4" placeholder="Введите текст заметки"></textarea>
        </div>
        <button type="submit" class="btn btn-main">Добавить заметку</button>
    </form>

    <#if notes?? && notes?size gt 0>
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
                    <form method="POST" action="${contextPath}/notes">
                        <input type="hidden" name="action" value="delete" />
                        <input type="hidden" name="noteId" value="${note.id}" />
                        <button type="submit" class="btn btn-outline-danger btn-sm">
                            ✕ Удалить
                        </button>
                    </form>
                </div>
            </#list>
        </div>
    <#else>
        <p class="text-muted text-center mt-4">У вас пока нет заметок</p>
    </#if>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
