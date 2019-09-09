package integration

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import spock.lang.Shared
import spock.lang.Specification
import util.MongoDBUtility

class ProductResourceGETIntegration extends Specification {

    def host = "http://localhost:8080/myRetail/api/products/"
    def mongoDBHelper = MongoDBUtility.INSTANCE

    @Shared String testId1
    @Shared String testId2
    @Shared String testId3
    @Shared String testProduct1
    @Shared String testProduct2
    @Shared String testProduct3

    def setup() {
        mongoDBHelper.cleanUp()

        testId1 = 13860429
        testId2 = 13860432
        testId3 = 13860428
        testProduct1 = "{\"_id\":$testId1,\"current_price\":{\"value\":7.50,\"currency_code\":\"USD\"}}"
        testProduct2 = "{\"_id\":$testId2,\"current_price\":{\"value\":27.59,\"currency_code\":\"USD\"}}"
        testProduct3 = "{\"_id\":$testId3,\"current_price\":{\"value\":249.99,\"currency_code\":\"USD\"}}"
    }

    def "Test get product 1"() {
        setup:
        mongoDBHelper.loadSampleTestData(testProduct1)

        when:
        HttpResponse response = Unirest.get(host + testId1)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .asString()

        then:
        response.status == HttpStatus.OK.value()
        response.body == '{"id":13860429,"name":"SpongeBob SquarePants: SpongeBob\'s Frozen Face-off","current_price":{"value":7.5,"currency_code":"USD"}}'
    }

    def "Test get product 2"() {
        setup:
        mongoDBHelper.loadSampleTestData(testProduct2)

        when:
        HttpResponse response = Unirest.get(host + testId2)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .asString()

        then:
        response.status == HttpStatus.OK.value()
        response.body == '{"id":13860432,"name":"The Donna Reed Show: Season Four, The Lost Episodes (DVD)","current_price":{"value":27.59,"currency_code":"USD"}}'
    }

    def "Test get product 3"() {
        setup:
        mongoDBHelper.loadSampleTestData(testProduct3)

        when:
        HttpResponse response = Unirest.get(host + testId3)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .asString()

        then:
        response.status == HttpStatus.OK.value()
        response.body == '{"id":13860428,"name":"The Big Lebowski (Blu-ray)","current_price":{"value":249.99,"currency_code":"USD"}}'
    }

    def "Test get product that doesn't exist in external API"() {
        given:
        def id = "12345678"

        when:
        HttpResponse response = Unirest.get(host + id)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .asString()

        then:
        response.status == HttpStatus.NOT_FOUND.value()
        response.body.contains(id)
    }

    def "Test get product that doesn't exist in data store"() {
        given:
        def id = "13860428"

        when:
        HttpResponse response = Unirest.get(host + id)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .asString()

        then:
        response.status == HttpStatus.NOT_FOUND.value()
        response.body.contains(id)
    }

    def "Test get project - invalid media type"() {
        when:
        HttpResponse response = Unirest.get(host + 12345678)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE)
                .asString()

        then:
        response.status == HttpStatus.NOT_ACCEPTABLE.value()
    }

}
