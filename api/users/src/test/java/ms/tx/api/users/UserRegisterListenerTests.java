package ms.tx.api.users;

import ms.tx.api.users.messaging.MessageSender;
import ms.tx.api.users.payload.UserCreation;
import ms.tx.api.users.payload.UserState;
import ms.tx.api.users.repository.UserRepository;
import ms.tx.api.users.service.UserRegisterListener;
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
public class UserRegisterListenerTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private UserRegisterListener listener;

    @Test
    public void testOnRegister_freeUsername_shouldSucceed() {
        var user = createUserCreation(
                UUID.randomUUID().toString(),
                "John",
                "21qewfgt2$^YFr",
                UserState.WAITING
        );

        when(this.userRepository.existsByUsername(any(String.class)))
                .thenReturn(false);

        this.listener.onUserRegisterRequest(user);

        assertEquals(UserState.REGISTERED, user.getState());
    }

    @Test
    public void testOnRegister_takenUsername_shouldFail() {
        var user = createUserCreation(
                UUID.randomUUID().toString(),
                "John",
                "21qewfgt2$^YFr",
                UserState.WAITING
        );

        when(this.userRepository.existsByUsername(any(String.class)))
                .thenReturn(true);

        this.listener.onUserRegisterRequest(user);

        assertEquals(UserState.REJECTED, user.getState());
    }

    @Test
    public void testOnRegister_nonWaitingStatus_doNothing() {
        var user = createUserCreation(
                UUID.randomUUID().toString(),
                "John",
                "21qewfgt2$^YFr",
                UserState.REGISTERED
        );

        // this stub is actually not used in the service
        // but we could not be sure, that's what we really test
        // so we set Mockito to be lenient here
        when(this.userRepository.existsByUsername(any(String.class)))
                .thenReturn(true);

        this.listener.onUserRegisterRequest(user);

        assertEquals(UserState.REGISTERED, user.getState());
    }

    private static UserCreation createUserCreation(
            String id,
            String username,
            String passwordHash,
            UserState state
    ) {
        var user = new UserCreation();
        user.setId(id);
        user.setUser(username);
        user.setPasswordHash(passwordHash);
        user.setState(state);

        return user;
    }
}
