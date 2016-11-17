package com.progressoft.jip;

import java.util.Collections;
import java.util.List;

public class MenuImpl implements Menu {

	private String description;
	private List<Menu> subMenu = Collections.emptyList();
	private Action action = NullableAction.INSTANCE;

	public MenuImpl(String description, List<Menu> subMenu, Action action) {
		this.action = action;
		this.subMenu = subMenu;
		this.description = description;
	}

	public MenuImpl(String description, Action action) {
		this.action = action;
		this.description = description;
	}

	public MenuImpl(String description, List<Menu> subMenu) {
		this.description = description;
		this.subMenu = subMenu;
	}

	public MenuImpl(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public List<Menu> getSubMenu() {
		return subMenu;
	}

	protected void addSubMenu(Menu menu){
		subMenu.add(menu);
	}
	public Action getRelatedAction() {
		return action;
	}

}
