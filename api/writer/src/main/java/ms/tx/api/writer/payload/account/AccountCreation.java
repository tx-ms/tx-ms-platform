package ms.tx.api.writer.payload.account;

import ms.tx.api.writer.messaging.type.PayloadEventType;

public class AccountCreation implements PayloadEventType {

    private String id;

    private String userId;

    private AccountState state;

    private String name;

    public AccountCreation(String id, String userId, AccountState state, String name) {
        this.id = id;
        this.userId = userId;
        this.state = state;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public AccountState getState() {
        return state;
    }

    public String getName() {
        return name;
    }
}
