package com.lorenzo.rentalmanagement.user.dto.response;

import java.time.LocalDate;

public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String roleName;
    private LocalDate createdAt;
    private Boolean active;

    private User() {}

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
        private String roleName;
        private LocalDate createdAt;
        private Boolean active;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder roleName(String roleName) { this.roleName = roleName; return this; }
        public Builder createdAt(LocalDate createdAt) { this.createdAt = createdAt; return this; }
        public Builder active(Boolean active) { this.active = active; return this; }

        public User build() {
            User response = new User();
            response.id = this.id;
            response.firstName = this.firstName;
            response.lastName = this.lastName;
            response.phoneNumber = this.phoneNumber;
            response.email = this.email;
            response.roleName = this.roleName;
            response.createdAt = this.createdAt;
            response.active = this.active;
            return response;
        }
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getRoleName() { return roleName; }
    public LocalDate getCreatedAt() { return createdAt; }
    public Boolean getActive() { return active; }
}