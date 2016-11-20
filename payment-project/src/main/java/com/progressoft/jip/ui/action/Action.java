package com.progressoft.jip.ui.action;

import com.progressoft.jip.ui.menu.MenuContext;

@FunctionalInterface
public interface Action<T extends MenuContext> {

	T doAction(T menuContext);

	default Action<T> andThen(Action<T> action) {
		return (c)->action.doAction(doAction(c));
	}

}
