package com309.springboot.isumarketplace.Controller;

import com309.springboot.isumarketplace.Model.ProductCategory;
import com309.springboot.isumarketplace.Repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductCategoryController {
    @Autowired
    ProductCategoryRepository ProductCategoryRepo;

    @GetMapping("/AllProductCategory")
    List<ProductCategory> getAllProductCategory(){
        return ProductCategoryRepo.findAll();
    }
}
