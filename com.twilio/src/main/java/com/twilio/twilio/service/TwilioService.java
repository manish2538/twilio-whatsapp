package com.twilio.twilio.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twilio.dto.MessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TwilioService {


    @Value("${twilio.sid}")
    public String ACCOUNT_SID;

    @Value("${twilio.auth}")
    public String AUTH_TOKEN;

    @Value(("${twilio.phoneNumber}"))
    public String FROM_PHONE_NUMBER;

    private static final String WHATSAPP_PREFIX = "whatsapp:";


    public List<Message> sendMessage(final MessageRequest request) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        List<Message> response = new ArrayList<>();

        List<CompletableFuture<Message>> futures = new ArrayList<>();
        int counter = 0;

        for (String number : request.getPhoneNumbers()) {
            CompletableFuture<Message> future = CompletableFuture.supplyAsync(() -> {
                return Message.creator(
                        new com.twilio.type.PhoneNumber(WHATSAPP_PREFIX + number),
                        new com.twilio.type.PhoneNumber(WHATSAPP_PREFIX + FROM_PHONE_NUMBER),
                        request.getMessage()
                ).create();
            });

            futures.add(future);
            counter++;

            // Check if we reached 100 messages
            if (counter % 100 == 0) {
                try {
                    // Delay of 1 second
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
        }

        // Wait for all futures to complete and gather results
        response = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        return response;
    }

}
