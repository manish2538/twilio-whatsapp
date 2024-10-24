package com.twilio.twilio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    private List<String> phoneNumbers = new ArrayList<>();

    private String message;
}
