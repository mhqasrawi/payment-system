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

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<Menu> getSubMenu() {
		return subMenu;
	}

	@Override
	public Action getRelatedAction() {
		return action;
	}

}
