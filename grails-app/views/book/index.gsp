<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book List</title>
</head>
<body>
<h1>Book Management</h1>

<!-- Form untuk Pencarian -->
<form action="${createLink(controller: 'book', action: 'search')}" method="get">
    <label>Elasticsearch</label>
    <input type="text" name="query" placeholder="Search for a book by title" value="${query ?: ''}" />
    <button type="submit">Search</button>
</form>
<form action="${createLink(controller: 'book', action: 'gorm')}" method="get">
    <label>GORM</label>
    <input type="text" name="query" placeholder="Search for a book by title" value="${query ?: ''}" />
    <button type="submit">Search</button>
</form>

<!-- Form untuk Menambahkan Buku -->
<h2>Add New Book</h2>
<form action="${createLink(controller: 'book', action: 'save')}" method="post">
    <label for="title">Title:</label>
    <input type="text" id="title" name="title" required /><br>

    <label for="author">Author:</label>
    <input type="text" id="author" name="author" required /><br>

    <label for="publicationYear">Publication Year:</label>
    <input type="number" id="publicationYear" name="publicationYear" /><br>

    <button type="submit">Save</button>
</form>

%{--<!-- Tampilkan Daftar Buku -->--}%
%{--<h2>Book List</h2>--}%
%{--<ul>--}%
%{--    <g:each in="${books}" var="book">--}%
%{--        <li>--}%
%{--            <strong>${book.title}</strong> by ${book.author} (${book.publicationYear ?: 'N/A'})--}%
%{--            <a href="${createLink(controller: 'book', action: 'delete', id: book.id)}" onclick="return confirm('Are you sure you want to delete this book?')">Delete</a>--}%
%{--        </li>--}%
%{--    </g:each>--}%
%{--</ul>--}%
</body>
</html>
