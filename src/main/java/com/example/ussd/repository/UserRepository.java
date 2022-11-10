package com.example.ussd.repository;

import com.example.ussd.data.UserData;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserData,String>{
    @Query(value="SELECT * FROM USER_DATA WHERE PHONE_NO=?1",nativeQuery=true)
    public UserData findByPhone(String phone);

    @Query(value="SELECT * FROM USER_DATA",nativeQuery=true)
    public List<UserData> getUserData();

    
}
