package com.lazaropower.iwa.backend.message.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignUpForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min=6, max=30)
    private String firstname;

    @NotBlank
    @Size(min=6, max=30)
    private String surname;

    private Set<String> role;

    @NotBlank
    @Size(min=6, max=30)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
