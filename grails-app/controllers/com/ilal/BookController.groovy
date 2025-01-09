package com.ilal

import org.springframework.beans.factory.annotation.Autowired

import javax.xml.bind.ValidationException

class BookController {

    @Autowired
    ElasticsearchService elasticsearchService

    def index() {
        [books: Book.list()]
    }

    def search() {
        String query = params.query
        if (!query) {
            flash.message = 'Please provide a search query'
            redirect(action: 'index')
            return
        }

        try {
            def results = elasticsearchService.searchBooks(query)
            render(view: 'search', model: [books: results, query: query])
        } catch (Exception e) {
            flash.message = "Error occured during search: ${e.message}"
            redirect(action: 'index')
        }
    }

    def save() {
        def book = new Book(params)
        try {
            if (book.save(flush: true)) {
                elasticsearchService.saveBook(book)
                flash.message = 'Book saved and indexed successfully.'
                redirect(action: 'index')
            } else {
                flash.message = 'Failed to save book'
                render(view: 'create', model: [book: book])
            }
        } catch (ValidationException e) {
            flash.message = "Validation error: ${e.message}"
            render(view: 'create', model: [book: book])
        }
    }

    def delete(Long id) {
        def book = Book.get(id)
        if (!book) {
            flash.message = 'Book not found.'
            redirect(action: 'index')
            return
        }

        try {
            book.delete(flush: true)
            elasticsearchService.deleteBook(book)
            flash.message = 'Book deleted successfully.'
            redirect(action: 'index')
        } catch (Exception e) {
            flash.message = "Error occured while deleteing book: ${e.message}"
            redirect(action: 'index')
        }
    }

    def gorm() {
        String query = params.query
        if (!query) {
            flash.message = 'Please provide a search query.'
            redirect(action: 'index')
            return
        }

        def results = Book.createCriteria().list {
            if (query.isInteger()) {
                // Jika input adalah angka, cari exact match untuk "Book Title <angka>"
                eq('title', "Book Title ${query.toInteger()}")
            } else {
                // Jika input adalah teks, gunakan ilike untuk pencarian fleksibel
                ilike('title', "%${query}%")
            }
        }

        render(view: 'gorm', model: [books: results, query: query])
    }

}
