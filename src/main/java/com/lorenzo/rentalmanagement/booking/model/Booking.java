package com.lorenzo.rentalmanagement.booking.model;

import com.lorenzo.rentalmanagement.user.model.User;
import com.lorenzo.rentalmanagement.property.model.Property;
import jakarta.persistence.*;
import java.time.LocalDate;

    @Entity
    public class Booking {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "property_id", nullable = false)
        private Property property;
        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;
        private LocalDate startDate;
        private LocalDate endDate;
        private Double totalPrice;
        private Boolean active;

        public Booking() {
        }

        public Booking(Property property, User user, LocalDate startDate, LocalDate endDate, Double totalPrice, Boolean active) {
            this.property = property;
            this.user = user;
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

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
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

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }
}