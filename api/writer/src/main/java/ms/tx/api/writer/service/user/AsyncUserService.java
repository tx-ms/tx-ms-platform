package ms.tx.api.writer.service.user;

import ms.tx.api.writer.cache.UserCache;
import ms.tx.api.writer.messaging.MessageSender;
import ms.tx.api.writer.payload.user.UserCreation;
import ms.tx.api.writer.payload.user.UserRequest;
import ms.tx.api.writer.payload.user.UserState;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AsyncUserService implements UserService {

    private final MessageSender messageSender;

    private final UserCache userCache;

    private final PasswordEncoder passwordEncoder;

    public AsyncUserService(
            MessageSender messageSender,
            UserCache userCache,
            PasswordEncoder passwordEncoder
    ) {
        this.messageSender = messageSender;
        this.userCache = userCache;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserCreation register(UserRequest input) {
        var viewModel = new UserCreation(
                UUID.randomUUID().toString(),
                input.getUsername(),
                this.passwordEncoder.encode(input.getPassword()),
                UserState.WAITING
        );

        if (!input.getPassword().equals(input.getConfirm())) {
            viewModel.setState(UserState.REJECTED);
        }

        if (this.userCache.has(input.getUsername())) {
            viewModel.setState(UserState.REJECTED);
        }

        if (viewModel.getState() != UserState.REJECTED) {
            this.userCache.set(input.getUsername());
            this.messageSender.send(viewModel);
        }

        return viewModel;
    }
}
