package com.elphie.accounts.models;

// =============================================================================
// File Name: model/Transaction.java
// File Description:
// This file contains the Transaction Entity Class
// =============================================================================

// =============================================================================
// Entity Imports
// =============================================================================
import java.sql.Timestamp;
import java.sql.Date;

import jakarta.persistence.*;

// =============================================================================
// Entity Class
// =============================================================================
@Entity
@Table(name = "transactions")
public class Transaction {

    // PROPERTIES ////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "accountId")
    private Long accountId;

    @Column(name = "reference")
    private String reference;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private Date date;

    @Column(name = "type")
    private String type;

    @Column(name = "createdOn")
    private Timestamp createdOn;

    @Column(name = "updatedOn")
    private Timestamp updatedOn;

    // DEFAULT CONSTRUCTOR ////////////////
    public Transaction() {}

    // CONSTRUCTORS ////////////////

    public Transaction(Long userId, Long accountId, String reference, Double amount, Date date) {
        this.userId = userId;
        this.accountId = accountId;
        this.reference = reference;
        this.amount = amount;
        this.date = date;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }
}
