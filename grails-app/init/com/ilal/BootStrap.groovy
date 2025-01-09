package com.ilal

class BootStrap {

    def elasticsearchService

    def init = { servletContext ->
        if (Book.count() == 0) {
            println 'Initializing 100,000 books...'

            int totalBooks = 100000
            int batchSize = 1000
            def authors = (1..100).collect { "Author ${it}" }

            (1..totalBooks).collate(batchSize).eachWithIndex { batch, batchIndex ->
                Book.withTransaction {
                    def booksToIndex = batch.collect { localIndex ->
                        int globalIndex = batchIndex * batchSize + localIndex + 1
                        def book = new Book(
                                title: "Book Title ${globalIndex}",
                                author: authors[globalIndex % authors.size()],
                                publicationYear: 1900 + (globalIndex % 120)
                        )
                        book.save(flush: true, failOnError: true)
                        book
                    }
                    // Batch indexing ke Elasticsearch
                    elasticsearchService?.saveBook(booksToIndex)
                }
                println "Batch ${batchIndex + 1} of ${totalBooks / batchSize} saved and indexed."
            }

            println '100,000 books initialized and indexed successfully.'
        } else {
            println 'Books already initialized.'
        }
    }

    def destroy = {
    }

}
