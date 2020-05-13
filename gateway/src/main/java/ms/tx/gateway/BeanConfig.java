package ms.tx.gateway;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    private final String tokenSecret;

    public BeanConfig(@Value("${token.secret}") String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }


    @Bean
    public JWTVerifier jwtVerifier() {
        var algorithm = Algorithm.HMAC256(this.tokenSecret);

        return JWT.require(algorithm).build();
    }
}
