package com309.springboot.isumarketplace.Repository;

import com309.springboot.isumarketplace.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Override
    List<Product> findAll();
    List<Product> findByProductOwner_userName(String username);
    Product findByIdAndProductOwner_userName(Integer integer, String username);
    List<Product> findByproductStatus(Boolean productStatus);
    List<Product> findBydatePosted(LocalDate date);
    List<Product> findByProductCategory(String productCategory);
    List<Product> findByProductCategoryAndProductStatus(String productCategory, Boolean productStatus);
}
