package com.mosippe.ussd_api.Entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class UssdSession implements Serializable {

    @Id
    private String sessionId;
    private String serviceCode;
    private String phoneNumber;
    private String text;
    private String previousMenuLevel;
    private String currentMenuLevel;
}
