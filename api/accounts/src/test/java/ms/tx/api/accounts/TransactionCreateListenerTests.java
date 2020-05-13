package ms.tx.api.accounts;

import ms.tx.api.accounts.entity.Account;
import ms.tx.api.accounts.messaging.MessageSender;
import ms.tx.api.accounts.payload.transaction.TransactionCreation;
import ms.tx.api.accounts.payload.transaction.TransactionState;
import ms.tx.api.accounts.payload.transaction.TransactionType;
import ms.tx.api.accounts.repository.AccountRepository;
import ms.tx.api.accounts.repository.TransactionRepository;
import ms.tx.api.accounts.service.TransactionCreationListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TransactionCreateListenerTests {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private TransactionCreationListener listener;

    @Test
    public void testCreate_hasAccountIncome_shouldSucceedAndAddToBalance() {
        var tx = createTransactionCreation(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                1000L,
                100L,
                TransactionType.INCOME,
                TransactionState.PROCESSING
        );

        var account = new Account();
        account.setId(tx.getId());
        account.setName("My Account");
        account.setUserId(tx.getUserId());
        account.setBalance(500L);

        when(this.accountRepository.findFirstByIdAndUserId(
                any(String.class),
                any(String.class)
        )).thenReturn(account);

        this.listener.onTransactionCreation(tx);

        assertAll(
                () -> assertEquals(TransactionState.ACCEPTED, tx.getState()),
                () -> assertTrue(account.getTransactions().stream().anyMatch(t -> t.getId().equals(tx.getId()))),
                () -> assertEquals(1500L, account.getBalance())
        );
    }

    @Test
    public void testCreate_hasAccountExpense_shouldSucceedAndSubtractFromBalance() {
        var tx = createTransactionCreation(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                1000L,
                100L,
                TransactionType.EXPENSE,
                TransactionState.PROCESSING
        );

        var account = new Account();
        account.setId(tx.getId());
        account.setName("My Account");
        account.setUserId(tx.getUserId());
        account.setBalance(600L);

        when(this.accountRepository.findFirstByIdAndUserId(
                any(String.class),
                any(String.class)
        )).thenReturn(account);

        this.listener.onTransactionCreation(tx);

        assertAll(
                () -> assertEquals(TransactionState.ACCEPTED, tx.getState()),
                () -> assertEquals(400L, account.getBalance())
        );
    }

    @Test
    public void testCreate_hasAccountExpenseNegativeAmount_shouldFail() {
        var tx = createTransactionCreation(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                -1000L,
                100L,
                TransactionType.EXPENSE,
                TransactionState.PROCESSING
        );

        var account = new Account();
        account.setId(tx.getId());
        account.setName("My Account");
        account.setUserId(tx.getUserId());
        account.setBalance(600L);

        when(this.accountRepository.findFirstByIdAndUserId(
                any(String.class),
                any(String.class)
        )).thenReturn(account);

        this.listener.onTransactionCreation(tx);

        assertAll(
                () -> assertEquals(TransactionState.REJECTED, tx.getState()),
                () -> assertEquals(600L, account.getBalance())
        );
    }

    @Test
    public void testCreate_noAccount_shouldFail() {
        var tx = createTransactionCreation(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                1000L,
                100L,
                TransactionType.EXPENSE,
                TransactionState.PROCESSING
        );

        when(this.accountRepository.findFirstByIdAndUserId(
                any(String.class),
                any(String.class)
        )).thenReturn(null);

        this.listener.onTransactionCreation(tx);

        assertEquals(TransactionState.REJECTED, tx.getState());
    }


    @Test
    public void testCreate_nonProcessingStatus_doNothing() {
        var tx = createTransactionCreation(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                1000L,
                100L,
                TransactionType.EXPENSE,
                TransactionState.ACCEPTED
        );

        when(this.accountRepository.findFirstByIdAndUserId(
                any(String.class),
                any(String.class)
        )).thenReturn(null);

        this.listener.onTransactionCreation(tx);

        assertEquals(TransactionState.ACCEPTED, tx.getState());
    }

    private static TransactionCreation createTransactionCreation(
            String id,
            String accountId,
            String userId,
            Long amount,
            Long denominator,
            TransactionType type,
            TransactionState state
    ) {
        var tx = new TransactionCreation();
        tx.setId(id);
        tx.setAccountId(accountId);
        tx.setUserId(userId);
        tx.setAmount(amount);
        tx.setDenominator(denominator);
        tx.setType(type);
        tx.setState(state);

        return tx;
    }
}
