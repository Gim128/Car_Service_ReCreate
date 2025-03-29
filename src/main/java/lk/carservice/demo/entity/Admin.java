package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "admin_users")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id", unique = true)
    private int admin_id;

    @Column(name = "admin_username", unique = true, nullable = false) // Add this field
    private String username;

    @Column(name = "admin_email", unique = true, nullable = false)
    private String email;

    @Column(name = "admin_password", nullable = false)
    private String password;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "admin_joined_date", updatable = false)
    private Date adminJoinedDate;

    @Column(name = "role", nullable = false)
    private String role = "ADMIN";

    @Column(name = "is_active", columnDefinition = "TINYINT(1) default 1")
    private boolean is_active = true;

    @Column(name = "is_deleted", columnDefinition = "TINYINT(1) default 0")
    private Boolean isDeleted;
}
