package com.example.ussd.repository;

import com.example.ussd.data.UssdSession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UssdSessionRepository extends JpaRepository<UssdSession, String>{
    @Query(value="SELECT * FROM USSD_SESSION WHERE ID=?1",nativeQuery = true)
    public UssdSession findById1(String ID);
    
}
