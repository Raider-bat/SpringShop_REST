package kz.gexa.spring.restshop.repository.product;

import kz.gexa.spring.restshop.entity.product.SaleControl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleControlRepo extends JpaRepository<SaleControl, Long> {
}
