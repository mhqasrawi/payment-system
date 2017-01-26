package com.progressoft.jip.navbar;

import com.progressoft.jip.ui.menu.Menu;

import java.util.List;

/**
 * Created by mhqasrawi on 27/12/16.
 */
public class SubMenu {

    public static final String SUB_MENU_START_HEADER = "<li class=\"dropdown\">\n" +
            "<a href=\"";
    public static final String SUB_MENU_END_HEADER = " <span class=\"caret\"></span></a>" +
            "<ul class=\"dropdown-menu\">";
    public static final String END_SUB_MENU = "</ul>";
    private static final String END_ITEM = "</li>";
    String SUB_MENU_START_HEDAER_END = "\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" " +
            "aria-expanded=\"false\">";
    private SingleMenuItemBuilder singleMenuItemBuilder;
    private StringBuilder subMenuString = new StringBuilder();

    public SubMenu(SingleMenuItemBuilder singleMenuItemBuilder) {
        this.singleMenuItemBuilder = singleMenuItemBuilder;
    }


    private String buildMenuItem(List<Menu> subMenuItem) {
        StringBuilder htmlItemString = new StringBuilder();
        for (Menu item : subMenuItem) {
            if (item.getSubMenu().size() == 0)
                htmlItemString.append(buildSingleMenuItem(item));
            else return htmlItemString + buildMenuItem(item.getSubMenu());
        }
        return htmlItemString.toString();
    }

    public String buildSubMenuItems(Menu menu) {
        subMenuString.append(SUB_MENU_START_HEADER).append(menu.getId()).append(SUB_MENU_START_HEDAER_END).append(menu.getDescription()).append(SUB_MENU_END_HEADER);
        List<Menu> subMenuList = menu.getSubMenu();
        for (Menu subMenuItem : subMenuList) {
            if (subMenuItem.getSubMenu().size() > 0) {
                subMenuString.append(SUB_MENU_START_HEADER).append(subMenuItem.getId()).append(SUB_MENU_START_HEDAER_END).append(subMenuItem.getDescription()).append(SUB_MENU_END_HEADER);
                String singleSubMenu = buildMenuItem(subMenuItem.getSubMenu());
                subMenuString.append(singleSubMenu).append(END_SUB_MENU).append(END_ITEM);
            } else {
                subMenuString.append(buildSingleMenuItem(subMenuItem));
            }
        }
        subMenuString.append(END_SUB_MENU);
        return subMenuString.toString();
    }


    private String buildSingleMenuItem(Menu item) {
        return singleMenuItemBuilder.buildSingleMenuItem(item);
    }
}
