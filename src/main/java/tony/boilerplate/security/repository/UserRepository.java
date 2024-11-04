package tony.boilerplate.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tony.boilerplate.security.data.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUid(String uid);
}
