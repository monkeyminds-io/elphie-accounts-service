package com.elphie.accounts.repositories;

import java.util.List;

// =============================================================================
// File Name: reposoitories/ITransactionRepository.java
// File Description:
// This file contains the code of the ITransactionRepository Interface that
// handles the queries to the users table in the DB
// =============================================================================

// =============================================================================
// Imports
// =============================================================================
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elphie.accounts.models.Transaction;

// =============================================================================
// Interface
// =============================================================================
@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByUserIdAndReferenceContaining(Long userId, String query);
}
