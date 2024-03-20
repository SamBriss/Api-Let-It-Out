// Clase Users
package com.apiLetItOut.Api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private String name;
    private String lastnameP;
    private String lastnameM;
    private String tel;
    private int age;
    private String gender;
    private String token;
    // Otras propiedades de la entidad

    @OneToMany(mappedBy = "user")
    private Set<UsersTherapists> therapists;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastnameP() {
        return lastnameP;
    }

    public void setLastnameP(String lastnameP) {
        this.lastnameP = lastnameP;
    }

    public String getLastnameM() {
        return lastnameM;
    }

    public void setLastnameM(String lastnameM) {
        this.lastnameM = lastnameM;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<UsersTherapists> getTherapists() {
        return therapists;
    }

    public void setTherapists(Set<UsersTherapists> therapists) {
        this.therapists = therapists;
    }
}
