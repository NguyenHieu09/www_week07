package vn.edu.iuh.fit;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductImage;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.repositories.ProductImageRepository;
import vn.edu.iuh.fit.backend.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;

import java.time.LocalDateTime;

@SpringBootApplication
public class WwwWeek07NguyenThiTrungHieu20020381Application {

    public static void main(String[] args) {
        SpringApplication.run(WwwWeek07NguyenThiTrungHieu20020381Application.class, args);
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Bean
    CommandLineRunner init(){
        return args -> {
            Faker faker = new Faker();
            for (int i=1; i<40; i++){
                Product product = new Product(faker.commerce().productName(),
                        faker.lorem().sentence(),"kg",faker.company().name(),  ProductStatus.ACTIVE);

                ProductImage image = new ProductImage("https://res.cloudinary.com/dxvrdtaky/image/upload/v1701286492/gao_l7f0rs.jpg","gao");
                image.setProduct(product);

                ProductPrice productPrice = new ProductPrice(LocalDateTime.now(),faker.number().randomDouble(3, 100, 1000),"gao xuat khau");
                productPrice.setProduct(product);

                productRepository.save(product);
                productImageRepository.save(image);
                productPriceRepository.save(productPrice);

            }
        };
    }


}
