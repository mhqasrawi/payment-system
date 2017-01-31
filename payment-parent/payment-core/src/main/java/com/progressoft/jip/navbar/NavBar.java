package com.progressoft.jip.navbar;

import com.progressoft.jip.ui.menu.Menu;

import java.util.List;

/**
 * Created by mhqasrawi on 27/12/16.
 */
public class NavBar {


    private final Menu menu;
    private final SingleMenuItemBuilder singleMenuItemBuilder;
    private final SubMenu subMenu;
    private String navbar = "";

    public NavBar(Menu menu, SingleMenuItemBuilder singleMenuItemBuilder, SubMenu subMenu) {
        this.menu = menu;
        this.singleMenuItemBuilder = singleMenuItemBuilder;
        this.subMenu = subMenu;
        navbar += generateNavBarHeader(menu.getDescription());
    }

    public String generate() {

        List<Menu> menuList = menu.getSubMenu();

        menuList.forEach(item -> {
            if (item.getSubMenu().size() == 0) {
                navbar += singleMenuItemBuilder.buildSingleMenuItem(item);
            } else {
                navbar += subMenu.buildSubMenuItems(item);
            }
        });

        navbar += appendEndNavBar();

        return navbar;
    }

    private String generateNavBarHeader(String mainMenuTitle) {
        return "<nav class=\"navbar navbar-default\">\n" +
                "  <div class=\"container-fluid\">\n" +
                "    <!-- Brand and toggle get grouped for better mobile display -->\n" +
                "    <div class=\"navbar-header\">\n" +
                "      <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=" +
                "\"#bs-example-navbar-collapse-1\" aria-expanded=\"false\">\n" +
                "        <span class=\"sr-only\">Toggle navigation</span>\n" +
                "        <span class=\"icon-bar\"></span>\n" +
                "        <span class=\"icon-bar\"></span>\n" +
                "        <span class=\"icon-bar\"></span>\n" +
                "      </button>\n" +
                "      <a class=\"navbar-brand\" href=\"#\">" + mainMenuTitle + "</a>\n" +
                "    </div>\n" +
                "\n" +
                "    <!-- Collect the nav links, forms, and other content for toggling -->\n" +
                "    <div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\"> <ul class=\"nav navbar-nav\">";
    }

    private String appendEndNavBar() {
        return " </div><!-- /.navbar-collapse -->\n" +
                "  </div><!-- /.container-fluid -->\n" +
                "</nav>";
    }


}
