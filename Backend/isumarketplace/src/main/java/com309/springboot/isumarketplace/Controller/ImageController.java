package com309.springboot.isumarketplace.Controller;

import com309.springboot.isumarketplace.Exceptions.OnSuccessException;
import com309.springboot.isumarketplace.Exceptions.ResourceNotFoundException;
import com309.springboot.isumarketplace.Exceptions.UserFailedException;
import com309.springboot.isumarketplace.Model.Image;
import com309.springboot.isumarketplace.Model.User;
import com309.springboot.isumarketplace.Repository.ImageRepository;
import com309.springboot.isumarketplace.Repository.ProductRepository;
import com309.springboot.isumarketplace.Repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
public class ImageController {
    @Autowired
    ImageRepository imageRepo;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/Image/Upload")
    public BodyBuilder uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException{
        Image img = new Image(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));//

        imageRepo.save(img);

        return ResponseEntity.status(HttpStatus.OK); // response 200
    }

    @GetMapping("/Image/Get/{imageName}")
    public Image getImage(@PathVariable String imageName){
        Image retrievedImage = imageRepo.findByimageName(imageName);

        Image img = new Image(retrievedImage.getImageName(), retrievedImage.getImageType(),
                decompressBytes(retrievedImage.getImageByte()));

        return img;
    }

    @GetMapping("/User/{username}/Image")
    public Image getUserProfilePicture(@PathVariable String username) throws ResourceNotFoundException {
       User retrievedUser = userRepository.findByUserName(username);

       if(retrievedUser == null){
           throw new ResourceNotFoundException("Username not found");
       }

       Image userImage = retrievedUser.getProfilePicture();
       String imageString = new String(userImage.getImageByte());

       Image img = new Image(userImage.getImageName(), userImage.getImageType(), imageString);

       return img;
    }

    @GetMapping("/Product/{productId}/Images")
    public List<Image> getAllImagesByProductId(@PathVariable Integer productId){
        return imageRepo.findByProductPicture_id(productId);
    }

    @PostMapping("/products/{productId}/image")
    public Image createProductImage(@PathVariable Integer productId,
                                    @RequestParam("imageFile") MultipartFile file)
            throws IOException, UserFailedException {
        Image img = new Image(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));

        return productRepository.findById(productId).map(product -> {
            img.setProductPicture(product);
            product.addProductImage(img);

            return imageRepo.save(img);
        }).orElseThrow(() -> new UserFailedException("Product Id not found"));
    }

    @PostMapping("/Users/{username}/UploadImage")
    public Image uploadUserProfilePicture(@PathVariable String username, @RequestBody Image image)
            throws ResourceNotFoundException, OnSuccessException {
        User retrievedUser = userRepository.findByUserName(username);

        if(retrievedUser == null){
            throw new ResourceNotFoundException("Username not found");
        }

        Image newImage = new Image(image.getImageName(), image.getImageType(), image.getImageByte());

        retrievedUser.setProfilePicture(newImage);
        newImage.setUserDp(retrievedUser);

        imageRepo.save(newImage);
        userRepository.save(retrievedUser);

        throw new OnSuccessException("You have successfully uploaded a new profile picture");
    }

    @PutMapping("/products/{productId}/images/{imageId}")
    public Image updateProductImage(@PathVariable Integer productId,
                                    @PathVariable Integer imageId,
                                    @RequestParam("imageFile") MultipartFile file) throws IOException, ResourceNotFoundException {

        Image img = new Image(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));

        if(productRepository.findById(productId) == null){
            throw new ResourceNotFoundException("No product Id Found");
        }

        return imageRepo.findById(imageId).map(image -> {
            image.setImageName(img.getImageName());
            image.setImageType(img.getImageType());
            image.setImageByte(img.getImageByte());

            return imageRepo.save(image);
        }).orElseThrow(() -> new ResourceNotFoundException("Image Id not found"));
    }

    @PutMapping("/users/{username}/images/{imageid}")
    public Image updateUserProfilePicture(@PathVariable String username, @PathVariable Integer imageid,
                                          @RequestParam("imageFile") MultipartFile file) throws IOException, ResourceNotFoundException {

        Image img = new Image(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));

        if(userRepository.findByUserName(username) == null){
            throw new ResourceNotFoundException("No username found");
        }

        return imageRepo.findById(imageid).map(image -> {
            image.setImageName(img.getImageName());
            image.setImageType(img.getImageType());
            image.setImageByte(img.getImageByte());

            return imageRepo.save(image);
        }).orElseThrow(() -> new ResourceNotFoundException("Image Id not found"));
    }

    @DeleteMapping("/products/{productId}/images/{imageId}")
    public ResponseEntity<?> deleteProductImage(@PathVariable Integer productId,
                                                @PathVariable Integer imageId) throws ResourceNotFoundException {
        Image retrievedImage = imageRepo.findByIdAndProductPicture_id(imageId, productId);

        if(retrievedImage == null){
            throw new ResourceNotFoundException("No Product Id or Image Id found");
        }else{
            imageRepo.delete(retrievedImage);
            return ResponseEntity.ok().build();
        }

    }

    @DeleteMapping("/users/{username}/images/{imageId}")
    public ResponseEntity<?> deleteUserProfilePicture(@PathVariable String username, @PathVariable Integer imageId)
            throws ResourceNotFoundException {
        Image retrievedImage = imageRepo.findByIdAndUserDp_userName(imageId, username);

        if(retrievedImage == null){
            throw new ResourceNotFoundException("No Product Id or Image Id found");
        }else{
            imageRepo.delete(retrievedImage);
            return ResponseEntity.ok().build();
        }

    }

    public static byte[] compressBytes(byte[] data){
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];

        while(!deflater.finished()){
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        try {
            outputStream.close();
        }catch (IOException ie){

        }

        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];

        try{
            while(!inflater.finished()){
                int count = inflater.inflate(buffer);

                outputStream.write(buffer, 0, count);
            }
            outputStream.close();

        } catch (IOException ie){

        } catch (DataFormatException dte){

        }

        return outputStream.toByteArray();
    }
}
