package com.progressoft.jip.ui.xml;

import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;

/**
 * @author Ahmad.Jardat
 */
public interface UserInterfaceGenerator<T extends MenuContext> {

	Menu<T> generateUserInterface();
}