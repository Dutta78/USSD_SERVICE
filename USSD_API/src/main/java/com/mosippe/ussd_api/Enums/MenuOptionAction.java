package com.mosippe.ussd_api.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MenuOptionAction {
    PROCESS_ACC_UIN("PROCESS_ACC_UIN"),
    PROCESS_GET_UIN("PROCESS_GET_UIN"),
    PROCESS_SAVE_UIN("PROCESS_SAVE_UIN"),
    PROCESS_GENERATE_VID("PROCESS_GENERATE_VID"),
    PROCESS_GET_VID("PROCESS_GET_VID");

    private String action;

    MenuOptionAction(String action) {
        this.action = action;
    }

    @JsonValue
    private String getAction() {
        return action;
    }
}

