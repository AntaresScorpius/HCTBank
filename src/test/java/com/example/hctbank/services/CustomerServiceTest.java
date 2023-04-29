//package com.example.hctbank.services;
//
//import com.example.hctbank.dto.UserRegisterDTO;
//import com.example.hctbank.entities.Account;
//import com.example.hctbank.entities.Customer;
//import com.example.hctbank.entities.embeddable.Address;
//import com.example.hctbank.repositories.AccountRepository;
//import com.example.hctbank.repositories.CustomerRepository;
//import com.example.hctbank.utils.ErrorMessageAndCode;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class CustomerServiceTest {
//
//
//    @InjectMocks
//    private CustomerService customerService;
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    @Mock
//    private AccountService accountService;
//
//    @Test
//    public void checkBalanceOfNewlyCreatedUser() throws ErrorMessageAndCode {
//        Account account = new Account(1L,500);
//        Mockito.when(accountService.getAccount(1L)).thenReturn(account);
//        Account account1 = customerService.getBalance(null,1L);
//        assertEquals(500,account1.getBalance());
//    }
//
////    @Test
////    void checkIfRegisteredUserIsSavedAndReturnsDTO() {
////        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder().customer_id(1L).name("John").balance(500D).account_id(1L).build();
////        Address address= Address.builder().country("India").AddressLane("Hi-tech").pincode(500081).city("Hyder").id(1).build();
////        Customer customer = Customer.builder().id(1L).name("John").address(address).phone(1234).email("rohit").build();
////        Mockito.when(customerService.registerCustomer(customer)).thenReturn(userRegisterDTO);
////        UserRegisterDTO userRegisterDTO1 = customerService.registerCustomer(customer);
////        assertNotNull(userRegisterDTO1);
////        assertEquals(customer.getId(), userRegisterDTO1.getCustomer_id());
////    }
//}