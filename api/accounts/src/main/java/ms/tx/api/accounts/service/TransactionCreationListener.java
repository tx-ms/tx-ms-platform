package ms.tx.api.accounts.service;

import ms.tx.api.accounts.entity.AccountTransaction;
import ms.tx.api.accounts.messaging.MessageSender;
import ms.tx.api.accounts.payload.transaction.TransactionCreation;
import ms.tx.api.accounts.payload.transaction.TransactionState;
import ms.tx.api.accounts.payload.transaction.TransactionType;
import ms.tx.api.accounts.repository.AccountRepository;
import ms.tx.api.accounts.repository.TransactionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionCreationListener {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final MessageSender messageSender;

    public TransactionCreationListener(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            MessageSender messageSender
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.messageSender = messageSender;
    }

    @RabbitListener(queues = "TransactionCreation")
    public void onTransactionCreation(TransactionCreation transaction) {
        if (transaction.getState() != TransactionState.PROCESSING) {
            return;
        }

        var account = this.accountRepository.findFirstByIdAndUserId(
                transaction.getAccountId(),
                transaction.getUserId()
        );

        if (account == null || transaction.getAmount() < 0) {
            transaction.setState(TransactionState.REJECTED);
            this.messageSender.send(transaction);
            return;
        }

        var transactionEntity = new AccountTransaction();
        transactionEntity.setId(transaction.getId());
        transactionEntity.setAmount(transaction.getAmount());
        transactionEntity.setType(transaction.getType());
        transactionEntity.setAccount(account);

        this.transactionRepository.save(transactionEntity);

        /*
         * The balance calculation is now separated
         * from the user command, but can be separated once more,
         * by emitting a message to increase/decrease the balance
         * and another service to handle it,
         * but this will require the SAGA pattern to be more complex now,
         * because we need to be @transactional in relation of
         * Account<->Transaction
         */
        var amount = transaction.getAmount() * (transaction.getType() == TransactionType.INCOME ? 1 : -1);
        account.setBalance(account.getBalance() + amount);
        account.getTransactions().add(transactionEntity);
        this.accountRepository.save(account);

        transaction.setState(TransactionState.ACCEPTED);
        this.messageSender.send(transaction);
    }
}
