//package com.example.hctbank.services;
//
//import com.example.hctbank.dto.UserRegisterDTO;
//import com.example.hctbank.entities.Customer;
//import com.example.hctbank.entities.embeddable.Address;
//import com.example.hctbank.repositories.AccountRepository;
//import com.example.hctbank.repositories.CustomerRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class AccountServiceTest {
//
//    @Autowired
//    AccountService accountService;
//    @Autowired
//    CustomerRepository customerRepository;
//    @Autowired
//    CustomerService customerService;
//
//    @Mock
//    AccountRepository accountRepository;
//
//    @Test
//    @DisplayName("New Account with 500 balance")
//    void CheckBalanceIs500WhenCustomerIsCreatedAndAccountIdIsGiven(){
//
//        Address address= Address.builder().country("India").AddressLane("Hi-tech").pincode(500081).city("Hyder").id(1).build();
//        Customer customer = Customer.builder().id(1L).name("John").address(address).phone(1234).email("rohit").build();
//        UserRegisterDTO userRegisterDTO = customerService.registerCustomer(customer);
//        assertEquals(userRegisterDTO.getBalance(), 500);
//    }
//}
