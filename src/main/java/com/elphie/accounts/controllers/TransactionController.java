package com.elphie.accounts.controllers;

// =============================================================================
// File Name: controllers/TransactionController.java
// File Description:
// This file contains the code of the Transaction Controller that handles
// the Http requests, manipulates the data and returns the responses.
// =============================================================================

// =============================================================================
// Controller Imports
// =============================================================================
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elphie.accounts.definitions.TransactionRequest;
import com.elphie.accounts.libs.Utiles;
import com.elphie.accounts.models.Transaction;
import com.elphie.accounts.repositories.ITransactionRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// =============================================================================
// Controller Class
// =============================================================================
@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/accounts/transactions/")
public class TransactionController {

    // PROPERTIES ////////////////
    @Autowired
    private ITransactionRepository transactionRepository;

    // HTTP REQUEST METHODS ////////////////

    /**
     * Used to CREATE an Transaction and add it to the DB.
     * Strategy: Validate data coming from FE, Try add Transaction to DB, Catch errors if any.
     * Steps: 
     *    1 -> If validateTransaction has ERRORS return ERROR Response with 400 Bad Request Status with ERRORS Array
     *      ELSE
     *    2 -> Try add Transaction to DB
     *    3 -> If added to DB OK then return SUCCESS Response with 200 Ok status with Transaction Object
     *    4 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param transaction type Transaction from Request Body
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Transaction transaction) {

         // Get errors from validateTransaction method
         ArrayList<String> errors = Utiles.validateTransaction(transaction);

         // Return ERROR Response 400 Bad Request
         if(errors != null && errors.size() > 0) {
            return Utiles.generateResponse(
                HttpStatus.BAD_REQUEST, 
                HttpStatus.BAD_REQUEST.getReasonPhrase(), 
                errors
            );
        }

         try {
            // Set Created Date data in Transaction Object
            transaction.setCreatedOn(new Timestamp(System.currentTimeMillis()));
            
            // Add Transaction to DB
            Transaction createdTransaction = transactionRepository.save(transaction);

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK, 
                "Success creating Transaction.", 
                createdTransaction
            );

         } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                error.getMessage(), 
                "Failed to add Account to DB."
            );
         }
    }

    /**
     * Used to GET Transaction by ID from the DB.
     * Strategy: Validate data coming from FE, Try find Transaction in DB, Catch errors if any.
     * Steps: 
     *    1 -> Try find Transaction in DB
     *    2 -> If not found then return ERROR Response with 404 Not Found Error and Message.
     *    3 -> Else return SUCCESS Response with 200 Ok status with Transaction Object
     *    4 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param id type Long
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @GetMapping(value="/get", params={"id"})
    public ResponseEntity<Object> get(@RequestParam(name="id") Long id) {

        try {
            // Find transaction
            Optional<Transaction> transaction = transactionRepository.findById(id);

            // If transaction not found return 404 ERROR
            if(!transaction.isPresent()) {
                return Utiles.generateResponse(
                    HttpStatus.NOT_FOUND, 
                    HttpStatus.NOT_FOUND.getReasonPhrase(), 
                    "Transaction with id " + id + " not found."
                );
            }

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK, 
                "Success getting Transaction with id " + id, 
                transaction
            ); 

        } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,  
                error.getMessage(), 
                "Failed to get Transaction from DB."
            );
        }
    }


    /**
     * Used to GET all Transactions by User ID and filtered by Reference Like query from the DB.
     * Strategy: Validate data coming from FE, Try find All Transactions Matching in DB, Catch errors if any.
     * Steps: 
     *    1 -> Try find All Transactions Matching in DB
     *    2 -> If not found then return ERROR Response with 404 Not Found Error and Message.
     *    3 -> Else return SUCCESS Response with 200 Ok status with All Transactions Matching List
     *    4 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param userId type Long
     * @param query type String
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @GetMapping(value="/get", params={"userId", "query"})
    public ResponseEntity<Object> getFiltered(
        @RequestParam(name="userId") Long userId,
        @RequestParam(name="query") String query
    ) {
        try {
            // Find Transactions List
            List<Transaction> filteredTransactions = transactionRepository.findByUserIdAndReferenceContaining(userId, query);

            // If Transactions List is empty return 404 ERROR
            if(filteredTransactions.size() == 0) {
                return Utiles.generateResponse(
                    HttpStatus.NOT_FOUND, 
                    HttpStatus.NOT_FOUND.getReasonPhrase(), 
                    "There seams to not be any matches in the DB..."
                );
            }

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK,
                "Success getting Transactions.", 
                filteredTransactions
            ); 

        } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,  
                error.getMessage(), 
                "Failed to get Transactions from DB."
            );
        }
    }

    /**
     * Used to UPDATE Transaction by Id in the DB.
     * Strategy: Validate data coming from FE, Try update Transaction in DB, Catch Errors if any.
     * Steps: 
     *    1 -> If Request is NULL return ERROR Response with 400 Bad Request Status with message
     *    2 -> Try find Transaction by Id in DB
     *    3 -> If not found return ERROR Response 404 Not Found with Message
     *    4 -> Else Update Transaction in DB
     *    5 -> If updated in DB then return SUCCESS Response with 200 Ok status with Transaction Object
     *    6 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param id type Long from URL Params
     * @param requestBody type TransactionRequest
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> update(
        @PathVariable Long id, 
        @RequestBody TransactionRequest request
    ) {
        // Check request is not null
        if(request == null) {
            return Utiles.generateResponse(
                HttpStatus.BAD_REQUEST, 
                HttpStatus.BAD_REQUEST.getReasonPhrase(), 
                "Request cannot be NULL."
            );
        }

        try {
            // Find billing or Throw Exception
            Optional<Transaction> transaction = transactionRepository.findById(id);

            // If not found return 404 ERROR
            if(!transaction.isPresent()) {
                return Utiles.generateResponse(
                    HttpStatus.NOT_FOUND, 
                    HttpStatus.NOT_FOUND.getReasonPhrase(), 
                    "Transaction with id " + id + " not found."
                );
            }
            
            // Set new data
            transaction.get().setUserId(Long.parseLong(request.getUserId()));
            transaction.get().setAccountId(Long.parseLong(request.getAccountId()));
            transaction.get().setReference(request.getReference());
            transaction.get().setAmount(Double.parseDouble(request.getAmount()));

            // Parsing String date from request to SQL Date Type
            Date date = new SimpleDateFormat("yyy-MM-dd").parse(request.getDate());
            transaction.get().setDate(new java.sql.Date(date.getTime()));

            // Set new Updated On date
            transaction.get().setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            
            // Update Transaction
            Transaction updatedTransaction = transactionRepository.save(transaction.get());

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK, 
                "Success updating Account.", 
                updatedTransaction
            );

        } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,  
                error.getMessage(), 
                "Failed to update Account in DB."
            );
        }
        
    }

    /**
     * Used to DELETE an Transaction by Id from the DB.
     * Strategy: Validate data coming from FE, Try delete Transaction from DB, Catch Errors if any.
     * Steps: 
     *    1 -> If ID is NULL return ERROR Response with 400 Bad Request Status with message
     *    2 -> Try find Transaction in DB
     *    3 -> If not found then return ERROR Response 404 Not Found with Message.
     *    4 -> Else delete Transaction from DB
     *    5 -> If delete from DB Ok then return SUCCESS Response with 200 Ok status with message
     *    6 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param id type Long
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> delete(@PathVariable Long id) {

        // Validate new password is not null
        if(id == null) {
            return Utiles.generateResponse(
                HttpStatus.BAD_REQUEST, 
                HttpStatus.BAD_REQUEST.getReasonPhrase(), 
                "Transaction ID cannot be NULL."
            );
        }

        try {
            // Find Account
            Optional<Transaction> transaction = transactionRepository.findById(id);

            // If not found return 404 ERROR
            if(!transaction.isPresent()) {
                return Utiles.generateResponse(
                    HttpStatus.NOT_FOUND, 
                    HttpStatus.NOT_FOUND.getReasonPhrase(), 
                    "Transaction with id " + id + " not found."
                );
            }
        
            // Delete Transaction
            transactionRepository.delete(transaction.get());

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK, 
                "Success deleting the Transaction.", 
                null
            );

        } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,  
                error.getMessage(), 
                "Failed to delete Transaction from DB."
            );
        }
    }
}

