package com.lorenzo.rentalmanagement.property.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PropertyResponse {

    private Long id;
    private String name;
    private String address;
    private String city;
    private Integer rooms;
    private BigDecimal pricePerMonth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private PropertyResponse() {}

    public static class Builder {
        private Long id;
        private String name;
        private String address;
        private String city;
        private Integer rooms;
        private BigDecimal pricePerMonth;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder address(String address) { this.address = address; return this; }
        public Builder city(String city) { this.city = city; return this; }
        public Builder rooms(Integer rooms) { this.rooms = rooms; return this; }
        public Builder pricePerMonth(BigDecimal pricePerMonth) { this.pricePerMonth = pricePerMonth; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public PropertyResponse build() {
            PropertyResponse response = new PropertyResponse();
            response.id = this.id;
            response.name = this.name;
            response.address = this.address;
            response.city = this.city;
            response.rooms = this.rooms;
            response.pricePerMonth = this.pricePerMonth;
            response.createdAt = this.createdAt;
            response.updatedAt = this.updatedAt;
            return response;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
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
