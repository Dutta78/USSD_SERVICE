package com.mosippe.ussd_api.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.mosippe.ussd_api.Enums.MenuOptionAction;
import lombok.Data;

@Data
public class MenuOption {

    private String type;

    private String response;

    @JsonProperty("next_menu_level")
    private String nextMenuLevel;

    private MenuOptionAction action;
}