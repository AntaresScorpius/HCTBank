package com.example.hctbank.services;

import com.example.hctbank.dto.*;
import com.example.hctbank.entities.Account;
import com.example.hctbank.entities.Customer;
import com.example.hctbank.entities.CustomerAccountMap;
import com.example.hctbank.entities.Transaction;
import com.example.hctbank.entities.embeddable.Address;
import com.example.hctbank.exceptions.FailedTransactionException;
import com.example.hctbank.exceptions.InvalidException;
import com.example.hctbank.repositories.CustomerAccountRepository;
import com.example.hctbank.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final AccountService accountService;
    private final CustomerAccountService customerAccountService;
    private final TransactionService transactionService;
    private final CustomerAccountRepository customerAccountRepository;
    private final CustomerRepository customerRepository;
    private final SendSMS sendSMS;
    private final WebClient webClient;

    public CustomerService(CustomerRepository customerRepository, CustomerAccountRepository customerAccountRepository,
                            TransactionService transactionService,CustomerAccountService customerAccountService,
                           AccountService accountService, SendSMS sendSMS, WebClient webClient
        ){
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.customerAccountService = customerAccountService;
        this.customerAccountRepository = customerAccountRepository;
        this.customerRepository = customerRepository;
        this.sendSMS = sendSMS;
        this.webClient = webClient;
    }

    @Transactional
    public UserRegisterDTO registerCustomer(Customer customer){
        Account account = accountService.createAccount(new Account());
        Customer customer1 =  customerRepository.save(customer);
        System.out.println("Saved Account is " + account);
        System.out.println("saved customer is: "  +customer1);
        customerAccountService.mapCustomerToAccount(customer1, account);
        return UserRegisterDTO.builder()
                .name(customer1.getName())
                .customer_id(customer.getId())
                .account_id(account.getId())
                .balance(account.getBalance())
                .build();
    }

    public UserRegisterDTO registerCustomers(CustomerRequestCreateDTO customerRequestCreateDTO){

        Account account = accountService.createAccount(new Account());
        Address address = Address.builder().AddressLane(customerRequestCreateDTO.getAddressLane())
                .pincode(customerRequestCreateDTO.getPincode())
                .country(customerRequestCreateDTO.getCountry())
                .city(customerRequestCreateDTO.getCity())
                .build();

        Customer customer1 =  Customer.builder().name(customerRequestCreateDTO.getName())
                .phone(customerRequestCreateDTO.getPhone())
                .email(customerRequestCreateDTO.getEmail())
                        .address(address).build();

        Customer customer = customerRepository.save(customer1);


        System.out.println("Saved Account is " + account);
        System.out.println("saved customer is: "  +customer);
        customerAccountService.mapCustomerToAccount(customer, account);
        String sms = webClient.get().
                 uri(uriBuilder ->
                         uriBuilder.port(8081).queryParam("phone",customer1.getPhone())
                                 .queryParam("accId",account.getId())
                                 .build())
                 .retrieve().bodyToMono(String.class).block();
        System.out.println("Response from SMS service: "+sms);

        //TODO :   Delete below classes and related services.
        sendSMS.sendSms(customer1.getPhone(), account.getId());
        //
        return UserRegisterDTO.builder()
                .name(customer.getName())
                .customer_id(customer.getId())
                .account_id(account.getId())
                .balance(account.getBalance())
                .build();
    }

    public List<FetchUserDTO>  getUsers(Long customer_id)  {
        Customer customer =  customerRepository.findById(customer_id).orElseThrow(() -> new InvalidException("Provided input's {query params or path params} is/are Invalid!.","HCTB404"));
        CustomerAccountMap customerAccountMap = customerAccountService.getCustomerAndAccount(customer_id);
        System.out.println("Get users Customer" + customer);
        System.out.println("Get users CustomerAccountMap" + customerAccountMap);
        List<FetchUserDTO> list3 = new ArrayList<>();

        list3.add(FetchUserDTO.builder()
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .account_id(customerAccountMap.getAccount().getId())
                .customer_id(customer_id)
                .created_on(customer.getCreatedOn()).build());

        return list3;
    }

    public List<FetchUserDTO> getAllUsers() {
        List<Customer> list =  customerRepository.findAll();
        List<CustomerAccountMap> list2 = customerAccountRepository.findAll();
        List<FetchUserDTO> list3 = new ArrayList<>();

        for (int i = 0 ; i < list.size(); i++){
            Customer customer =  list.get(i);
            CustomerAccountMap customerAccountMap = list2.get(i);
            list3.add(FetchUserDTO.builder()
                    .name(customer.getName())
                    .phone(customer.getPhone())
                    .email(customer.getEmail())
                    .account_id(customerAccountMap.getAccount().getId())
                    .customer_id(customer.getId())
                    .created_on(customer.getCreatedOn()).build());
        }
        return list3;
    }

    public Account getBalance(Long customer_id, Long account_id)  {
        if(account_id != null){
            System.out.println("account ID found");
            if (customer_id != null){
                CustomerAccountMap customerAccountMap = customerAccountService.getCustomerAndAccount(customer_id);
                if (!customerAccountMap.getAccount().getId().equals(account_id)){
                    throw new InvalidException("Customer and Account IDs Mismatch", "HCB404");
                }
            }
            return accountService.getAccount(account_id);
        } else{
            CustomerAccountMap customerAccountMap = customerAccountService.getCustomerAndAccount(customer_id);
            return accountService.accountRepository.findById(customerAccountMap.getAccount().getId()).orElseThrow(InvalidException::new);
        }
    }

    public TransactionResponseDTO makeTransaction(TransactionDTO transactionDTO) {
        Account from = accountService.accountRepository.findById(transactionDTO.getAcc_id()).orElseThrow(FailedTransactionException::new);
        Account to = accountService.accountRepository.findById(transactionDTO.getTo_acc_id()).orElseThrow(FailedTransactionException::new);
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
        if(transactionDTO.getType().equals("CREDIT")){
            if(from.getBalance() >= transactionDTO.getAmount()){
                System.out.println("Enough balance to transfer");
                from.setBalance(from.getBalance() - transactionDTO.getAmount());
                to.setBalance(to.getBalance() + transactionDTO.getAmount());
                accountService.accountRepository.save(from);
                accountService.accountRepository.save(to);
                Long ref_id = transactionService.saveTransaction(from,to,transactionDTO.getAmount());
                CustomerAccountMap customerAccountMap = customerAccountService.getCustomerAndAccountFromAccount(from.getId());
                Customer customer = customerAccountMap.getCustomer();
                sendSMS.sendTransactionSms(customer.getPhone(),ref_id,from.getId(), to.getId(), transactionDTO.getAmount(), customerAccountMap.getAccount().getBalance());
                transactionResponseDTO.setTransaction_ref_id(ref_id);
                transactionResponseDTO.setStatus_code("HCT200");
                transactionResponseDTO.setMessage("Transaction Successful");
            }else{
                transactionResponseDTO.setStatus_code("HCT400");
                transactionResponseDTO.setMessage("Transaction Failed");
            }
        }
        return transactionResponseDTO;
    }

    public List<TransactionPdfDTO> generatePdf(Long acc_id) {
        List<TransactionPdfDTO> list3 = new ArrayList<>();
        List<Transaction> list =  transactionService.getTransaction(acc_id, null);
        for (Transaction t : list){
            TransactionPdfDTO transactionPdfDTO = TransactionPdfDTO.builder().
                    date(t.getLastUpdated())
                    .Description("CREDIT")
                    .balance(t.getAvlBalance())
                    .in(t.getCredit())
                    .out(t.getDebit())
                    .build();
            list3.add(transactionPdfDTO);
        }

        return list3;

    }
}
