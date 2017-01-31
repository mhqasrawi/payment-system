package com.progressoft.jip.navbar;

import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mhqasrawi on 27/12/16.
 */
public class NavBarTest {

    public static final String SUB_1 = "sub1";
    public static final String SUB_2 = "sub2";
    public static final String SUB_3 = "sub3";
    public static final String DESCRIPTION = "description";
    private static final String SUB_4 = "sub4";
    private static final String SUB_5 = "sub5";
    private static final String SUB_6 = "sub6";
    private static final String SUB_7 = "sub7";
    private NavBar navBar;
    private Menu menu;
    private SingleMenuItemBuilder singleItem;
    private SubMenu subMenu;

    @Before
    public void setUp() throws Exception {
        menu = new MenuImpl("description", null, null);
        singleItem = new SingleMenuItemBuilder();
        subMenu = new SubMenu(singleItem);
        navBar = new NavBar(menu, singleItem, subMenu);
    }


    @Test
    public void givenMenuWithoutSub_WhenCallGenerate_ThenSingleMenuItemReturned() throws Exception {
        Menu singleMenuItem = new MenuImpl("description");
        String generatedItem
                = singleItem.buildSingleMenuItem(singleMenuItem);
        assertEquals("<li><a href=\"" + "description".hashCode() + "\">description</a></li>", generatedItem);
    }


    @Test
    public void givenMenuWithSub_WhenCallGenerate_ThenMenuWithSubReturned() throws Exception {
        List<Menu> subMenuItems = new ArrayList<>();
        List<Menu> subMenuSubItems = new ArrayList<>();
        Menu sub1 = new MenuImpl(SUB_1);
        Menu sub2 = new MenuImpl(SUB_2);
        Menu sub3 = new MenuImpl(SUB_3);


        subMenuSubItems.add(sub1);
        subMenuSubItems.add(sub2);
        subMenuSubItems.add(sub3);

        subMenuItems.add(sub1);
        subMenuItems.add(sub2);
        subMenuItems.add(sub3);


        Menu menu = new MenuImpl(DESCRIPTION, subMenuItems, null);
        SubMenu subMenu = new SubMenu(new SingleMenuItemBuilder());

        String generatedSubMenu = subMenu.buildSubMenuItems(menu);

        String menuItemWithSubMenu =
                "<li class=\"dropdown\">\n" +
                        "<a href=\"" +
                        "description".hashCode() + "\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" " +
                        "aria-haspopup=\"true\" " +
                        "aria-expanded=\"false\">description <span class=\"caret\"></span></a>" +
                        "<ul class=\"dropdown-menu\">" +
                        "<li><a href=\"" + "sub1".hashCode() + "\">sub1</a></li>" +
                        "<li><a href=\"" + "sub2".hashCode() + "\">sub2</a></li>" +
                        "<li><a href=\"" + "sub3".hashCode() + "\">sub3</a></li>" +
                        "</ul>";

        assertEquals(menuItemWithSubMenu, generatedSubMenu);

    }


