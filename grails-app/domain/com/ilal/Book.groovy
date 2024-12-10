package com.ilal

class Book {

    String title
    String author
    Integer publicationYear

    static mapping = {
        id generator: 'identity'
    }

    static constraints = {
        title blank: false, size: 1..255
        author blank: false, size: 1..255
        publicationYear nullable: true
    }

    static searchable = true
}