package com.example.hctbank.services;

import com.example.hctbank.entities.Account;
import com.example.hctbank.entities.CustomerAccountMap;
import com.example.hctbank.entities.Transaction;
import com.example.hctbank.exceptions.InvalidException;
import com.example.hctbank.exceptions.ParamRequiredException;
import com.example.hctbank.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final SendSMS sendSMS;

    public TransactionService(TransactionRepository transactionRepository, SendSMS sendSMS) {
        this.transactionRepository = transactionRepository;
        this.sendSMS = sendSMS;
    }

    public Long saveTransaction(Account from, Account to, double amount) {
        Long refId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;


        Transaction transaction1 = Transaction.builder()
                .transactionRefId(refId)
                .debit(amount)
                .credit(0.0)
                .avlBalance(from.getBalance())
                .accID(from.getId()).build();


        Transaction transaction2 = Transaction.builder()
                .transactionRefId(refId)
                .debit(0.0)
                .credit(amount)
                .avlBalance(to.getBalance())
                .accID(to.getId()).build();


        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        return refId;
    }

    public List<Transaction> getTransaction(Long accId, Long tranRefId)  {
        if(accId != null && tranRefId != null){
            return transactionRepository.findAllByAccIDAndTransactionRefId(accId,tranRefId).orElseThrow(InvalidException::new);
        }else if(accId != null){
            List<Transaction> list =  transactionRepository.findAllByAccID(accId).orElseThrow(InvalidException::new);
            if(list.size() == 0 ){
                throw new InvalidException();
            }else {
                return list;
            }
        }else if(tranRefId != null){
            List<Transaction> list =  transactionRepository.findAllByTransactionRefId(tranRefId).orElseThrow(InvalidException::new);
            if(list.size() == 0 ){
                throw new InvalidException();
            }else {
                return list;
            }
        }else{
            throw new ParamRequiredException();
        }
    }
}
