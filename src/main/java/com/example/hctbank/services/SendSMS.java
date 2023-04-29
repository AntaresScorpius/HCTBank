package com.example.hctbank.services;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class SendSMS {
    String message;
    String number;
    @Value("${hct.bank.sms.api.key}")
    String apiKey;

    @Value("${hct.bank.sms.api.secret}")
    String apiSecret;




    VonageClient client;

    public SendSMS(VonageClient client){
        this.client = client;
    }

    boolean validate(String number){
        if (number.length() != 13) return false;

        return true;
    }

    public void sendTransactionSms(Long phone, Long ref_id, Long fromAccountId, Long ToAccountId,double amount, double availableBalance){
        String number =  "+91" + String.valueOf(phone);
        if (validate(number)){
            System.out.println("Sending SMS to: " +number);
//            TextMessage message = new TextMessage("Vonage APIs",
//                    number,
//                    "Debited "+ amount + " from your account to" + ToAccountId +
//                    "Available Balance is " + availableBalance
//            );
//            System.out.println(apiKey + " " + apiSecret);
//            SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
//            if (response.getMessages().get(0).getStatus() == MessageStatus.OK){
//                System.out.println("message sent");
//            }else {
//                System.out.println("failed to send sms");
//            }
        }else {
            System.out.println("Number Validation Failed");
        }
    }

    public void sendSms(Long phone, Long id){
        String number =  "+91" + String.valueOf(phone);
        if (validate(number)){
            System.out.println("Sending SMS to: " +number);
            TextMessage message = new TextMessage("Vonage APIs",
                    number,
                    "Congratulations Thank You for choosing HCT-Bank(formerly  SVB) the best bank where your money is super safe" + "Your Account ID is:" +id
            );
//            System.out.println(apiKey + " " + apiSecret);
//            SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
//            if (response.getMessages().get(0).getStatus() == MessageStatus.OK){
//                System.out.println("message sent");
//            }else {
//                System.out.println("failed to send sms");
//            }
        }else {
            System.out.println("Number Validation Failed");
        }
    }
}
