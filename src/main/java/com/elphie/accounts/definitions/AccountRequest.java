package com.elphie.accounts.definitions;

@Data
public class AccountRequest {
    
    // PROPERTIES ////////////////
    private String name;
    private String type;
    private String iban;
    private String balance;

    // GETTERS & SETTERS ////////////////
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getIban() { return this.iban; }
    public void setIban(String iban) { this.iban = iban; }
    public String getBalance() { return balance; }
    public void setBalance(String balance) { this.balance = balance; }
}
