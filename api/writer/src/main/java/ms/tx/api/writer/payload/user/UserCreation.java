package ms.tx.api.writer.payload.user;

import ms.tx.api.writer.messaging.type.PayloadEventType;

public class UserCreation implements PayloadEventType {

    private String id;

    private String user;

    private String passwordHash;

    private UserState state;

    public UserCreation(String id, String user, String passwordHash, UserState state) {
        this.id = id;
        this.user = user;
        this.passwordHash = passwordHash;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }
}
