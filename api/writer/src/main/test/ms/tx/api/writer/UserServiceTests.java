package main.test.ms.tx.api.writer;

import ms.tx.api.writer.cache.UserCache;
import ms.tx.api.writer.messaging.MessageSender;
import ms.tx.api.writer.payload.user.UserRequest;
import ms.tx.api.writer.payload.user.UserState;
import ms.tx.api.writer.service.user.AsyncUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserCache userCache;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private AsyncUserService asyncUserService;

    @Test
    public void testRegister_withEqualsPasswordAndUniqueUser_shouldSucceed() {
        final var request = createUserRequest("Random", "123", "123");
        given(this.userCache.has(any(String.class))).willReturn(false);

        var result = this.asyncUserService.register(request);

        assertEquals(UserState.WAITING, result.getState());
    }

    @Test
    public void testRegister_withWrongPasswords_shouldReject() {
        final var request = createUserRequest("Random", "123", "321");
        given(this.userCache.has(any(String.class))).willReturn(false);

        var result = this.asyncUserService.register(request);

        assertEquals(UserState.REJECTED, result.getState());
    }

    @Test
    public void testRegister_withExistingUser_shouldReject() {
        final var request = createUserRequest("Random", "123", "123");
        given(this.userCache.has("Random")).willReturn(true);

        var result = this.asyncUserService.register(request);

        assertEquals(UserState.REJECTED, result.getState());
    }

    private static UserRequest createUserRequest(
            String username,
            String password,
            String confirm
    ) {
        var user = new UserRequest();
        user.setUsername(username);
        user.setPassword(password);
        user.setConfirm(confirm);

        return user;
    }
}
