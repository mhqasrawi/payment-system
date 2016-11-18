package com.progressoft.jip;

import java.util.List;

import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuImpl;

public class MainMenu extends MenuImpl<PaymentMenuContext>{

	private static final String CHOISE_OPTION = "Choise  Option : ";

	public MainMenu(List<Menu<PaymentMenuContext>> subMenu) {
		super(CHOISE_OPTION,subMenu);
	}


}
