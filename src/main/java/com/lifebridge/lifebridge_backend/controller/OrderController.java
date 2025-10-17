package com.lifebridge.lifebridge_backend.controller;

import com.lifebridge.lifebridge_backend.entity.Order;
import com.lifebridge.lifebridge_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. Place a new order (Public)
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        try {
            Order placedOrder = orderService.placeOrder(order);
            return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 2. View user's order history (Public)
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long userId) {
        List<Order> history = orderService.getOrderHistoryByUserId(userId);
        if (history.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    // 3. Admin View: Get all pending orders
    @GetMapping("/admin/pending")
    public ResponseEntity<List<Order>> getPendingOrders(@RequestParam Long userId) {
        if (!userId.equals(7L)) { // Medicine Admin ID check
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Order> pending = orderService.getPendingOrders();
        return new ResponseEntity<>(pending, HttpStatus.OK);
    }

    // 4. Admin Action: Update order status (Tracking)
    @PutMapping("/status/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestParam String newStatus, @RequestParam Long userId) {
        if (!userId.equals(7L)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Order> updatedOrder = orderService.updateOrderStatus(orderId, newStatus);

        return updatedOrder.map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}