package com.lazaropower.iwa.backend.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
   //@Size(min=6, max=30)
    private String password;

    @NotBlank
    private String firstname;

    @NotBlank
    private String surname;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_course",
                joinColumns = @JoinColumn(referencedColumnName = "id", name = "userId"),
                inverseJoinColumns = @JoinColumn(referencedColumnName = "id", name = "courseId"))
    private Set<Course> courses = new HashSet<>();

    public User() {

    }

    public User(@NotBlank String firstname, @NotBlank String surname, @NotBlank @Email String email, String password) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
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
