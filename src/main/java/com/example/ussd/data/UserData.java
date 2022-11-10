package com.example.ussd.data;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserData {
    @Id
    private String aadharNo;
    private String phoneNo;
    
}
