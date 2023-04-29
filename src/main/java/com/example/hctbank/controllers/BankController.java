package com.example.hctbank.controllers;

import com.example.hctbank.dto.*;
import com.example.hctbank.entities.Customer;
import com.example.hctbank.exceptions.ParamRequiredException;
import com.example.hctbank.services.CustomerService;
import com.example.hctbank.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/hctbank")
public class BankController {

    private static final Logger logger = LoggerFactory.getLogger(BankController.class);
    CustomerService customerService;
    TransactionService transactionService;

    public BankController(CustomerService customerService, TransactionService transactionService){
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @PostMapping("/customer")
    public UserRegisterDTO createUser(@RequestBody Customer customer){
        logger.info("Starting Customer Registration " +  customer.getName());
        System.out.println("Register User: " +customer);
        return customerService.registerCustomer(customer);
    }

    @PostMapping("/customers")
    public UserRegisterDTO createUsers(@RequestBody CustomerRequestCreateDTO customerRequestCreateDTO){
        logger.info("Starting Customer Registration " +  customerRequestCreateDTO.getName());
        System.out.println("Register User: " +customerRequestCreateDTO);
        return customerService.registerCustomers(customerRequestCreateDTO);
    }

    @GetMapping("/customers")
    public ResponseEntity<Object> getUsers(@RequestParam(required = false)String id){
        System.out.println("Get users: ID : " + id);
        logger.info("Get Mapping Customers {}", id);
        if(id == null){
            logger.info("null id");
            return new ResponseEntity<>(customerService.getAllUsers(), HttpStatus.OK);
        }else {
            logger.info("Specific user");
            return new ResponseEntity<>(customerService.getUsers(Long.parseLong(id)) , HttpStatus.OK);
        }
    }

    @GetMapping("/balances")
    public ResponseEntity<Object> getBalance(@RequestParam(required = false) Long customer, @RequestParam(required = false) Long account){
        logger.info("Customer " + customer + " Account " +account);
        logger.info("Getting Balance of Customer: " + customer);
        if(customer == null && account == null){
            logger.warn("GET Balance: Both Customer and account Id is null");
            throw new ParamRequiredException();
        }
        return new ResponseEntity<>(customerService.getBalance(customer, account) , HttpStatus.OK);
    }

    @PostMapping("transactions")
    public ResponseEntity<Object> makeTransaction(@RequestBody TransactionDTO transactionDTO){
        logger.info("makeTransaction(): from -- to " + transactionDTO.getAcc_id() + " -- " +transactionDTO.getTo_acc_id());
        TransactionResponseDTO success = customerService.makeTransaction(transactionDTO);
        logger.info("makeTransaction(): Success " + success.getTransaction_ref_id());
        return new ResponseEntity<>(success, success.getTransaction_ref_id() != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("transactions")
    public ResponseEntity<?> getTransactions(@RequestParam(required = false) Long acc_id, @RequestParam(required = false) Long tran_refId){
        logger.info("get Transaction: " + acc_id + " " + tran_refId);
        logger.info("getTransaction(): accID -- tran_refId " + acc_id + " -- " + tran_refId);
        return new ResponseEntity<>(transactionService.getTransaction(acc_id, tran_refId), HttpStatus.OK);
    }
}
