package com.elphie.accounts.libs;

import java.util.ArrayList;

// =============================================================================
// File Name: libs/Utiles.java
// File Description:
// This file contains handy methods to help increase development.
// =============================================================================

// =============================================================================
// Imports
// =============================================================================
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.elphie.accounts.models.Account;
import com.elphie.accounts.models.Transaction;


// =============================================================================
// Class
// =============================================================================
public class Utiles {

    /**
     * Used to generate custome JSON Http Responses
     * @param status type HttpStatus -> The Response status 200 OK | 400 Client Error | 404 Not Found | 500 Server Error
     * @param message type String -> The Response message
     * @param data type Object | null -> If Success add Data Object else set as null
     * @return 
     */
    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message, Object data) {

        // Create the Response Object as a Map
        Map<String, Object> responseMap = new HashMap<String, Object>();

        // Populate Response
        responseMap.put("timestamp", new Date());
        responseMap.put("status", status.value());
        responseMap.put("ok", status.value() == 200 ? true : false);
        responseMap.put("message", message);
        if(data != null) responseMap.put("data", data);

        // Return Response Object
        return new ResponseEntity<Object>(responseMap, status);
    }

    /**
     * Used to validate USer Object Data as per DB Policies.
     *    1 -> NOT NULL Data Policy for: email, password and account_type.
     *    2 -> NOT VALID Data Policy for: email and account_type.
     * @param user type User
     * @return errors type ArrayList<String>
     */
    public static ArrayList<String> validateAccount(Account account) {

        // Local variable errors
        ArrayList<String> errors = new ArrayList<>();

        // Validate NOT NULL Policy violations
        if(account.getUserId() == null) errors.add("User ID cannot be NULL.");
        if(account.getName() == null) errors.add("Account Name cannot be NULL.");
        if(account.getType() == null) errors.add("Account Type cannot be NULL.");
        if(account.getIban() == null) errors.add("Account IBAN cannot be NUL.");
        if(account.getBalance() == null) errors.add("Account Balance cannot be NULL.");

        // Return isValid value
        return errors;
    }

    public static ArrayList<String> validateTransaction(Transaction transaction) {

        // Local variable errors
        ArrayList<String> errors = new ArrayList<>();

        // Validate NOT NULL Policy violations
        if(transaction.getUserId() == null) errors.add("User ID cannot be NULL.");
        if(transaction.getAccountId() == null) errors.add("Account ID cannot be NULL.");
        if(transaction.getReference() == null) errors.add("Reference cannot be NULL.");
        if(transaction.getAmount() == null) errors.add("Amount cannot be NULL.");
        if(transaction.getDate() == null) errors.add("Date cannot be NULL.");
        if(transaction.getType() == null) errors.add("Type cannot be NULL.");

        // Return isValid value
        return errors;
    }
}

