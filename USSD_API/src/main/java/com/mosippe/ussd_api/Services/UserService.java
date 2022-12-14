package com.mosippe.ussd_api.Services;

public interface UserService {
    String generateVid(String phoneNumber);
    Boolean validatePin(String pin, String phoneNumber);
}
