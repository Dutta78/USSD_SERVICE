package com.mosippe.ussd_api.Repositories;

import com.mosippe.ussd_api.DTO.SessionDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionDTO, String> {
}
