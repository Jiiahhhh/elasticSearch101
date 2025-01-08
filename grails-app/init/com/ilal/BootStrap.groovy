package com.ilal

class BootStrap {

    def elasticsearchService

    def init = { servletContext ->
        if (Book.count() == 0) { // Hanya inisialisasi jika database kosong
            println "Initializing 100,000 books..."

            int totalBooks = 100000 // Total jumlah buku
            int batchSize = 5000   // Jumlah buku per batch (untuk mengurangi beban memori)

            def authors = (1..100).collect { "Author ${it}" } // 100 Penulis

            (1..totalBooks).collate(batchSize).eachWithIndex { batch, batchIndex ->
                Book.withTransaction {
                    batch.each { i ->
                        int globalIndex = batchIndex * batchSize + i // Menghitung indeks global
                        def book = new Book(
                                title: "Book Title ${globalIndex}", // Judul diurutkan
                                author: authors[globalIndex % authors.size()],
                                publicationYear: 1900 + (globalIndex % 120) // Tahun publikasi antara 1900-2020
                        )
                        if (book.save()) {
                            elasticsearchService?.saveBook(book) // Indeks buku ke Elasticsearch
                        } else {
                            println "Failed to save book: ${book.errors}"
                        }
                    }
                }
                println "Batch ${batchIndex + 1} of ${totalBooks / batchSize} saved."
            }
            println "100,000 books initialized successfully."
        } else {
            println "Books already initialized."
        }
    }


    def destroy = {
    }
}
