package kz.gexa.spring.restshop.sevice;

import kz.gexa.spring.restshop.entity.product.Brand;
import kz.gexa.spring.restshop.entity.product.Category;
import kz.gexa.spring.restshop.entity.product.Product;
import kz.gexa.spring.restshop.entity.product.SaleControl;
import kz.gexa.spring.restshop.repository.product.BrandRepo;
import kz.gexa.spring.restshop.repository.product.CategoryRepo;
import kz.gexa.spring.restshop.repository.product.ProductsRepo;
import kz.gexa.spring.restshop.repository.product.SaleControlRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductsRepo productsRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    SaleControlRepo saleControlRepo;

    public List<Product> getAllProduct(){
        return productsRepo.findAll();
    }


    public void addProduct(Product product) throws Exception {

        Brand brand = product.getBrand();
        Category category = product.getCategory();
        Optional<Brand> optionalBrand = brandRepo.findById(brand.getId());
        Optional<Category> optionalCategory = categoryRepo.findById(category.getId());

        if (
                product.getName().length() > 6 &&
                        product.getName().length() < 40 &&
                        product.getDescription().length() >100 &&
                        product.getDescription().length() < 500
        ){
            if (optionalBrand.isPresent() && optionalCategory.isPresent()){
                product.setBrand(optionalBrand.get());
                product.setCategory(optionalCategory.get());
                productsRepo.save(product);
            }else {

                throw new Exception("Invalid category or brand");
            }
        }else {

            throw new Exception("Invalid data");
        }
    }

    @Transactional
    public void deleteProductById(Long id) throws Exception {
        Long countDeletedProducts = productsRepo.deleteProductById(id);

         if (countDeletedProducts == 0){
             throw new Exception("Product not found");
         }
    }

    public void saleControl(String pause) throws Exception {
        SaleControl saleControl = new SaleControl();
        if (pause.equals("yes") || pause.equals("Y")|| pause.equals("y"))
        {
            saleControl.setSaleProducts(false);
            saleControlRepo.save(saleControl);
        }else if (pause.equals("no") || pause.equals("N")|| pause.equals("n")){
            saleControl.setSaleProducts(true);
            saleControlRepo.save(saleControl);
        }else {
            throw new Exception("Invalid param");
        }
    }
}
