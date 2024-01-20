package com.elphie.accounts.repositories;

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
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elphie.accounts.models.Transaction;

import java.util.Date;
import java.util.List;

// =============================================================================
// Interface
// =============================================================================
@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByUserIdAndReferenceContaining(Long userId, String query);
    List<Transaction> findByUserIdAndDateBetween(Long userId, Date start, Date end);

    // TODO Couldn't get this custom query to work... 😞
    @Query(value="SELECT SUM(amount), date FROM Transactions t WHERE user_id = ?1 AND date BETWEEN ?2 AND ?3 GROUP BY date ORDER BY date ASC", nativeQuery = true)
    List<Transaction> findTransactionsByUserIdBetweenDates(Long userId, Date start, Date end);

}
