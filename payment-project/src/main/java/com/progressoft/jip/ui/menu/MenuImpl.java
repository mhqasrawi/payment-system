package com.progressoft.jip.ui.menu;

import java.util.Collections;
import java.util.List;

import com.progressoft.jip.ui.action.Action;
import com.progressoft.jip.ui.action.NullableAction;

public class MenuImpl<T extends MenuContext> implements Menu<T> {

	private String description;
	private List<Menu<T>> subMenu = Collections.emptyList();
	private Action<T> action = NullableAction.INSTANCE;

	public MenuImpl( String description, List<Menu<T>> subMenu, Action<T> action) {
		this.action = action;
		this.subMenu = subMenu;
		this.description = description;
	}

	public MenuImpl(String description, Action<T> action) {
		this.action = action;
		this.description = description;
	}

	public MenuImpl(String description, List<Menu<T>> subMenu) {
		this.description = description;
		this.subMenu = subMenu;
	}

	public MenuImpl(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public List<Menu<T>> getSubMenu() {
		return subMenu;
	}

	protected void addSubMenu(Menu<T> menu) {
		subMenu.add(menu);
	}

	@Override
	public void doAction(T t) {
		action.doAction(t);
	}

}
