package com.lorenzo.rentalmanagement.property.dto.response;

import java.math.BigDecimal;

public class PropertyResponse {

    private String name;
    private String address;
    private String city;
    private Integer rooms;
    private BigDecimal pricePerMonth;
    private Boolean active;

    public PropertyResponse() {}

    public PropertyResponse(String name, String address, String city, Integer rooms, BigDecimal pricePerMonth, Boolean active) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.rooms = rooms;
        this.pricePerMonth = pricePerMonth;
        this.active = active;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
