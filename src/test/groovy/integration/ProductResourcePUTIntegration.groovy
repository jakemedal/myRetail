package integration

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import integration.util.MongoDBUtility
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import spock.lang.Specification

class ProductResourcePUTIntegration extends Specification {

    def host = "http://localhost:8080/myRetail/api/products/"
    def mongoDBHelper = MongoDBUtility.INSTANCE

    def setup() {
        mongoDBHelper.cleanUp()
    }

    def "Test put product price"() {
        given:
        def id = 16696652
        def price = 237.99
        def currency = "USD"

        def json = buildJsonPut(id, price, currency)

        when:
        HttpResponse response = Unirest.put(host + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .asString()

        then:
        response.status == HttpStatus.CREATED.value()
    }

    def "Test put product price - update existing price"() {
        given:
        def id = 16696652
        def price = 237.99
        def priceUpdate = 214.99
        def currency = "USD"

        def json = buildJsonPut(id, price, currency)
        def jsonUpdate = buildJsonPut(id, priceUpdate, currency)

        when:
        HttpResponse response = Unirest.put(host + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(json)
                .asString()

        then:
        response.status == HttpStatus.CREATED.value()

        when:
        HttpResponse responseUpdate = Unirest.put(host + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(jsonUpdate)
                .asString()

        then:
        responseUpdate.status == HttpStatus.CREATED.value()
    }

    def "Test put product price - mismatching IDs"() {
        given:
        def idParam = 12345678
        def idPaylaod = 55555555
        def json = buildJsonPut(idPaylaod, 100, "USD")

        when:
        HttpResponse response = Unirest.put(host + idParam)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .asString()

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        response.body.contains(String.valueOf(idParam))
        response.body.contains(String.valueOf(idPaylaod))
    }

    def "Test put product price - empty payload"() {
        when:
        HttpResponse response = Unirest.put(host + "12345678")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body("")
                .asString()

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

    def "Test put product price - invalid json format"() {
        given:
        def id = 12345
        def badJson = "{\"id\":$id, \"bad_key\":\"this is invalid\"}"

        when:
        HttpResponse response = Unirest.put(host + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(badJson)
                .asString()

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

    def "Test put product price - invalid media type"() {
        when:
        HttpResponse response = Unirest.put(host + "12345678")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                .body("")
                .asString()

        then:
        response.status == HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()
    }

    def buildJsonPut(long id, double price, String currency) {
        return "{\"id\":$id,\"current_price\":{\"value\": $price,\"currency_code\":\"$currency\"}}"
    }
}