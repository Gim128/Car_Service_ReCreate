package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int admin_id;

    @Column(name = "username", unique = true) // Add this field
    private String username;

    private String email;
    private String password;
    private String role = "ADMIN";
    private boolean is_active = true;
}
