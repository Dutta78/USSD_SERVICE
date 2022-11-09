package com.mosippe.ussd_api.Services;


import com.mosippe.ussd_api.Entities.Menu;

import java.io.IOException;
import java.util.Map;

public interface MenuService {
    Map<String, Menu> loadMenus() throws IOException;
}
