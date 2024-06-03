package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductDetail;

import vn.edu.iuh.fit.backend.repositories.ProductImageRepository;
import vn.edu.iuh.fit.backend.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;

import java.util.Optional;


@Service
public class ProductServices {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;
    public Page<ProductDetail> findAll(int pageNo, int pageSize, String sortBy,
                                   String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::ProductDetail);
//        return productRepository.findAll(pageable);
    }

    public ProductDetail ProductDetail(Product product){
       ProductDetail productDetail  = new ProductDetail();
       productDetail.setId(product.getProductId());
       productDetail.setName(product.getName());
       productDetail.setDescription(product.getDescription());
       productDetail.setProductImage(productImageRepository.findProductImageByProduct_ProductId(product.getProductId()).get().getPath());
       productDetail.setPrice(productPriceRepository.findFirstByProductOrderByPriceDateDesc(product).get().getPrice());
       productDetail.setUnit(product.getUnit());
       productDetail.setManufacturer(product.getManufacturer());
       return productDetail;
    }

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }


}
