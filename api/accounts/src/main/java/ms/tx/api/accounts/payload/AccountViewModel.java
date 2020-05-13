package ms.tx.api.accounts.payload;

public class AccountViewModel {

    private String id;

    private String name;

    private Long balance;

    public AccountViewModel(String id, String name, Long balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getBalance() {
        return balance;
    }
}
