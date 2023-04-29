package com.example.hctbank.repositories;

import com.example.hctbank.entities.CustomerAccountMap;
import com.example.hctbank.exceptions.InvalidException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccountMap, Long> {

    Optional<CustomerAccountMap> findByCustomer_Id(Long id);

    Optional<CustomerAccountMap> findByAccount_Id(Long id);

}
