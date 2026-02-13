package com.lorenzo.rentalmanagement.user.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "app_user") // nome tabella personalizzato in quanto PostgreSQL non accetta "user" in quanto parola riservata
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDate createdAt;
    private Boolean active;

    public User() {
    }

    public User(String firstName, String lastName, String phoneNumber, String email,  String password, Role role,  LocalDate createdAt, Boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.active = active;
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

    public String getPassword() {return password;}

    public Role getRole()  {return role;}

    public LocalDate getCreatedAt() {return createdAt;}

    public Boolean getActive() {return active;}




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

    public void setPassword(String password) {this.password = password;}

    public void setRole(Role role) {this.role = role;}

    public void setCreatedAt(LocalDate createdAt) {this.createdAt = createdAt;}

    public void setActive(Boolean active) {this.active = active;}

}