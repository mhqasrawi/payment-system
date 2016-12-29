package com.progressoft.jip.ui.menu;

import java.util.Collections;
import java.util.List;

import com.progressoft.jip.ui.action.Action;
import com.progressoft.jip.ui.action.NullableAction;

public class MenuImpl<C extends MenuContext> implements Menu<C> {

	private final String description;
	private final List<Menu<C>> subMenu ;
	private final Action<C> action ;

	public MenuImpl( String description, List<Menu<C>> subMenu, Action<C> action) {
		this.action = action;
		this.subMenu = subMenu;
		this.description = description;
	}

	public MenuImpl(String description, Action<C> action) {
		this(description,Collections.emptyList(),action);
	}

	@SuppressWarnings("unchecked")
	public MenuImpl(String description, List<Menu<C>> subMenu) {
		this(description,subMenu,NullableAction.INSTANCE);
	}

	public MenuImpl(String description) {
		this(description,Collections.emptyList());
	}

	@Override
	public String getId() {
		return String.valueOf(description.hashCode());
	}
	public String getDescription() {
		return description;
	}

	public List<Menu<C>> getSubMenu() {
		return subMenu;
	}

	public void addSubMenu(Menu<C> menu) {
		subMenu.add(menu);
	}

	@Override
	public void doAction(C t) {
		action.doAction(t);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((subMenu == null) ? 0 : subMenu.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuImpl other = (MenuImpl) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (subMenu == null) {
			if (other.subMenu != null)
				return false;
		} else if (!subMenu.equals(other.subMenu))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuImpl [description=" + description + ", subMenu=" + subMenu + ", action=" + action + "]";
	}

	
}
