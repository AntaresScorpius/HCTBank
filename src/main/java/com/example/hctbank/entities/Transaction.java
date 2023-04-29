package com.example.hctbank.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Long transactionRefId;

    Long accID;

    Double credit;

    Double debit;

    Double avlBalance;

    @UpdateTimestamp
    Date lastUpdated;

}
