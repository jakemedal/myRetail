import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest

import spock.lang.Specification
import util.MongoDBUtility

import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType

import static javax.ws.rs.core.Response.Status.*


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
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(json)
                .asString()

        then:
        response.status == CREATED.statusCode
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
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(json)
                .asString()

        then:
        response.status == CREATED.statusCode

        when:
        HttpResponse responseUpdate = Unirest.put(host + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(jsonUpdate)
                .asString()

        then:
        responseUpdate.status == CREATED.statusCode
    }

    def "Test put product price - mismatching IDs"() {
        given:
        def idParam = 12345678
        def idPaylaod = 55555555
        def json = buildJsonPut(idPaylaod, 100, "USD")

        when:
        HttpResponse response = Unirest.put(host + idParam)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(json)
                .asString()

        then:
        response.status == BAD_REQUEST.statusCode
        response.body.contains(String.valueOf(idParam))
        response.body.contains(String.valueOf(idPaylaod))
    }

    def "Test put product price - empty payload"() {
        when:
        HttpResponse response = Unirest.put(host + "12345678")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body("")
                .asString()

        then:
        response.status == BAD_REQUEST.statusCode
    }

    def "Test put product price - invalid json format"() {
        given:
        def id = 12345
        def badJson = "{\"id\":$id, \"bad_key\":\"this is invalid\"}"

        when:
        HttpResponse response = Unirest.put(host + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(badJson)
                .asString()

        then:
        response.status == BAD_REQUEST.statusCode
    }

    def "Test put product price - invalid media type"() {
        when:
        HttpResponse response = Unirest.put(host + "12345678")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML)
                .body("")
                .asString()

        then:
        response.status == UNSUPPORTED_MEDIA_TYPE.statusCode
    }

    def buildJsonPut(long id, double price, String currency) {
        return "{\"id\":$id,\"current_price\":{\"value\": $price,\"currency_code\":\"$currency\"}}"
    }
}