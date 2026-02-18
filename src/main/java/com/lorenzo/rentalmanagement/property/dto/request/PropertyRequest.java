package com.lorenzo.rentalmanagement.property.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class PropertyRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String city;

    @NotNull
    @Positive
    private Integer rooms;
    @NotNull
    @Positive
    private BigDecimal pricePerMonth;

    @NotNull
    private Boolean isActive;

    public PropertyRequest() {}

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }

    public Integer getRooms()
    {
        return rooms;
    }
    public void setRooms(Integer rooms)
    {
        this.rooms = rooms;
    }

    public BigDecimal getPricePerMonth()
    {
        return pricePerMonth;
    }
    public void setPricePerMonth(BigDecimal pricePerMonth)
    {
        this.pricePerMonth = pricePerMonth;
    }

    public Boolean getActive()
    {
        return isActive;
    }
    public void setActive(Boolean isActive)
    {
        this.isActive = isActive;
    }
}
