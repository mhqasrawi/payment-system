package com.progressoft.jip.ui.menu;

import java.util.Collections;
import java.util.List;

import com.progressoft.jip.ui.action.Action;
import com.progressoft.jip.ui.action.NullableAction;

public class MenuImpl<T extends MenuContext> implements Menu<T> {

	private final String description;
	private final List<Menu<T>> subMenu ;
	private final Action<T> action ;

	public MenuImpl( String description, List<Menu<T>> subMenu, Action<T> action) {
		this.action = action;
		this.subMenu = subMenu;
		this.description = description;
	}

	public MenuImpl(String description, Action<T> action) {
		this(description,Collections.emptyList(),action);
	}

	@SuppressWarnings("unchecked")
	public MenuImpl(String description, List<Menu<T>> subMenu) {
		this(description,subMenu,NullableAction.INSTANCE);
	}

	public MenuImpl(String description) {
		this(description,Collections.emptyList());
	}

	public String getDescription() {
		return description;
	}

	public List<Menu<T>> getSubMenu() {
		return subMenu;
	}

	public void addSubMenu(Menu<T> menu) {
		subMenu.add(menu);
	}

	@Override
	public void doAction(T t) {
		action.doAction(t);
	}

}
