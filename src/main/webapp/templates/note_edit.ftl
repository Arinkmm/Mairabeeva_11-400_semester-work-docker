<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Редактировать заметку</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    <div class="collapse navbar-collapse justify-content-end">
        <div class="navbar-nav">
            <a class="nav-link text-danger fw-bold d-flex align-items-center" href="${contextPath}/notes" role="button">
                &#8592;
                <span class="ms-2">Назад</span>
            </a>
        </div>
    </div>
</nav>

<main class="container my-5">
    <h1 class="section-title mb-4">Редактировать заметку</h1>
    <form method="POST" action="${contextPath}/notes" class="mb-5 p-4 border rounded shadow-sm bg-light">
        <input type="hidden" name="action" value="edit" />
        <input type="hidden" name="noteId" value="${note.id}" />

        <div class="mb-3">
            <label class="form-label fw-bold">Заголовок</label>
            <input type="text" name="title" class="form-control" value="${note.title}" required />
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Содержание</label>
            <textarea name="content" class="form-control" rows="4">${note.content}</textarea>
        </div>

        <button type="submit" class="btn btn-main">Сохранить изменения</button>
    </form>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
