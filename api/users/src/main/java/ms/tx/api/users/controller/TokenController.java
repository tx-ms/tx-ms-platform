package ms.tx.api.users.controller;

import ms.tx.api.users.payload.UserLoginRequest;
import ms.tx.api.users.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String TOKEN_TYPE = "Bearer ";

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        var token = this.tokenService.createToken(
                request.getUsername(),
                request.getPassword()
        );

        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(AUTHORIZATION_HEADER, TOKEN_TYPE + token)
                .build();
    }
}
