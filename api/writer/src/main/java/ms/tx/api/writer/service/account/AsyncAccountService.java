package ms.tx.api.writer.service.account;

import ms.tx.api.writer.messaging.MessageSender;
import ms.tx.api.writer.payload.account.AccountCreation;
import ms.tx.api.writer.payload.account.AccountRequest;
import ms.tx.api.writer.payload.account.AccountState;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AsyncAccountService implements AccountService {

    private final MessageSender messageSender;

    public AsyncAccountService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public AccountCreation create(String userId, AccountRequest input) {
         var viewModel = new AccountCreation(
                 UUID.randomUUID().toString(),
                 userId,
                 AccountState.WAITING,
                 input.getName()
         );

         this.messageSender.send(viewModel);

         return viewModel;
    }
}
