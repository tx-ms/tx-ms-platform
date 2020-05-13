package ms.tx.api.writer.controller;

import ms.tx.api.writer.payload.user.UserCreation;
import ms.tx.api.writer.payload.user.UserRequest;
import ms.tx.api.writer.payload.user.UserState;
import ms.tx.api.writer.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserCreation> register(@RequestBody UserRequest request) {
        var result = this.userService.register(request);
        if (result.getState() != UserState.REJECTED) {
            return ResponseEntity.ok(result);
        }

        return new ResponseEntity<>(result, HttpStatus.CONFLICT);
    }
}
