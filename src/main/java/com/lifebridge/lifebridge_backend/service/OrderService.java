package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.Medicine;
import com.lifebridge.lifebridge_backend.entity.Order;
import com.lifebridge.lifebridge_backend.entity.User;
import com.lifebridge.lifebridge_backend.repository.OrderRepository;
import com.lifebridge.lifebridge_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineService medicineService;

    @Transactional
    public Order placeOrder(Order order) {
        // 1. Validate User and Medicine existence
        User user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found."));

        Medicine medicine = medicineService.findById(order.getMedicine().getId())
                .orElseThrow(() -> new RuntimeException("Medicine not found."));

        // 2. Check stock and availability
        if (!medicine.getIsAvailable() || medicine.getStockQuantity() < order.getQuantity()) {
            throw new RuntimeException("Medicine is out of stock or unavailable.");
        }

        // 3. Calculate price and set timestamps
        BigDecimal itemPrice = medicine.getPrice();
        BigDecimal total = itemPrice.multiply(BigDecimal.valueOf(order.getQuantity()));

        order.setUser(user);
        order.setMedicine(medicine);
        order.setTotalPrice(total);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        // 4. Update stock quantity (CRITICAL STEP)
        medicine.setStockQuantity(medicine.getStockQuantity() - order.getQuantity());
        medicineService.saveMedicine(medicine); // Persist updated stock

        // 5. Save the order
        return orderRepository.save(order);
    }

    /**
     * 🛑 FIX APPLIED: Added @Transactional(readOnly = true) to keep the
     * Hibernate session open long enough to fetch lazy-loaded Medicine data.
     */
    @Transactional
    public List<Order> getOrderHistoryByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUser_IdOrderByOrderDateDesc(userId);
        // Force initialization of medicine data
        orders.forEach(order -> order.getMedicine().getName());
        return orders;
    }

    @Transactional
    public List<Order> getPendingOrders() {
        List<Order> orders = orderRepository.findByStatus("PENDING");
        // Force initialization of medicine data
        orders.forEach(order -> order.getMedicine().getName());
        return orders;
    }

    // Method for the Admin to update tracking status
    @Transactional
    public Optional<Order> updateOrderStatus(Long orderId, String newStatus) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(newStatus);
                    return orderRepository.save(order);
                });
    }
}