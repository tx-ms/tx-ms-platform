package ms.tx.api.accounts.repository;

import ms.tx.api.accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

    Account findFirstByIdAndUserId(String id, String userId);

    boolean existsByNameAndUserId(String name, String userId);

}
