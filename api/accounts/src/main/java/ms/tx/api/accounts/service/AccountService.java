package ms.tx.api.accounts.service;

import ms.tx.api.accounts.payload.AccountViewModel;

public interface AccountService {

    AccountViewModel getAccountInfo(String accountId, String userId);
}
