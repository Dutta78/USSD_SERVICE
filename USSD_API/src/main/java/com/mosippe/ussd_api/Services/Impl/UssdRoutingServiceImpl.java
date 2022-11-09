package com.mosippe.ussd_api.Services.Impl;

import com.mosippe.ussd_api.Entities.Menu;
import com.mosippe.ussd_api.Entities.MenuOption;
import com.mosippe.ussd_api.Entities.UssdSession;
import com.mosippe.ussd_api.Services.MenuService;
import com.mosippe.ussd_api.Services.SessionService;
import com.mosippe.ussd_api.Services.UssdRoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.text.StringSubstitutor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Service
public class UssdRoutingServiceImpl implements UssdRoutingService {
    @Autowired
    private MenuService menuService;
    @Autowired
    private SessionService sessionService;
    @Override
    public String menuLevelRouter(String sessionId, String serviceCode, String phoneNumber, String text) throws IOException {
        Map<String, Menu> menus = menuService.loadMenus();
        UssdSession session = checkAndSetSession(sessionId, serviceCode, phoneNumber, text);
        /**
         * Check if response has some value
         */
        return text.length() > 0 ? getNextMenuItem(session, menus) : menus.get(session.getCurrentMenuLevel()).getText();
    }

    @Override
    public String getNextMenuItem(UssdSession session, Map<String, Menu> menus) throws IOException {
        String[] levels = session.getText().split("\\*");
        String lastValue = levels[levels.length - 1];
        Menu menuLevel = menus.get(session.getCurrentMenuLevel());

        if (Integer.parseInt(lastValue) <= menuLevel.getMaxSelections()) {
            MenuOption menuOption = menuLevel.getMenuOptions().get(Integer.parseInt(lastValue) - 1);
            return processMenuOption(session, menuOption);
        }

        return "CON ";
    }

    @Override
    public String getMenu(String menuLevel) throws IOException {
        return menuService.loadMenus().get(menuLevel).getText();
    }

    @Override
    public String processMenuOption(UssdSession session, MenuOption menuOption) throws IOException {
        switch (menuOption.getType()) {
            case "response":
                return processMenuOptionResponses(menuOption);
            case "level":
                updateSessionMenuLevel(session, menuOption.getNextMenuLevel());
                return getMenu(menuOption.getNextMenuLevel());
            default:
                return "CON ";
        }
    }

    @Override
    public String processMenuOptionResponses(MenuOption menuOption) {
            String response = menuOption.getResponse();
            Map<String, String> variablesMap = new HashMap<>();

            switch (menuOption.getAction()) {
                case PROCESS_ACC_BALANCE:
                    variablesMap.put("account_balance", "10000");
                    break;
                case PROCESS_ACC_NUMBER:
                    variablesMap.put("account_number", "123412512");
                    break;
                case PROCESS_ACC_PHONE_NUMBER:
                    variablesMap.put("phone_number", "254702759950");
                    break;
            }

            response = replaceVariable(variablesMap, response);
            return response;
    }

    @Override
    public String replaceVariable(Map<String, String> variablesMap, String response) {
        return new StringSubstitutor(variablesMap).replace(response);
    }

    @Override
    public UssdSession updateSessionMenuLevel(UssdSession session, String menuLevel) {
        session.setPreviousMenuLevel(session.getCurrentMenuLevel());
        session.setCurrentMenuLevel(menuLevel);
        return sessionService.updateSession(session);
    }

    @Override
    public UssdSession checkAndSetSession(String sessionId, String serviceCode, String phoneNumber, String text) {
        UssdSession session = sessionService.getSession(sessionId);

        if (session != null) {
            session.setText(text);
            return sessionService.updateSession(session);
        }

        session = new UssdSession();
        session.setCurrentMenuLevel("1");
        session.setPreviousMenuLevel("1");
        session.setSessionId(sessionId);
        session.setPhoneNumber(phoneNumber);
        session.setServiceCode(serviceCode);
        session.setText(text);

        return sessionService.createUssdSession(session);
    }
}
