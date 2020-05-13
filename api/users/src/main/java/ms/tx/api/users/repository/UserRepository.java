package ms.tx.api.users.repository;

import ms.tx.api.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);

    User findFirstByUsername(String username);
}
