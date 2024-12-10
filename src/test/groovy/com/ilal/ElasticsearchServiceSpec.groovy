package com.ilal

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ElasticsearchServiceSpec extends Specification implements ServiceUnitTest<ElasticsearchService> {

     void "test something"() {
        expect:
        service.doSomething()
     }
}
