package com.progressoft.jip;

public interface Menu {

    String getDescription();

    Iterable<Menu> getSubMenu();

    Action getRelatedAction();

}
