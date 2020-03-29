package kz.gexa.spring.restshop.repository.product;

import kz.gexa.spring.restshop.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}
