package com.lorenzo.rentalmanagement.rental.domain.entity;

import com.lorenzo.rentalmanagement.user.domain.entity.User;
import com.lorenzo.rentalmanagement.property.domain.entity.Property;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

    @Entity
    public class Rental {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "property_id", nullable = false)
        private Property property;
        @ManyToOne
        @JoinColumn(name = "tenant_id", nullable = false)
        private User Tenant;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal totalPrice;
        private Boolean active;

        public Rental() {
        }

        public Rental(Property property, User Tenant, LocalDate startDate, LocalDate endDate, BigDecimal totalPrice, Boolean active) {
            this.property = property;
            this.Tenant = Tenant;
            this.startDate = startDate;
            this.endDate = endDate;
            this.totalPrice = totalPrice;
            this.active = active;
        }

        public Long getId() {
            return id;
        }

        public Property getProperty() {
            return property;
        }

        public void setProperty(Property property) {
            this.property = property;
        }

        public User getTenant() {
            return Tenant;
        }

        public void setTenant(User tenant) {
            this.Tenant = tenant;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }
}