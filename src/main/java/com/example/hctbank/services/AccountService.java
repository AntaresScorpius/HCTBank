package com.example.hctbank.services;

import com.example.hctbank.entities.Account;
import com.example.hctbank.exceptions.InvalidException;
import com.example.hctbank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    Account createAccount(Account account){
        Account newAccount =  accountRepository.save(account);
        return newAccount;
    }

    Account getAccount(Long id){
        return accountRepository.findById(id).orElseThrow(InvalidException::new);
    }
}
