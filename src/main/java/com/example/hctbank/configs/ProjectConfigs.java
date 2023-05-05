package com.example.hctbank.configs;

import com.vonage.client.VonageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProjectConfigs {

    @Value("${hct.bank.sms.api.key}")
    String apiKey;

    @Value("${hct.bank.sms.api.secret}")
    String apiSecret;

    @Bean
    public WebClient webClient(){

        return WebClient.builder().baseUrl("http://localhost/sms").build();
    }
    @Bean
    public VonageClient vonageClient(){
        return VonageClient.builder().apiKey(apiKey).apiSecret(apiSecret).build();
    }

}
