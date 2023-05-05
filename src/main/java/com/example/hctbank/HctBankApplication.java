package com.example.hctbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class HctBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(HctBankApplication.class, args);
        System.out.println(SpringVersion.getVersion());
        System.out.println(SpringBootVersion.getVersion());
    }

}
