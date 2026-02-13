package com.lorenzo.rentalmanagement.property.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private Double pricePerMonth;
    private String city;
    private Integer rooms;
    private Boolean active;


    public Property() {
    }

    public Property(String name, String address, Double pricePerMonth, String city, Integer rooms, Boolean active) {
        this.name = name;
        this.address = address;
        this.pricePerMonth = pricePerMonth;
        this.city = city;
        this.rooms = rooms;
        this.active = active;
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

    public Double getPricePerMonth() {
        return pricePerMonth;
    }

    public String getCity() {
        return city;
    }

    public Integer getRooms() {
        return rooms;
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

    public void setPricePerMonth(Double pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}