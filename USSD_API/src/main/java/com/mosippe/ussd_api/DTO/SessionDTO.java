package com.mosippe.ussd_api.DTO;

import lombok.Data;

@Data
public class SessionDTO {
    private String id;
    private String sessionId;
    private String serviceCode;
    private String phoneNumber;
    private String text;
    private String previousMenuLevel;
    private String currentMenuLevel;
}
