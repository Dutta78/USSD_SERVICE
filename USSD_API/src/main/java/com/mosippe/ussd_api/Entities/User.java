package com.mosippe.ussd_api.Entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private String aadharNo;
    private String phoneNo;
}
