package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String address;
    private String mobile;
    private Boolean is_active;

    public enum Role {
        ADMIN,
        USER
    }
}
