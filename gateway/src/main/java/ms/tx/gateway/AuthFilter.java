package ms.tx.gateway;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class AuthFilter extends ZuulFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String USER_ID_HEADER = "X-User-Id";

    private static final String TOKEN_TYPE = "Bearer ";

    private final JWTVerifier jwtVerifier;

    public AuthFilter(JWTVerifier jwtVerifier) {
        this.jwtVerifier = jwtVerifier;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        var ctx = RequestContext.getCurrentContext();
        var request = ctx.getRequest();
        return !request.getRequestURI().startsWith("/users/token") &&
                !request.getRequestURI().startsWith("/w/users");
    }

    @Override
    public Object run() {
        var ctx = RequestContext.getCurrentContext();
        var request = ctx.getRequest();

        var authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader == null || authHeader.isBlank()) {
            ctx.unset();
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        var token = authHeader.replace(TOKEN_TYPE, "");

        try {
            var jwt = this.jwtVerifier.verify(token);
            var userId = jwt.getSubject();
            ctx.set(USER_ID_HEADER, userId);
            ctx.addZuulRequestHeader(USER_ID_HEADER, userId);
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            ctx.unset();
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return null;
    }
}
