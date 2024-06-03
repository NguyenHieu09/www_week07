package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;

import java.util.Optional;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    @Query("SELECT pp FROM ProductPrice pp WHERE pp.product = :product ORDER BY pp.priceDate DESC")
    Optional<ProductPrice> findFirstByProductOrderByPriceDateDesc(Product product);
}