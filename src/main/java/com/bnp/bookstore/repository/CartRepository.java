package com.bnp.bookstore.repository;

import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByAppUser(AppUser appUser);
}