package ms.tx.api.users.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ms.tx.api.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final String tokenSecret;

    public TokenServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${token.secret}") String tokenSecret
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenSecret = tokenSecret;
    }

    @Override
    public String createToken(String username, String password) {
        var user = this.userRepository.findFirstByUsername(username);
        if (user == null) {
            return null;
        }

        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        return JWT.create()
                .withSubject(user.getId())
                .sign(Algorithm.HMAC256(this.tokenSecret));
    }
}
