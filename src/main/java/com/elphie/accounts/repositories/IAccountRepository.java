package com.elphie.accounts.repositories;

// =============================================================================
// File Name: reposoitories/IAccountRepository.java
// File Description:
// This file contains the code of the IAccountRepository Interface that
// handles the queries to the users table in the DB
// =============================================================================

// =============================================================================
// Imports
// =============================================================================
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elphie.accounts.models.Account;

import java.util.List;

// =============================================================================
// Interface
// =============================================================================
@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    
    List<Account> findByUserIdAndNameContaining(Long userId, String query);
}
