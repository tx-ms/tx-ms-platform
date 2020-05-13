package ms.tx.api.users.payload;


import ms.tx.api.users.messaging.type.PayloadEventType;

public class UserCreation implements PayloadEventType {

    private String id;

    private String user;

    private String passwordHash;

    private UserState state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }
}
