package ms.tx.api.accounts.repository;

import ms.tx.api.accounts.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<AccountTransaction, String> {
}
