package com.mosippe.ussd_api.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserData implements Serializable {
    @Id
    private String aadharNo;
    private String phoneNo;
    private String vid;
}
