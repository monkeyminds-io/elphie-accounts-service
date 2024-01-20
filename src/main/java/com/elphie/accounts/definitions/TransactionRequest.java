package com.elphie.accounts.definitions;

@Data
public class TransactionRequest {
    
    // PROPERTIES ////////////////
    private String accountId;
    private String reference;
    private String amount;
    private String date;
    private String type;

    // GETTERS & SETTERS ////////////////
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
    public String getReference() { return this.reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getAmount() { return this.amount; }
    public void setAmount(String amount) { this.amount = amount; }
    public String getDate() { return this.date; }
    public void setDate(String date) { this.date = date; }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

}
