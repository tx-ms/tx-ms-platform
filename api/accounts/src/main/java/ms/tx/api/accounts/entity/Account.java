package ms.tx.api.accounts.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(indexes = {
        @Index(name = "account_owner", columnList = "userId"),
        @Index(name = "account_name", columnList = "name"),
        @Index(name = "account_name_owner", columnList = "name,userId", unique = true)
})
public class Account {

    private String id;

    private String name;

    private String userId;

    private Set<AccountTransaction> transactions;

    private Long balance;

    public Account() {
        this.transactions = new HashSet<>();
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @OneToMany(mappedBy = "account")
    public Set<AccountTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<AccountTransaction> transactions) {
        this.transactions = transactions;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
