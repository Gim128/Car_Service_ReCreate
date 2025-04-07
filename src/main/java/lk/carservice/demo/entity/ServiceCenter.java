package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ServiceCenter")
public class ServiceCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_center_id")
    private Integer serviceCenterId;

    @Column(name = "service_center_name", length = 45, nullable = false)
    private String serviceCenterName;

    @Column(name = "service_center_mobile", nullable = false)
    private String serviceCenterMobile;

    @Column(name = "service_center_email", length = 50, nullable = false, unique = true)
    private String serviceCenterEmail;

    @Column(name = "service_center_location", length = 450, nullable = false)
    private String serviceCenterLocation;

    @Column(name = "is_active", columnDefinition = "TINYINT default 1")
    private Boolean isActive;
}
