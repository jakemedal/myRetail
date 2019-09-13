package service

import com.myRetail.domain.Price
import com.myRetail.domain.ProductPrice
import com.myRetail.domain.Product
import com.myRetail.repository.ProductPriceDao
import com.myRetail.service.MyRetailProductService
import com.myRetail.service.ProductNameClient
import com.myRetail.service.ProductService
import com.myRetail.service.exception.ProductNameNotFoundException
import com.myRetail.service.exception.ProductPriceNotFoundException
import spock.lang.Specification
import spock.lang.Unroll

class MyRetailProductServiceSpec extends Specification {

    ProductPriceDao dao = Mock(ProductPriceDao)
    ProductNameClient client = Mock(ProductNameClient)

    ProductService service = new MyRetailProductService(dao, client)

    def "Test get product"() {
        given:
        def id = "12345678"
        def productName = "The product name"
        double value = 12.50
        def currency = "USD"

        Price price = new Price(value, currency)
        ProductPrice productPrice = new ProductPrice(id as long, price)

        when:
        Product response = service.getProduct(id)

        then:
        1 * client.getProductName(id) >> productName
        1 * dao.get(id) >> Optional.of(productPrice)

        assert response.id == id as long
        assert response.name == productName
        assert response.price.value == value
        assert response.price.currencyCode == currency
    }

    def "Test get product - product name not found"() {
        given:
        def id = "12345678"

        when:
        service.getProduct(id)

        then:
        1 * client.getProductName(id) >> Optional.empty()
        def e = thrown(ProductNameNotFoundException)
        e.message.contains(id)
    }

    def "Test get product - product price not found"() {
        given:
        def id = "12345678"

        when:
        service.getProduct(id)

        then:
        1 * client.getProductName(id) >> Optional.of("Product name")
        1 * dao.get(id) >> Optional.empty()

        def e = thrown(ProductPriceNotFoundException)
        e.message.contains(id)
    }
}
