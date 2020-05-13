package ms.tx.api.writer.controller;

import ms.tx.api.writer.config.Values;
import ms.tx.api.writer.payload.transaction.TransactionRequest;
import ms.tx.api.writer.payload.transaction.TransactionCreation;
import ms.tx.api.writer.service.transaction.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionCreation> create(
            @RequestHeader(Values.USER_ID_HEADER) String userId,
            @RequestBody TransactionRequest request) {
        return new ResponseEntity<>(this.transactionService.create(userId, request), HttpStatus.CREATED);
    }
}
