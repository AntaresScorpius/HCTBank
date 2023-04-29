package com.example.hctbank.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerAccountMap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @OneToOne
    Account account;

    @OneToOne
    Customer customer;

}
