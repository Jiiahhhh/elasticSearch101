package com.ilal

class BootStrap {

//    def elasticsearchService // Inject ElasticsearchService
//
    def init = { servletContext ->
//        if (Book.count() == 0) { // Hanya inisialisasi jika database kosong
//            println "Initializing 100,000 books..."
//
//            int totalBooks = 100000 // Total jumlah buku
//            int batchSize = 5000   // Jumlah buku per batch (untuk mengurangi beban memori)
//
//            def authors = (1..100).collect { "Author ${it}" } // 100 Penulis
//            def titles = (1..100).collect { "Book Title ${it}" } // 100 Judul Umum
//
//            (1..totalBooks).collate(batchSize).eachWithIndex { batch, batchIndex ->
//                Book.withTransaction {
//                    batch.each { i ->
//                        def book = new Book(
//                                title: "${titles[i % titles.size()]} #${i}",
//                                author: authors[i % authors.size()],
//                                publicationYear: 1900 + (i % 120) // Tahun publikasi antara 1900-2020
//                        )
//                        if (book.save()) {
//                            elasticsearchService?.saveBook(book) // Indeks buku ke Elasticsearch
//                        } else {
//                            println "Failed to save book: ${book.errors}"
//                        }
//                    }
//                }
//                println "Batch ${batchIndex + 1} of ${totalBooks / batchSize} saved."
//            }
//
//            println "100,000 books initialized successfully."
//        } else {
//            println "Books already initialized."
//        }
    }

    def destroy = {
    }
}
