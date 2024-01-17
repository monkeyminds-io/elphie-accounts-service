package com.elphie.accounts.definitions;

@Data
public class UpdateRequest {
    
    // PROPERTIES ////////////////
    private String userId;
    private String name;
    private String type;
    private String balance;
    private String currency;

    // GETTERS & SETTERS ////////////////
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getBalance() { return balance; }
    public void setBalance(String balance) { this.balance = balance; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
