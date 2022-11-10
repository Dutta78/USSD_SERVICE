package com.mosippe.ussd_api.Entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class UserData implements Serializable {
    @Id
    private String aadharNo;
    private String phoneNo;
}
