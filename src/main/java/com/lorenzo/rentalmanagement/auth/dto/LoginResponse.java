package com.lorenzo.rentalmanagement.auth.dto;

public class LoginResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String roleName;
    private String token;

    public LoginResponse(Long id, String firstName, String lastName, String email, String roleName,  String token) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleName = roleName;
        this.token = token;
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

    public String getEmail() {
        return email;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getToken() {
        return token;
    }
}
