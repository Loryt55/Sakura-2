package com.lorenzo.rentalmanagement.user.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDate createdAt;
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public static class Builder {
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
        private String password;
        private LocalDate createdAt;
        private Boolean active;
        private Role role;

        public User.Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public User.Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public User.Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public User.Builder email(String email) {
            this.email = email;
            return this;
        }

        public User.Builder password(String password) {
            this.password = password;
            return this;
        }

        public User.Builder createdAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public User.Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public User.Builder role(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            User user = new User();
            user.firstName = this.firstName;
            user.lastName = this.lastName;
            user.phoneNumber = this.phoneNumber;
            user.password = this.password;
            user.email = this.email;
            user.createdAt = this.createdAt;
            user.active = this.active;
            user.role = this.role;
            return user;
        }
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Boolean getActive() {
        return active;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}