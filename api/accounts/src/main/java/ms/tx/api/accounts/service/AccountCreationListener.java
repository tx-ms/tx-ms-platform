package ms.tx.api.accounts.service;

import ms.tx.api.accounts.entity.Account;
import ms.tx.api.accounts.messaging.MessageSender;
import ms.tx.api.accounts.payload.account.AccountCreation;
import ms.tx.api.accounts.payload.account.AccountState;
import ms.tx.api.accounts.repository.AccountRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountCreationListener {

    private final AccountRepository accountRepository;

    private final MessageSender messageSender;

    public AccountCreationListener(AccountRepository accountRepository, MessageSender messageSender) {
        this.accountRepository = accountRepository;
        this.messageSender = messageSender;
    }

    @RabbitListener(queues = "AccountCreation")
    public void onAccountCreation(AccountCreation account) {
        if (account.getState() != AccountState.WAITING) {
            return;
        }

        if (this.accountRepository.existsByNameAndUserId(account.getName(), account.getUserId())) {
            account.setState(AccountState.REJECTED);
            this.messageSender.send(account);
            return;
        }

        var accountEntity = new Account();
        accountEntity.setId(account.getId());
        accountEntity.setName(account.getName());
        accountEntity.setUserId(account.getUserId());
        accountEntity.setBalance(0L);
        this.accountRepository.save(accountEntity);

        account.setState(AccountState.CREATED);
        this.messageSender.send(account);
    }
}
