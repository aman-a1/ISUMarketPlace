package com309.springboot.isumarketplace.Repository;

import com309.springboot.isumarketplace.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findByimageName(String imageName);
    List<Image> findByProductPicture_id(Integer productId);
    Image findByIdAndProductPicture_id(Integer id, Integer productId);
    Image findByUserDp_userName(String username);
    Image findByIdAndUserDp_userName(Integer id, String username);
}
