package com.mosippe.ussd_api.DTO;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class SessionDTO {
    @Id
    private String id;
    private String sessionId;
    private String serviceCode;
    private String phoneNumber;
    private String text;
    private String previousMenuLevel;
    private String currentMenuLevel;
}
