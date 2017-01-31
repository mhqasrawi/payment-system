package com.progressoft.jip.ui.menu;

import java.util.List;

/**
 * @author u612
 *         Understand Execution Of Menu Action
 */
public interface Menu<C extends MenuContext> {

    String getDescription();

    List<Menu<C>> getSubMenu();

    void doAction(C t);

    String getId();

}
