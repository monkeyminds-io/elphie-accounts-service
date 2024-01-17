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


    // DEFAULT CONSTRUCTOR ////////////////
    public Transaction() {}

    // CONSTRUCTORS ////////////////
    // Create a constructor with all fields here...

    // GETTERS & SETTERS ////////////////
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
