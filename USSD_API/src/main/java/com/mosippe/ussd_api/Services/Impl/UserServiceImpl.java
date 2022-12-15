package com.mosippe.ussd_api.Services.Impl;

import com.mosippe.ussd_api.Entities.UserData;
import com.mosippe.ussd_api.Repositories.UserRepository;
import com.mosippe.ussd_api.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public String generateVid(String phoneNumber) {
        UserData user = userRepository.findByPhoneNo(phoneNumber);
        String vid = Double.toString((Math.random() * ((999999999 - 100000000) + 1)) + 100000000);
        String tenVid = vid.substring(0,9);
        user.setVid(tenVid);
        userRepository.save(user);
        return tenVid;
    }

    @Override
    public Boolean validatePin(String pin, String phoneNumber) {
        UserData userData = userRepository.findByPhoneNo(phoneNumber);
        System.out.println(pin);
        String validPin = userData.getAadharNo().substring(8);
        System.out.println(validPin);
        if(validPin.equals(pin)) return true;
        return false;
    }

}
