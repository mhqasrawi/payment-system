package com.progressoft.jip;

@FunctionalInterface
public interface Action {

	MenuContext doAction(MenuContext menuContext);

	default Action andThen(Action action) {
		return (c)->action.doAction(doAction(c));
	}

}
