package ms.tx.api.accounts.service;

import ms.tx.api.accounts.payload.AccountViewModel;
import ms.tx.api.accounts.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountViewModel getAccountInfo(String accountId, String userId) {
        var account = this.accountRepository.findFirstByIdAndUserId(
                accountId,
                userId
        );

        if (account == null) {
            return null;
        }

        return new AccountViewModel(
                account.getId(),
                account.getName(),
                account.getBalance()
        );
    }
}
