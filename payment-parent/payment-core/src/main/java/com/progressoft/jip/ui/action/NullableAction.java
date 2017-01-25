package com.progressoft.jip.ui.action;

import com.progressoft.jip.ui.menu.MenuContext;

@SuppressWarnings("rawtypes")
public class NullableAction implements Action {

	public static final NullableAction INSTANCE = new NullableAction();

	private NullableAction() {

	}

	@Override
	public MenuContext doAction(MenuContext menuContext) {
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof NullableAction)
			return true;
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
