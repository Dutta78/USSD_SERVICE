package com.mosippe.ussd_api.Services;

import com.mosippe.ussd_api.Entities.Menu;
import com.mosippe.ussd_api.Entities.MenuOption;
import com.mosippe.ussd_api.Entities.UssdSession;

import java.io.IOException;
import java.util.Map;

public interface UssdRoutingService {
    String menuLevelRouter(String sessionId, String serviceCode, String phoneNumber, String text)
        throws IOException;
    String getNextMenuItem(UssdSession session, Map<String, Menu> menus) throws IOException;
    String getMenu(String menuLevel) throws IOException;
    String processMenuOption(UssdSession session, MenuOption menuOption) throws IOException;
    String processMenuOptionResponses(MenuOption menuOption);
    String replaceVariable(Map<String, String> variableMap, String response);
    UssdSession updateSessionMenuLevel(UssdSession session, String menuLevel);
    UssdSession checkAndSetSession(String sessionId, String serviceCode, String phoneNumber, String text);
}
