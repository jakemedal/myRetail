
import com.myRetail.dao.ProductDAO
import com.myRetail.domain.PriceDTO
import com.myRetail.domain.ProductPriceDTO
import com.myRetail.domain.ProductResponseDTO
import com.myRetail.service.ProductService
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class ProductServiceSpec extends Specification {

    ProductDAO dao = Mock(ProductDAO)
    ProductService service = new ProductService(dao)

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
