package com.twilio.twilio.controller;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twilio.dto.MessageRequest;
import com.twilio.twilio.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/send")
public class HomeController {

    @Autowired
    TwilioService twilioService;

    @PutMapping
    public List<Message> sendMessage(@RequestBody final MessageRequest request){
        return twilioService.sendMessage(request);
    }
}
