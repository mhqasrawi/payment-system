package com.progressoft.jip.navbar;

import com.progressoft.jip.ui.menu.Menu;

/**
 * Created by mhqasrawi on 27/12/16.
 */
public class SingleMenuItemBuilder {

    private static final String START_ITEM = "<li>";
    private static final String END_ITEM = "</li>";

    private static final String START_ANCOR = "<a href=\"";
    public static final String END_ANCOR = "</a>";


    public String buildSingleMenuItem(Menu singleMenuItem) {
        return START_ITEM + START_ANCOR + singleMenuItem.getId() + "\">"+singleMenuItem.getDescription() +END_ANCOR + END_ITEM;


    }
}
