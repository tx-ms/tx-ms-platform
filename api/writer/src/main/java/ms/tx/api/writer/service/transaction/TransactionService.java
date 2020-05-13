package ms.tx.api.writer.service.transaction;

import ms.tx.api.writer.payload.transaction.TransactionRequest;
import ms.tx.api.writer.payload.transaction.TransactionCreation;

public interface TransactionService {

    TransactionCreation create(String userId, TransactionRequest input);

}
