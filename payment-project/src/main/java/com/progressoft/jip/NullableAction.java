package com.progressoft.jip;

public class NullableAction implements Action {

	public static final NullableAction INSTANCE = new NullableAction();

	private NullableAction() {

	}

	@Override
	public MenuContext doAction(MenuContext menuContext) {
		return null;
	}

}
