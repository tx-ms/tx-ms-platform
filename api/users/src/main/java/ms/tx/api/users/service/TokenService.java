package ms.tx.api.users.service;


public interface TokenService {

    String createToken(String username, String password);

}
