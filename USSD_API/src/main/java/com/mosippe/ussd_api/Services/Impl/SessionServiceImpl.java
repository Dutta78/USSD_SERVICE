package com.mosippe.ussd_api.Services.Impl;

import com.mosippe.ussd_api.DTO.SessionDTO;
import com.mosippe.ussd_api.Repositories.SessionRepository;
import com.mosippe.ussd_api.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;

public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    @Override
    public SessionDTO createUssdSession(SessionDTO sessionDTO) {
        return sessionRepository.save(sessionDTO);
    }

    @Override
    public SessionDTO getSession(String id) {
        return sessionRepository.findById(id).orElse(null);
    }

    @Override
    public SessionDTO updateSession(SessionDTO newSessionDTO) {
        if(sessionRepository.existsById(newSessionDTO.getId()))
        {
            return sessionRepository.save(newSessionDTO);
        }
        throw new IllegalArgumentException("Invalid Id for session");
    }

    @Override
    public void deleteSession(String id) {
        sessionRepository.deleteById(id);
    }
}
