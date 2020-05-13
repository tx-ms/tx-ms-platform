package ms.tx.api.accounts.payload.account;


import ms.tx.api.accounts.messaging.type.PayloadEventType;

public class AccountCreation implements PayloadEventType {

    private String id;

    private String userId;

    private AccountState state;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AccountState getState() {
        return state;
    }

    public void setState(AccountState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
