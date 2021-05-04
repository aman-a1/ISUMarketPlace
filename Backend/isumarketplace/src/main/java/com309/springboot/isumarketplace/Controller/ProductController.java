package com309.springboot.isumarketplace.Controller;

import com309.springboot.isumarketplace.Exceptions.OnSuccessException;
import com309.springboot.isumarketplace.Exceptions.ResourceNotFoundException;
import com309.springboot.isumarketplace.Exceptions.UserFailedException;
import com309.springboot.isumarketplace.Model.*;
import com309.springboot.isumarketplace.Repository.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com309.springboot.isumarketplace.Controller.ImageController.compressBytes;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    @GetMapping("/GetAllProducts")
    List<Product> getAllProducts(){
        List<Product> getAllProducts = productRepository.findAll();

        for(Product product: getAllProducts){

            Set<Image> productImages = product.getProductImages();

            if(productImages != null || !productImages.isEmpty()){
                for(Image image: productImages){
                    String imageString = new String(image.getImageByte());
                    image.setImageString(imageString);
                    image.setImageByte(null);
                }
            }

            product.setProductImages(productImages);

        }

        return getAllProducts;
    }

    @GetMapping("/Products/{id}")
    Product getProductById(@PathVariable int id){
        Optional<Product> getProductById = productRepository.findById(id);
        Product getProduct = getProductById.get();

        Set<Image> productImages = getProduct.getProductImages();

        if(productImages != null ){
            if(!productImages.isEmpty()){
                for(Image image: productImages){
                    String imageString = new String(image.getImageByte());
                    image.setImageString(imageString);
                    image.setImageByte(null);
                }
            }
        }

        getProduct.setProductImages(productImages);

        return getProduct;
    }

    @GetMapping("/Products/Categories/{productCategory}")
    List<Product> getAllProductsByCategory(@PathVariable String productCategory) throws ResourceNotFoundException {
        List<Product> getAllProductsByCategory = productRepository.findByProductCategory(productCategory);

        for(Product product: getAllProductsByCategory){

            Set<Image> productImages = product.getProductImages();

            if(productImages != null || !productImages.isEmpty()){
                for(Image image: productImages){
                    String imageString = new String(image.getImageByte());
                    image.setImageString(imageString);
                    image.setImageByte(null);
                }
            }

            product.setProductImages(productImages);

        }

        return getAllProductsByCategory;
    }

    @GetMapping("/Products/Categories/{productCategory}/Status/{productStatus}")
    List<Product> getAllProductsByCategoryAndStatus(@PathVariable String productCategory, @PathVariable Boolean productStatus)
            throws ResourceNotFoundException {
        ProductCategory retrievedCategory = productCategoryRepository.findByCategoryName(productCategory);

        if(retrievedCategory == null){
            throw new ResourceNotFoundException("Product Category does not exist");
        }

        List<Product> getAllProductsByCategoryAndStatus = productRepository.findByProductCategoryAndProductStatus(productCategory, productStatus);

        return getAllProductsByCategoryAndStatus;
    }

    @GetMapping("/users/{username}/products")
    public List<Product> getAllProductsByUsername(@PathVariable String username) throws ResourceNotFoundException {
        User retrievedUser = userRepository.findByUserName(username);

        if(retrievedUser == null){
            throw new ResourceNotFoundException("No username found");
        }

        List<Product> getAllProductsByUsername = productRepository.findByProductOwner_userName(username);

        for(Product product: getAllProductsByUsername){

            Set<Image> productImages = product.getProductImages();

            if(productImages != null){
                if(!productImages.isEmpty()){
                    for(Image image: productImages){
                        String imageString = new String(image.getImageByte());
                        image.setImageString(imageString);
                        image.setImageByte(null);
                    }
                }
            }

            product.setProductImages(productImages);

        }

        return getAllProductsByUsername;
    }

    @PostMapping("/users/{username}/products")
    public Product createProduct(@RequestBody Product product, @PathVariable String username)
            throws ResourceNotFoundException, OnSuccessException {

        User retrievedUser = userRepository.findByUserName(username);

        if(retrievedUser == null){
            throw new ResourceNotFoundException("Username not found");
        }

        //Set User to a Product
        product.setOwnerUsername(retrievedUser.getUserName());
        product.setProductOwner(retrievedUser);

        //Add Product to a user
        retrievedUser.addProduct(product);

        //Save Product to repository
        productRepository.save(product);
        userRepository.save(retrievedUser);

        Set<Image> productImages = product.getProductImages();

        if(productImages != null){
            //Save image to repository
            for(Image image: product.getProductImages()){
                Image img = new Image(image.getImageName(), image.getImageType(), image.getImageString().getBytes());

                img.setProductPicture(product);

                imageRepository.save(img);
            }
        }

        //Create Notification
        String notificationMessage = "User " + username + " has uploaded a new product in category " + product.getProductCategory();
        Date date = new Date();

        //Set Notification to all user
        for(User user: userRepository.findAll()){
            if(user.getUserName().equals(retrievedUser.getUserName())){
                continue;
            }
            Notification notification = new Notification(notificationMessage, date, false);

            user.addUserNotifications(notification);
            notification.setUserNotification(user);

            userRepository.save(user);
            notificationRepository.save(notification);
        }

        throw new OnSuccessException("You have successfully upload a product");
    }

    @PutMapping("/users/{username}/products/{productId}")
    public Product updateProduct(@PathVariable String username,
                                 @PathVariable Integer productId,
                                 @RequestBody Product productRequest) throws ResourceNotFoundException {
        if(userRepository.findByUserName(username) == null){
            throw new ResourceNotFoundException("Username not found");
        }

        return productRepository.findById(productId).map(product -> {
            product.setProductName(productRequest.getProductName());
            product.setProductDescription(productRequest.getProductDescription());
            product.setProductCategory(productRequest.getProductCategory());
            product.setProductPrice(productRequest.getProductPrice());
            product.setProductQuantity(productRequest.getProductQuantity());
            product.setProductStatus(productRequest.getProductStatus());

            return productRepository.save(product);

        }).orElseThrow(() -> new ResourceNotFoundException("No product found"));
    }

    @PostMapping("/user/{username}/products/{productid}")
    public ResponseEntity<?> deleteProduct(@PathVariable String username,
                                           @PathVariable Integer productid) throws ResourceNotFoundException, OnSuccessException {
        if(productRepository.findByIdAndProductOwner_userName(productid, username) == null){
            throw new ResourceNotFoundException("No product found with username or product id given");
        }else{
            Product product = productRepository.findByIdAndProductOwner_userName(productid, username);

            Set<Image> productImages = product.getProductImages();

            if(!productImages.isEmpty()){
                for(Image image: productImages){
                    productImages.remove(image);
                    imageRepository.delete(image);
                }
            }

            User getUser = userRepository.findByUserName(username);

            Set<Product> userProducts = getUser.getUserProducts();

            if(!userProducts.isEmpty()){
                for(Product product1: userProducts){
                    if(product == product1){
                        userProducts.remove(product);
                    }
                }
            }

            productRepository.delete(product);

            throw new OnSuccessException("Product has been successfully deleted");
        }

    }

}
