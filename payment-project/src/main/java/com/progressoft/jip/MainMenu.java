package com.progressoft.jip;

import java.util.List;

public class MainMenu extends MenuImpl{

	private static final String CHOISE_OPTION = "Choise  Option : ";

	public MainMenu(List<Menu> subMenu) {
		super(CHOISE_OPTION,subMenu);
	}


}
