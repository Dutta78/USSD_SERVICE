package com.example.ussd.data;

import com.example.ussd.enums.MenuOptionAction;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MenuOption {
    private String type;

    private String response;

    @JsonProperty("next_menu_level")
    private String nextMenuLevel;

    private MenuOptionAction action;
}
