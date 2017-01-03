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

    private String subMenu = "";

    public SubMenu(SingleMenuItemBuilder singleMenuItemBuilder) {
        this.singleMenuItemBuilder = singleMenuItemBuilder;
    }

    public String buildSubMenuItems(Menu menu) {
        subMenu += SUB_MENU_START_HEADER + menu.getId() + SUB_MENU_START_HEDAER_END + menu.getDescription() + SUB_MENU_END_HEADER;
        List<Menu> subMenuList = menu.getSubMenu();
        for (Menu subMenuItem : subMenuList) {
            if (subMenuItem.getSubMenu().size()>0) {
                subMenu += SUB_MENU_START_HEADER + subMenuItem.getId() + SUB_MENU_START_HEDAER_END + subMenuItem.getDescription() + SUB_MENU_END_HEADER;
                String singleSubMenu = buildMenuItem(subMenuItem.getSubMenu());
                subMenu += singleSubMenu+END_SUB_MENU + END_ITEM;
            } else {
               subMenu+= buildSingleMenuItem(subMenuItem);
            }
        }
        subMenu += END_SUB_MENU;
        return subMenu;
    }

    private String buildMenuItem(List<Menu> subMenuItem) {
        String htmlItemString = "";
        for (Menu item : subMenuItem) {
            if (item.getSubMenu().size()==0)
                 htmlItemString += buildSingleMenuItem(item);
            else return htmlItemString + buildMenuItem(item.getSubMenu());
        }
        return htmlItemString ;
    }

    private String buildSingleMenuItem(Menu item) {
        return singleMenuItemBuilder.buildSingleMenuItem(item);
    }
}
