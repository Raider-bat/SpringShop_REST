package kz.gexa.spring.restshop.controller;

import kz.gexa.spring.restshop.entity.product.Product;
import kz.gexa.spring.restshop.exception.ApiError;
import kz.gexa.spring.restshop.sevice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/products")
public class ProductRestController {

    @Autowired
    ProductService productService;


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = productService.getAllProduct();
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> addProduct(@RequestBody Product product){
        try {
            productService.addProduct(product);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {

            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try {

            productService.deleteProductById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {

            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("saleControl")
    public ResponseEntity<?> saleControl(@RequestParam(required = true) String pause){
        try {
            productService.saleControl(pause);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
    }
}
