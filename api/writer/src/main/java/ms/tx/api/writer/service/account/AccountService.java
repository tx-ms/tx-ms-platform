package ms.tx.api.writer.service.account;

import ms.tx.api.writer.payload.account.AccountCreation;
import ms.tx.api.writer.payload.account.AccountRequest;

public interface AccountService {

    AccountCreation create(String userId, AccountRequest input);

}
