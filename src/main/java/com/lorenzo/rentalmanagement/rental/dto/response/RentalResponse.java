package com.lorenzo.rentalmanagement.rental.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalResponse {

    private Long id;
    private String propertyName;
    private String propertyCity;
    private String userFullName;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;

    private RentalResponse() {}

    public static class Builder {
        private Long id;
        private String propertyName;
        private String propertyCity;
        private String userFullName;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal totalPrice;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder propertyName(String propertyName) { this.propertyName = propertyName; return this; }
        public Builder propertyCity(String propertyCity) { this.propertyCity = propertyCity; return this; }
        public Builder userFullName(String userFullName) { this.userFullName = userFullName; return this; }
        public Builder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public Builder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public Builder totalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; return this; }

        public RentalResponse build() {
            RentalResponse r = new RentalResponse();
            r.id = this.id;
            r.propertyName = this.propertyName;
            r.propertyCity = this.propertyCity;
            r.userFullName = this.userFullName;
            r.startDate = this.startDate;
            r.endDate = this.endDate;
            r.totalPrice = this.totalPrice;
            return r;
        }
    }

    public Long getId() { return id; }
    public String getPropertyName() { return propertyName; }
    public String getPropertyCity() { return propertyCity; }
    public String getUserFullName() { return userFullName; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public BigDecimal getTotalPrice() { return totalPrice; }
}