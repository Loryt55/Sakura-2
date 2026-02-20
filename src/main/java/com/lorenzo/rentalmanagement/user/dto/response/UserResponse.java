package com.lorenzo.rentalmanagement.user.dto.response;

import java.time.LocalDate;

public class UserResponse {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String roleName;
    private LocalDate createdAt;

    private UserResponse() {}

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
        private String roleName;
        private LocalDate createdAt;

        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder roleName(String roleName) { this.roleName = roleName; return this; }
        public Builder createdAt(LocalDate createdAt) { this.createdAt = createdAt; return this; }

        public UserResponse build() {
            UserResponse response = new UserResponse();
            response.firstName = this.firstName;
            response.lastName = this.lastName;
            response.phoneNumber = this.phoneNumber;
            response.email = this.email;
            response.roleName = this.roleName;
            response.createdAt = this.createdAt;
            return response;
        }
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getRoleName() { return roleName; }
    public LocalDate getCreatedAt() { return createdAt; }
}