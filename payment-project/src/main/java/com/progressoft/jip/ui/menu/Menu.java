package com.progressoft.jip.ui.menu;

import java.util.List;

public interface Menu<C extends MenuContext> {

    String getDescription();

    List<Menu<C>> getSubMenu();

    void doAction(C t);

}
