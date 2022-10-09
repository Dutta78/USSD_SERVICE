package com.mosippe.ussd_api.Services;

import com.mosippe.ussd_api.Entities.UssdSession;

public interface SessionService {
    UssdSession createUssdSession(UssdSession ussdSession);
    UssdSession getSession(String id);
    UssdSession updateSession(UssdSession newUssdSession);
    void deleteSession(String id);
}
