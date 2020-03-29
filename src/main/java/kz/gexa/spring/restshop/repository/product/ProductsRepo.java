package kz.gexa.spring.restshop.repository.product;

import kz.gexa.spring.restshop.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo extends JpaRepository<Product, Long> {

    Long deleteProductById(Long id);
}
