package lk.carservice.demo.repository;

import lk.carservice.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByUserType(Integer userType);

    List<User> findByIsActive(Boolean isActive);

    Optional<User> findByUsername(String username);


    // Alternative if your DB column name is different:
    @Query("SELECT u FROM User u WHERE u.isActive = :status")
    List<User> findByActiveStatus(@Param("status") Boolean isActive);
}


