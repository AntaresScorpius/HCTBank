package com.example.hctbank.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class Random12DigitGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        Random random = new Random();
        long randomNumber = (long) (random.nextDouble() * 900000000000L) + 100000000000L;
        return randomNumber;
    }
}
