package vn.edu.iuh.fit.www_week07_nguyenthitrunghieu_20020381;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class WwwWeek07NguyenThiTrungHieu20020381ApplicationTests {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindFirstByProductOrderByPriceDateTimeDesc() {

        Product product = new Product("hi", "ji", "hi", "ji", ProductStatus.ACTIVE);


        ProductPrice latestPrice = new ProductPrice(LocalDateTime.now(), 1000.0, "Giá mới nhất");
        latestPrice.setProduct(product);

        productRepository.save(product);
        productPriceRepository.save(latestPrice);


        Optional<ProductPrice> foundPrice = productPriceRepository.findFirstByProductOrderByPriceDateDesc(product);


        Assertions.assertTrue(foundPrice.isPresent());

    }

}
