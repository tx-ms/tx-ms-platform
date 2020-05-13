package ms.tx.api.writer.payload.transaction;

import ms.tx.api.writer.messaging.type.PayloadEventType;

public class TransactionCreation implements PayloadEventType {

    private String id;

    private String accountId;

    private String userId;

    private Long amount;

    private Long denominator;

    private TransactionType type;

    private TransactionState state;

    public TransactionCreation(
            String id,
            String accountId,
            String userId,
            Long amount,
            Long denominator,
            TransactionType type,
            TransactionState state
    ) {
        this.id = id;
        this.accountId = accountId;
        this.userId = userId;
        this.amount = amount;
        this.denominator = denominator;
        this.type = type;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getDenominator() {
        return denominator;
    }

    public void setDenominator(Long denominator) {
        this.denominator = denominator;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }
}
