
import com.myRetail.repository.MongoDBProductRepository
import com.myRetail.repository.PriceDTO
import com.myRetail.repository.ProductPriceDTO
import com.myRetail.domain.ProductResponseDTO
import com.myRetail.service.MyRetailProductService
import spock.lang.Ignore
import spock.lang.Specification

@Ignore("Work in progress")
class MyRetailProductServiceSpec extends Specification {

    MongoDBProductRepository dao = Mock(MongoDBProductRepository)
    MyRetailProductService service = new MyRetailProductService(dao)

    def "Test get product"() {
        given:
        def id = "12345678"
        def productName = "The product name"
        double value = 12.50
        def currency = "USD"

        ProductPriceDTO productPriceDTO = new ProductPriceDTO(id as long, new PriceDTO(value, currency))

        when:
        ProductResponseDTO responseDTO = service.getProduct(id)

        then:
        1 * service.getProductName(id) >> productName
        1 * dao.getProductPrice(id) >> productPriceDTO

        assert responseDTO.id == id as long
        assert responseDTO.name == productName
        assert responseDTO.price.value == value
        assert responseDTO.price.currency == currency
    }
}
