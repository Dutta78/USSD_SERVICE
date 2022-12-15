package com.mosippe.ussd_api.Services.Impl;

import com.mosippe.ussd_api.Entities.UssdSession;
import com.mosippe.ussd_api.Repositories.UssdSessionRepository;
import com.mosippe.ussd_api.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private UssdSessionRepository ussdSessionRepository;
    @Override
    public UssdSession createUssdSession(UssdSession ussdSession) {
        return ussdSessionRepository.save(ussdSession);
    }

    @Override
    public UssdSession getSession(String id) {
        return ussdSessionRepository.findById(id).orElse(null);
    }

    @Override
    public UssdSession updateSession(UssdSession newUssdSession) {
        if(ussdSessionRepository.existsById(newUssdSession.getSessionId()))
        {
            return ussdSessionRepository.save(newUssdSession);
        }
        throw new IllegalArgumentException("Invalid Id for session");
    }

    @Override
    public void deleteSession(String id) {
        ussdSessionRepository.deleteById(id);
    }
}
