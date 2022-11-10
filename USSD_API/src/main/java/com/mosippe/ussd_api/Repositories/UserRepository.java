package com.mosippe.ussd_api.Repositories;

import com.mosippe.ussd_api.Entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserData, String> {
    UserData findByPhoneNo(String phoneNo);
}
