package com.example.hctbank.repositories;

import com.example.hctbank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<List<Transaction>> findAllByAccID(Long id);

    Optional<List<Transaction>> findAllByTransactionRefId(Long id);

    Optional<List<Transaction>> findAllByAccIDAndTransactionRefId(Long acc_id, Long transactionRefId);
}
