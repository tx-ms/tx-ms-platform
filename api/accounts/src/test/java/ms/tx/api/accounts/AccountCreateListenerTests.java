package ms.tx.api.accounts;

import ms.tx.api.accounts.messaging.MessageSender;
import ms.tx.api.accounts.payload.account.AccountCreation;
import ms.tx.api.accounts.payload.account.AccountState;
import ms.tx.api.accounts.repository.AccountRepository;
import ms.tx.api.accounts.service.AccountCreationListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AccountCreateListenerTests {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private AccountCreationListener listener;

    @Test
    public void testCreate_freeName_shouldSucceed() {
        var account = createAccountCreation(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                AccountState.WAITING,
                "My Account"
        );

        when(this.accountRepository.existsByNameAndUserId(
                any(String.class),
                any(String.class)
        )).thenReturn(false);

        this.listener.onAccountCreation(account);

        assertEquals(AccountState.CREATED, account.getState());
    }

    @Test
    public void testCreate_takenName_shouldFail() {
        var account = createAccountCreation(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                AccountState.WAITING,
                "My Account"
        );

        when(this.accountRepository.existsByNameAndUserId(
                any(String.class),
                any(String.class)
        )).thenReturn(true);

        this.listener.onAccountCreation(account);

        assertEquals(AccountState.REJECTED, account.getState());
    }

    @Test
    public void testCreate_nonWaitingStatus_doNothing() {
        var account = createAccountCreation(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                AccountState.CREATED,
                "My Account"
        );

        when(this.accountRepository.existsByNameAndUserId(
                any(String.class),
                any(String.class)
        )).thenReturn(true);

        this.listener.onAccountCreation(account);

        assertEquals(AccountState.CREATED, account.getState());
    }

    private static AccountCreation createAccountCreation(
            String id,
            String userId,
            AccountState state,
            String name
    ) {
        var account = new AccountCreation();
        account.setId(id);
        account.setName(name);
        account.setUserId(userId);
        account.setState(state);

        return account;
    }
}
