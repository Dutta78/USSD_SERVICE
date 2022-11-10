package com.mosippe.ussd_api.Repositories;

import com.mosippe.ussd_api.Entities.UssdSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UssdSessionRepository extends JpaRepository<UssdSession, String> {
    UssdSession findBySessionId(String sessionId);

}
