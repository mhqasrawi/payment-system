package com.progressoft.jip.ui.menu;

import java.util.List;

public interface Menu<T extends MenuContext> {

    String getDescription();

    List<Menu<T>> getSubMenu();

    void doAction(T t);

}
