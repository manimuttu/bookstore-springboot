package com.bnp.bookstore.repository;

import com.bnp.bookstore.model.CartOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<CartOrder, Long> {
    List<CartOrder> findByUsername(String username);
}