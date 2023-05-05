package com.example.hctbank.controllers;

import com.example.hctbank.dto.*;
import com.example.hctbank.entities.Customer;
import com.example.hctbank.exceptions.ParamRequiredException;
import com.example.hctbank.services.CustomerService;
import com.example.hctbank.services.TransactionService;
import com.example.hctbank.utils.GeneratePDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@RestController
@RequestMapping("/hctbank")
public class BankController {

    private static final Logger logger = LoggerFactory.getLogger(BankController.class);
    CustomerService customerService;
    TransactionService transactionService;

    @Autowired
    SpringTemplateEngine templateEngine;

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
    public ModelAndView getUsers(@RequestParam(required = false)String id, Model model1){
        System.out.println("Get users: ID : " + id);
        logger.info("Get Mapping Customers {}", id);
        if(id == null){
            logger.info("null id");
            List<FetchUserDTO> customers = customerService.getAllUsers();
            ModelAndView model = new ModelAndView();
            model.setViewName("Allcustomers");
            model.addObject("customers", customers);
            Context context = new Context();
            context.setVariable("customers", customers);
            String html = templateEngine.process("Allcustomers", context);
            GeneratePDF.generatePdfFromHtml(html);
            return model;
        }else {
            logger.info("Specific user");
            return null;
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

    @GetMapping("transactionspdf")
    public ModelAndView getTransactionsPdf(@RequestParam(required = false) Long acc_id, @RequestParam(required = false) Long tran_refId){
        logger.info("generating transaction PDF");
        List<TransactionPdfDTO> transactions = customerService.generatePdf(acc_id);
        ModelAndView model = new ModelAndView();
        model.setViewName("customer_transaction");
        model.addObject("transactions", transactions);
        Context context = new Context();
        context.setVariable("transactions", transactions);
        String html = templateEngine.process("customer_transaction", context);
        GeneratePDF.generatePdfFromHtml(html);
        return model;
    }
}
