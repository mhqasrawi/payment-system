package com.progressoft.jip.ui.action;

import com.progressoft.jip.ui.menu.MenuContext;

@FunctionalInterface
public interface Action<C extends MenuContext> {

    C doAction(C menuContext);

    default Action<C> andThen(Action<C> action) {
        return (c) -> action.doAction(doAction(c));
    }

}
