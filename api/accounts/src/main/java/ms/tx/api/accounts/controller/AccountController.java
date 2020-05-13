package ms.tx.api.accounts.controller;

import ms.tx.api.accounts.config.Values;
import ms.tx.api.accounts.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getAccount(
            @RequestHeader(Values.USER_ID_HEADER) String userId,
            @PathVariable String id
    ) {
        var account = this.accountService.getAccountInfo(id, userId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(account);
    }
}
