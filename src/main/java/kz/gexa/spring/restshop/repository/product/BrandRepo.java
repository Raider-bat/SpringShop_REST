package kz.gexa.spring.restshop.repository.product;

import kz.gexa.spring.restshop.entity.product.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepo extends JpaRepository<Brand, Long> {
}