    @Test
    public void givenMenuWithSubMenuAndSubMenuHaveSubMenu_WhenCallGenerate_ThenMEnuBuilt() throws Exception {

        List<Menu> subMenuItems = new ArrayList<>();
        List<Menu> subMenuSubItems = new ArrayList<>();
        Menu sub2 = new MenuImpl(SUB_2);
        Menu sub3 = new MenuImpl(SUB_3);

        Menu sub4 = new MenuImpl(SUB_4);
        Menu sub5 = new MenuImpl(SUB_5);


        subMenuSubItems.add(sub4);
        subMenuSubItems.add(sub5);
        Menu sub1 = new MenuImpl(SUB_1, subMenuSubItems, null);

        subMenuItems.add(sub1);
        subMenuItems.add(sub2);
        subMenuItems.add(sub3);


        Menu menu = new MenuImpl(DESCRIPTION, subMenuItems, null);
        SubMenu subMenu = new SubMenu(new SingleMenuItemBuilder());

        String generatedSubMenu = subMenu.buildSubMenuItems(menu);

        String menuItemWithSubMenu =
                "<li class=\"dropdown\">\n" +
                        "<a href=\"" +
                        "description".hashCode() + "\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" " +
                        "aria-haspopup=\"true\" " +
                        "aria-expanded=\"false\">description <span class=\"caret\"></span></a>" +
                        "<ul class=\"dropdown-menu\">" +
                        "<li class=\"dropdown\">\n" +
                        "<a href=\"" + "sub1".hashCode() + "\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" " +
                        "aria-haspopup=\"true\" aria-expanded=\"false\">sub1 <span class=\"caret\"></span></a>" +
                        "<ul class=\"dropdown-menu\">" +
                        "<li><a href=\"" + "sub4".hashCode() + "\">sub4</a></li>" +
                        "<li><a href=\"" + "sub5".hashCode() + "\">sub5</a></li>" +
                        "</ul>" +
                        "</li>" +
                        "<li><a href=\"" + "sub2".hashCode() + "\">sub2</a></li>" +
                        "<li><a href=\"" + "sub3".hashCode() + "\">sub3</a></li>" +
                        "</ul>";


        assertEquals(menuItemWithSubMenu, generatedSubMenu);

    }

//    @Test
//    public void givenMenuWithSubMenuAndSubOfSubMenuHaveSubMenu_WhenCallGenerate_ThenMEnuBuilt() throws Exception {
//        List<Menu> subMenuItems = new ArrayList<>();
//        List<Menu> subMenuSubItems = new ArrayList<>();
//        List<Menu> subOfSubItems = new ArrayList<>();
//        Menu sub2 = new MenuImpl(SUB_2);
//        Menu sub3 = new MenuImpl(SUB_3);
//
//
//        Menu sub5 = new MenuImpl(SUB_5);
//        Menu sub6 = new MenuImpl(SUB_6);
//        Menu sub7 = new MenuImpl(SUB_7);
//
//
//        subOfSubItems.add(sub6);
//        subOfSubItems.add(sub7);
//        Menu sub4 = new MenuImpl(SUB_4, subOfSubItems, null);
//        subMenuSubItems.add(sub4);
//        subMenuSubItems.add(sub5);
//        Menu sub1 = new MenuImpl(SUB_1, subMenuSubItems, null);
//
//
//        subMenuItems.add(sub1);
//        subMenuItems.add(sub2);
//        subMenuItems.add(sub3);
//
//
//        Menu menu = new MenuImpl(DESCRIPTION, subMenuItems, null);
//        SubMenu subMenu = new SubMenu(new SingleMenuItemBuilder());
//        String generatedSubMenu = subMenu.buildSubMenuItems(menu);
//
//        String menuItemWithSubMenu =
//                "<li class=\"dropdown\">\n" +
//                        "<a href=\"" +
//                        "description".hashCode() + "\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" " +
//                        "aria-haspopup=\"true\" " +
//                        "aria-expanded=\"false\">description <span class=\"caret\"></span></a>" +
//                        "<ul class=\"dropdown-menu\">" +
//                        "<li class=\"dropdown\">\n" +
//                        "<a href=\"" + "sub1".hashCode() + "\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" " +
//                        "aria-haspopup=\"true\" aria-expanded=\"false\">sub1 <span class=\"caret\"></span></a>" +
//                        "<ul class=\"dropdown-menu\">" +
//                        "<li class=\"dropdown\">\n" +
//                        "<li><a href=\"" + "sub4".hashCode() + "\"  class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" " +
//                "aria-haspopup="true\" aria-expanded=\"false">sub4<span class=\"caret\"></span></a>" +
//                ""<ul class=\"dropdown-menu\">" +
//                "<li><a href=\"" + "sub6".hashCode() + "\">sub6</a></li>" +
//                "<li><a href=\"" + "sub7".hashCode() + "\">sub7</a></li>" +
//                "</ul>" +
//                "</li>" +
//
//                "<li><a href=\"" + "sub5".hashCode() + "\">sub5</a></li>" +
//                        "</ul>" +
//                        "</li>" +
//                        "<li><a href=\"" + "sub2".hashCode() + "\">sub2</a></li>" +
//                        "<li><a href=\"" + "sub3".hashCode() + "\">sub3</a></li>" +
//                        "</ul>""";
    //}
}
