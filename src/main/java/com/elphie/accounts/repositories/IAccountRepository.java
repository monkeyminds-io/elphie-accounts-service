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
import java.util.Optional;

// =============================================================================
// Interface
// =============================================================================
@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByUserId(Long userId);
    List<Account> findByUserIdAndNameContaining(Long userId, String query);
}
