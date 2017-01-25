package com.progressoft.jip.navbar;

import com.progressoft.jip.ui.menu.Menu;

import java.util.List;
import java.util.Objects;

/**
 * Created by mhqasrawi on 27/12/16.
 */
public class SubMenu {

    private SingleMenuItemBuilder singleMenuItemBuilder;
    public static final String SUB_MENU_START_HEADER = "<li class=\"dropdown\">\n" +
            "<a href=\"";
    String SUB_MENU_START_HEDAER_END = "\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" " +
            "aria-expanded=\"false\">";
    public static final String SUB_MENU_END_HEADER = " <span class=\"caret\"></span></a>" +
            "<ul class=\"dropdown-menu\">";

    public static final String END_SUB_MENU = "</ul>";
    private static final String END_ITEM = "</li>";

    private StringBuilder subMenu = new StringBuilder();

    public SubMenu(SingleMenuItemBuilder singleMenuItemBuilder) {
        this.singleMenuItemBuilder = singleMenuItemBuilder;
    }

    public String buildSubMenuItems(Menu menu) {
        subMenu.append(SUB_MENU_START_HEADER).append(menu.getId()).append(SUB_MENU_START_HEDAER_END).append(menu.getDescription()).append(SUB_MENU_END_HEADER);
        List<Menu> subMenuList = menu.getSubMenu();
        for (Menu subMenuItem : subMenuList) {
            if (subMenuItem.getSubMenu().size() > 0) {
                subMenu.append(SUB_MENU_START_HEADER).append(subMenuItem.getId()).append(SUB_MENU_START_HEDAER_END).append(subMenuItem.getDescription()).append(SUB_MENU_END_HEADER);
                String singleSubMenu = buildMenuItem(subMenuItem.getSubMenu());
                subMenu.append(singleSubMenu).append(END_SUB_MENU).append(END_ITEM);
            } else {
                subMenu.append(buildSingleMenuItem(subMenuItem));
            }
        }
        subMenu.append(END_SUB_MENU);
        return subMenu.toString();
    }

    private String buildMenuItem(List<Menu> subMenuItem) {
        String htmlItemString = "";
        for (Menu item : subMenuItem) {
            if (item.getSubMenu().size() == 0)
                htmlItemString += buildSingleMenuItem(item);
            else return htmlItemString + buildMenuItem(item.getSubMenu());
        }
        return htmlItemString;
    }

    private String buildSingleMenuItem(Menu item) {
        return singleMenuItemBuilder.buildSingleMenuItem(item);
    }
}
