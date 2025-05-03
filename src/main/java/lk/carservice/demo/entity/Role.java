package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lk.carservice.demo.enums.ERole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {}

    public Role(ERole name) {
        this.name = name;
    }

    public void setId(Long id) { this.id = Math.toIntExact(id); }
    public String getName() {
        return this.name.toString();  }


    public String name() {
        return this.name.toString();
    }
}
