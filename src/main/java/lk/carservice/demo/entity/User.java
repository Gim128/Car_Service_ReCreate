package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int user_id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", unique = true, nullable = false)
    private String last_name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "is_active", columnDefinition = "TINYINT(1) default 1")
    private Boolean is_active;

    public enum Role {
        ADMIN,
        USER
    }
}
