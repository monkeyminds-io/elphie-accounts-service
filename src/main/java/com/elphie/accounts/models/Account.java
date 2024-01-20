package com.elphie.accounts.models;

// =============================================================================
// File Name: model/Account.java
// File Description:
// This file contains the Account Entity Class
// =============================================================================

// =============================================================================
// Entity Imports
// =============================================================================
import java.sql.Timestamp;

import jakarta.persistence.*;

// =============================================================================
// Entity Class
// =============================================================================
@Entity
@Table(name = "accounts")
public class Account {

    // PROPERTIES ////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "iban")
    private String iban;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "plaidId")
    private String plaidId;

    @Column(name = "createdOn")
    private Timestamp createdOn;

    @Column(name = "updatedOn")
    private Timestamp updatedOn;


    // DEFAULT CONSTRUCTOR ////////////////
    public Account() {}

    // CONSTRUCTORS ////////////////

    public Account(Long userId, String name, String type, String iban, Double balance) {
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.iban = iban;
        this.balance = balance;
    }


    // GETTERS & SETTERS ////////////////
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPlaidId() {
        return plaidId;
    }

    public void setPlaidId(String plaidId) {
        this.plaidId = plaidId;
    }

    public Timestamp getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return this.updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }


}
