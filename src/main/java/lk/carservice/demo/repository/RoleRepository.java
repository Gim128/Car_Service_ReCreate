package lk.carservice.demo.repository;

import lk.carservice.demo.enums.ERole;
import lk.carservice.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


    public interface RoleRepository extends JpaRepository<Role, Long> {
        Optional<Role> findByName(ERole name);
    }

