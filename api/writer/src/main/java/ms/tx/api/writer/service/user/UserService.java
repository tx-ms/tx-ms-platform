package ms.tx.api.writer.service.user;

import ms.tx.api.writer.payload.user.UserCreation;
import ms.tx.api.writer.payload.user.UserRequest;

public interface UserService {

    UserCreation register(UserRequest input);

}
