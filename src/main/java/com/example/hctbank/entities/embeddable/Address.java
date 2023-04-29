package com.example.hctbank.entities.embeddable;

import com.example.hctbank.entities.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @OneToOne(mappedBy = "address")
    Customer customer;

    String city;
    String AddressLane;
    long pincode;
    String country;

    @UpdateTimestamp
    Date LastUpdated;

}
