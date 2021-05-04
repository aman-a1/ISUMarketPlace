package com309.springboot.isumarketplace.Repository;

import com309.springboot.isumarketplace.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByEmailAddress(String userEmailAddress);
    List<User> findAll();
}
