package com.lifebridge.lifebridge_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)  // Changed to EAGER loading for medicine
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine; // Link to the actual medicine

    private Integer quantity;
    private BigDecimal totalPrice;
    private LocalDateTime orderDate;
    private String status = "PENDING";

    // Setters for JSON deserialization (like previous entities)
    @JsonProperty("user")
    private void unpackNestedUser(User user) { this.user = user; }

    @JsonProperty("medicineId") // Expecting medicineId from frontend
    private void setMedicineId(Long medicineId) {
        this.medicine = new Medicine();
        this.medicine.setId(medicineId);
    }

    // Default Constructor and Getters/Setters

    public Order() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Medicine getMedicine() { return medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}