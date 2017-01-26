package com.progressoft.jip;

import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuImpl;

import java.util.List;

public class MainMenu extends MenuImpl<PaymentMenuContext> {

    private static final String CHOOSE_OPTION = "Choose  Option : ";

    public MainMenu(List<Menu<PaymentMenuContext>> subMenu) {
        super(CHOOSE_OPTION, subMenu);
    }
}
