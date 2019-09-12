package service

import com.myRetail.domain.Price
import com.myRetail.domain.ProductPrice
import com.myRetail.domain.Product
import com.myRetail.repository.ProductPriceDao
import com.myRetail.service.MyRetailProductService
import com.myRetail.service.ProductNameClient
import spock.lang.Specification

class MyRetailProductServiceSpec extends Specification {

    ProductPriceDao dao = Mock(ProductPriceDao)
    ProductNameClient client = Mock(ProductNameClient)
    MyRetailProductService service = new MyRetailProductService(dao, client)

    def "Test get product"() {
        given:
        def id = "12345678"
        def productName = "The product name"
        double value = 12.50
        def currency = "USD"

        Price price = new Price(value, currency)
        ProductPrice productPrice = new ProductPrice(id as long, price)

        when:
        Product responseDTO = service.getProduct(id)

        then:
        1 * client.getProductName(id) >> productName
        1 * dao.get(id) >> Optional.of(productPrice)

        assert responseDTO.id == id as long
        assert responseDTO.name == productName
        assert responseDTO.price.value == value
        assert responseDTO.price.currencyCode == currency
    }
}
