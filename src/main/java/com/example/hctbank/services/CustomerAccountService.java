package com.example.hctbank.services;

import com.example.hctbank.entities.Account;
import com.example.hctbank.entities.Customer;
import com.example.hctbank.entities.CustomerAccountMap;
import com.example.hctbank.exceptions.InvalidException;
import com.example.hctbank.repositories.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerAccountService {
    @Autowired
    CustomerAccountRepository customerAccountRepository;

    void mapCustomerToAccount(Customer customer, Account account){
        CustomerAccountMap customerAccountMap = new CustomerAccountMap();
        customerAccountMap.setCustomer(customer);
        customerAccountMap.setAccount(account);
        customerAccountRepository.save(customerAccountMap);
    }

    CustomerAccountMap getCustomerAndAccount(Long id){
        return customerAccountRepository.findByCustomer_Id(id).orElseThrow(InvalidException::new);
    }

    CustomerAccountMap getCustomerAndAccountFromAccount(Long id){
        return customerAccountRepository.findByAccount_Id(id).orElseThrow(InvalidException::new);
    }
}
