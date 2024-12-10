<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search with GORM</title>
</head>
<body>
<h1>Search Results (GORM Query)</h1>

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

<h2>Results for "${query}"</h2>
<ul>
    <g:if test="${books?.isEmpty()}">
        <li>No results found.</li>
    </g:if>
    <g:else>
        <g:each in="${books}" var="book">
            <li>
                <strong>${book.title}</strong> by ${book.author} (${book.publicationYear ?: 'N/A'})
            </li>
        </g:each>
    </g:else>
</ul>

<a href="${createLink(controller: 'book', action: 'index')}">Back to All Books</a>
</body>
</html>
