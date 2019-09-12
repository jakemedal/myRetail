
import com.myRetail.repository.PriceDTO
import com.myRetail.repository.ProductPriceDTO
import com.myRetail.domain.Product
import com.myRetail.repository.ProductRepository
import com.myRetail.service.MyRetailProductService
import com.myRetail.service.ProductNameClient
import spock.lang.Specification

class MyRetailProductServiceSpec extends Specification {

    ProductRepository dao = Mock(ProductRepository)
    ProductNameClient client = Mock(ProductNameClient)
    MyRetailProductService service = new MyRetailProductService(dao, client)

    def "Test get product"() {
        given:
        def id = "12345678"
        def productName = "The product name"
        double value = 12.50
        def currency = "USD"

        ProductPriceDTO productPriceDTO = new ProductPriceDTO()
        productPriceDTO.setId(id as long)

        PriceDTO price = new PriceDTO()
        price.setValue(value)
        price.setCurrency_code(currency)
        productPriceDTO.setPrice(price)

        when:
        Product responseDTO = service.getProduct(id)

        then:
        1 * client.getProductName(id) >> productName
        1 * dao.getProductPriceById(id) >> productPriceDTO

        assert responseDTO.id == id as long
        assert responseDTO.name == productName
        assert responseDTO.price.value == value
        assert responseDTO.price.currencyCode == currency
    }
}
