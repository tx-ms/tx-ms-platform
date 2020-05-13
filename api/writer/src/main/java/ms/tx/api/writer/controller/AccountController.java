package ms.tx.api.writer.controller;

import ms.tx.api.writer.config.Values;
import ms.tx.api.writer.payload.account.AccountCreation;
import ms.tx.api.writer.payload.account.AccountRequest;
import ms.tx.api.writer.service.account.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountCreation> create(
            @RequestHeader(Values.USER_ID_HEADER) String userId,
            @RequestBody AccountRequest request
    ) {
        return new ResponseEntity<>(this.accountService.create(userId, request), HttpStatus.CREATED);
    }
}
