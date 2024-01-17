package com.elphie.accounts.controllers;

// =============================================================================
// File Name: controllers/AccountController.java
// File Description:
// This file contains the code of the Account Controller that handles
// the Http requests, manipulates the data and returns the responses.
// =============================================================================

// =============================================================================
// Controller Imports
// =============================================================================
import java.sql.Timestamp;
import java.util.ArrayList;
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

import com.elphie.accounts.definitions.UpdateRequest;
import com.elphie.accounts.libs.Utiles;
import com.elphie.accounts.models.Account;
import com.elphie.accounts.repositories.IAccountRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// =============================================================================
// Controller Class
// =============================================================================
@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/accounts/")
public class AccountsController {

    // PROPERTIES ////////////////
    @Autowired
    private IAccountRepository accountRepository;

    // HTTP REQUEST METHODS ////////////////

    /**
     * Used to CREATE an Account and add it to the DB.
     * Strategy: Validate data coming from FE, Try add account to DB, Catch errors if any.
     * Steps: 
     *    1 -> If validateAccount has ERRORS return ERROR Response with 400 Bad Request Status with ERRORS Array
     *      ELSE
     *    3 -> Try add Account to DB
     *    4 -> If added to DB OK then return SUCCESS Response with 200 Ok status with Account Object
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param account type Account from Request Body
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Account account) {

         // Create validatedBillingInfo instance
         ArrayList<String> errors = Utiles.validateAccount(account);

         // Return ERROR Response 400 Bad Request
         if(errors != null && errors.size() > 0) {
            return Utiles.generateResponse(
                HttpStatus.BAD_REQUEST, 
                HttpStatus.BAD_REQUEST.getReasonPhrase(), 
                errors
            );
        }

         try {
            // Set Created Date data in Account Object
            account.setCreatedOn(new Timestamp(System.currentTimeMillis()));
            
            // Add Account to DB
            Account createdAccount = accountRepository.save(account);

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK, 
                "Success creating Account.", 
                createdAccount
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
     * Used to GET Account by ID from the DB.
     * Strategy: Validate data coming from FE, Try find Account in DB, Catch errors if any.
     * Steps: 
     *    1 -> Try find Account in DB
     *    2 -> If not found then return ERROR Response with 404 Not Found Error and Message.
     *    3 -> Else return SUCCESS Response with 200 Ok status with Account Object
     *    4 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param id type Long
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @GetMapping(value="/get", params={"id"})
    public ResponseEntity<Object> get(
        @RequestParam(name="id") Long id
    ) {
        try {
            // Find or Throw Exception
            Optional<Account> account = accountRepository.findById(id);

            // If account not found return 404 ERROR
            if(!account.isPresent()) {
                return Utiles.generateResponse(
                    HttpStatus.NOT_FOUND, 
                    HttpStatus.NOT_FOUND.getReasonPhrase(), 
                    "Account with id " + id + " not found."
                );
            }

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK, 
                "Success getting Account with id " + id, 
                account
            ); 

        } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,  
                error.getMessage(), 
                "Failed to get Account from DB."
            );
        }
    }


    /**
     * Used to GET all Accounts by User ID and filtered by Name Like query from the DB.
     * Strategy: Validate data coming from FE, Try find All Accounts Matching in DB, Catch errors if any.
     * Steps: 
     *    1 -> Try find All Accounts Matching in DB
     *    2 -> If not found then return ERROR Response with 404 Not Found Error and Message.
     *    3 -> Else return SUCCESS Response with 200 Ok status with All Accounts Matching List
     *    4 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param userId type Long
     * @param query type String
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @GetMapping(value="/get", params={"userId", "query"})
    public ResponseEntity<Object> get(
        @RequestParam(name="userId") Long userId,
        @RequestParam(name="query") String query
    ) {
        try {
            // Find Account List
            List<Account> filteredAccounts = accountRepository.findByUserIdAndNameContaining(userId, query);

            // If Accounts List is empty return 404 ERROR
            if(filteredAccounts.size() == 0) {
                return Utiles.generateResponse(
                    HttpStatus.NOT_FOUND, 
                    HttpStatus.NOT_FOUND.getReasonPhrase(), 
                    "There seams to not be any matches in the DB..."
                );
            }

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK,
                "Success getting Accounts.", 
                filteredAccounts
            ); 

        } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,  
                error.getMessage(), 
                "Failed to get Accounts from DB."
            );
        }
    }

    /**
     * Used to UPDATE the Account by Id in the DB.
     * Strategy: Validate data coming from FE, Try update Account in DB, Catch Errors if any.
     * Steps: 
     *    1 -> If Request is NULL return ERROR Response with 400 Bad Request Status with message
     *    2 -> Try find Account by Id in DB
     *    3 -> Try update Billing in DB
     *    4 -> If updated in DB then return SUCCESS Response with 200 Ok status with Billing Object
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param id type Long from URL Params
     * @param requestBody type UpdatePasswordBody from the request body
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateRequest request) {
        
        // Validate new password is not null
        if(request == null) {
            return Utiles.generateResponse(
                HttpStatus.BAD_REQUEST, 
                HttpStatus.BAD_REQUEST.getReasonPhrase(), 
                "Request cannot be NULL."
            );
        }

        try {
            // Find billing or Throw Exception
            Optional<Account> account = accountRepository.findById(id);

            // If account not found return 404 ERROR
            if(!account.isPresent()) {
                return Utiles.generateResponse(
                    HttpStatus.NOT_FOUND, 
                    HttpStatus.NOT_FOUND.getReasonPhrase(), 
                    "Account with id " + id + " not found."
                );
            }
            
            // Set new Updated On date
            account.get().setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            
            // Save user with new data
            Account updatedAccount = accountRepository.save(account.get());

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK, 
                "Success updating Account.", 
                updatedAccount
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
     * Used to DELETE an Account by Id from the DB.
     * Strategy: Validate data coming from FE, Try delete Account from DB, Catch Errors if any.
     * Steps: 
     *    1 -> If ID is NULL return ERROR Response with 400 Bad Request Status with message
     *    2 -> Try find Account in DB
     *    3 -> If not found then return ERROR Response 404 Not Found with Message.
     *    4 -> Else delete Account from DB
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
                "Account ID cannot be NULL."
            );
        }

        try {
            // Find Account
            Optional<Account> account = accountRepository.findById(id);

            // If account not found return 404 ERROR
            if(!account.isPresent()) {
                return Utiles.generateResponse(
                    HttpStatus.NOT_FOUND, 
                    HttpStatus.NOT_FOUND.getReasonPhrase(), 
                    "Account with id " + id + " not found."
                );
            }
        
            // Delete Account
            accountRepository.delete(account.get());

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK, 
                "Success deleting the Account.", 
                null
            );

        } catch (Exception error) {
            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,  
                error.getMessage(), 
                "Failed to delete Account from DB."
            );
        }
    }
}
