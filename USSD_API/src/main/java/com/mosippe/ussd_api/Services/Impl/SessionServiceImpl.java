package com.mosippe.ussd_api.Services.Impl;

import com.mosippe.ussd_api.Entities.UssdSession;
import com.mosippe.ussd_api.Repositories.SessionRepository;
import com.mosippe.ussd_api.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    @Override
    public UssdSession createUssdSession(UssdSession ussdSession) {
        return sessionRepository.save(ussdSession);
    }

    @Override
    public UssdSession getSession(String id) {
        return sessionRepository.findById(id).orElse(null);
    }

    @Override
    public UssdSession updateSession(UssdSession newUssdSession) {
        if(sessionRepository.existsById(newUssdSession.getSessionId()))
        {
            return sessionRepository.save(newUssdSession);
        }
        throw new IllegalArgumentException("Invalid Id for session");
    }

    @Override
    public void deleteSession(String id) {
        sessionRepository.deleteById(id);
    }
}
