package kz.gexa.spring.restshop.repository.user;


import kz.gexa.spring.restshop.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Long deleteUserById(Long id);
}
