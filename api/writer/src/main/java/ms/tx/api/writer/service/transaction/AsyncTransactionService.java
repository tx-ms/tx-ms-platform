package ms.tx.api.writer.service.transaction;

import ms.tx.api.writer.messaging.MessageSender;
import ms.tx.api.writer.payload.transaction.TransactionRequest;
import ms.tx.api.writer.payload.transaction.TransactionState;
import ms.tx.api.writer.payload.transaction.TransactionCreation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AsyncTransactionService implements TransactionService {

    private static final long DENOMINATOR = 100;

    private final MessageSender messageSender;

    public AsyncTransactionService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public TransactionCreation create(String userId, TransactionRequest input) {
        var viewModel = new TransactionCreation(
                UUID.randomUUID().toString(),
                input.getAccountId(),
                userId,
                input.getAmount(),
                DENOMINATOR,
                input.getType(),
                TransactionState.PROCESSING
        );

        this.messageSender.send(viewModel);

        return viewModel;
    }
}
