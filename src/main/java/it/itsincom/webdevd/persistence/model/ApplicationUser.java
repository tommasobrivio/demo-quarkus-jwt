package it.itsincom.webdevd.persistence.model;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;

@Entity
@Table(name = "User")
@UserDefinition
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Username", length = 100, unique = true, nullable = false)
    @Username
    private String username;

    @Column(name = "Password", nullable = false)
    @Password
    private String password;

    @Column(name = "Role", length = 100, nullable = false)
    @Roles
    private String role;

    @Column(name = "FirstName", length = 100, nullable = false)
    private String firstName;

    @Column(name = "SecondName", length = 100, nullable = false)
    private String secondName;

    @Column(name = "Address", nullable = false)
    private String address;

    public ApplicationUser() {
    }

    public ApplicationUser(String username, String password, String role, String firstName, String secondName, String address) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getSecondName() { return secondName; }

    public void setSecondName(String secondName) { this.secondName = secondName; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }
}
