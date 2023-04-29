package com.example.hctbank.entities;

import com.example.hctbank.entities.embeddable.Address;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String name;

    long phone;
    String email;

    @OneToOne(cascade = CascadeType.ALL)
    Address address;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;

}
