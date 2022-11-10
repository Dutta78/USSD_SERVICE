package com.example.ussd.enums;

import com.fasterxml.jackson.annotation.JsonValue;


public enum MenuOptionAction {
    PROCESS_ACC_BALANCE("PROCESS_ACC_BALANCE"),
    PROCESS_ACC_PHONE_NUMBER("PROCESS_ACC_PHONE_NUMBER"),
    PROCESS_ACC_NUMBER("PROCESS_ACC_NUMBER"),
    PROCESS_ACC_AADHAR("PROCESS_ACC_AADHAR");

    private String action;

    MenuOptionAction(String action) {
        this.action = action;
    }

    @JsonValue
    private String getAction() {
        return action;
    }
    
}
