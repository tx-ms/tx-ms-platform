package ms.tx.api.users.service;

import ms.tx.api.users.entity.User;
import ms.tx.api.users.messaging.MessageSender;
import ms.tx.api.users.payload.UserCreation;
import ms.tx.api.users.payload.UserState;
import ms.tx.api.users.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRegisterListener {

    private final UserRepository userRepository;

    private final MessageSender messageSender;

    public UserRegisterListener(UserRepository userRepository, MessageSender messageSender) {
        this.userRepository = userRepository;
        this.messageSender = messageSender;
    }

    @RabbitListener(queues = "UserCreation")
    public void onUserRegisterRequest(UserCreation user) {
        if (user.getState() != UserState.WAITING) {
            return;
        }

        if (this.userRepository.existsByUsername(user.getUser())) {
            user.setState(UserState.REJECTED);
            this.messageSender.send(user);
            return;
        }


        var userEntity = new User();
        userEntity.setId(user.getId());
        userEntity.setUsername(user.getUser());
        userEntity.setPassword(user.getPasswordHash());
        this.userRepository.save(userEntity);

        user.setState(UserState.REGISTERED);
        this.messageSender.send(user);
    }
}
