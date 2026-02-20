package com.lorenzo.rentalmanagement.property.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String city;
    private Integer rooms;
    private BigDecimal pricePerMonth;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Property() {
    }

    public Property(String name, String address, String city, Integer rooms, BigDecimal pricePerMonth) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.rooms = rooms;
        this.pricePerMonth = pricePerMonth;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public Integer getRooms() {
        return rooms;
    }

    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public Boolean getActive() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public void setPricePerMonth(BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}