package com.lifebridge.lifebridge_backend.repository;

import com.lifebridge.lifebridge_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_IdOrderByOrderDateDesc(Long userId);
    List<Order> findByStatus(String status);
}