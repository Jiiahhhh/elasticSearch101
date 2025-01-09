package com.ilal

import grails.gorm.transactions.Transactional
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired

@Transactional
class ElasticsearchService {

    @Autowired
    RestHighLevelClient client

    def searchBooks(String query, int page = 0, int size = 100) {
        try {
            SearchRequest searchRequest = new SearchRequest('books')
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
            sourceBuilder.query(QueryBuilders.matchPhraseQuery('title', query))
            sourceBuilder.from(page * size)
            sourceBuilder.size(size)

            log.info("Elasticsearch query: ${sourceBuilder}")

            searchRequest.source(sourceBuilder)
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT)

            return searchResponse.hits.hits.collect { hit -> hit.getSourceAsMap() }
        } catch (Exception e) {
            log.error("Error during Elasticsearch search: ${e.message}", e)
            throw new RuntimeException('Search failed. Please try again later.')
        }
    }

    def saveBook(List<Book> books) {
        try {
            def bulkRequest = new BulkRequest()
            books.each { book ->
                bulkRequest.add(new IndexRequest('books')
                        .id(book.id.toString())
                        .source([
                                title          : book.title,
                                author         : book.author,
                                publicationYear: book.publicationYear
                        ], XContentType.JSON)
                )
            }
            client.bulk(bulkRequest, RequestOptions.DEFAULT)
            log.info('Batch indexing completed successfully.')
        } catch (Exception e) {
            log.error("Error during Elasticsearch batch indexing: ${e.message}", e)
            throw new RuntimeException('Batch indexing failed. Please try again later.')
        }
    }

    def deleteBook(Book book) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest('books', book.id.toString())
            client.delete(deleteRequest, RequestOptions.DEFAULT)
            log.info("Book deleted from Elasticsearch: ${book.id}")
        } catch (Exception e) {
            log.error("Error during Elasticsearch deletion: ${e.message}", e)
            throw new RuntimeException('Deletion failed. Please try again later.')
        }
    }

}
