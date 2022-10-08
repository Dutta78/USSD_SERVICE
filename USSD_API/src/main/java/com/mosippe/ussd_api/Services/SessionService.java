package com.mosippe.ussd_api.Services;

import com.mosippe.ussd_api.DTO.SessionDTO;

public interface SessionService {
    SessionDTO createUssdSession(SessionDTO sessionDTO);
    SessionDTO getSession(String id);
    SessionDTO updateSession(SessionDTO newSessionDTO);
    void deleteSession(String id);
}
