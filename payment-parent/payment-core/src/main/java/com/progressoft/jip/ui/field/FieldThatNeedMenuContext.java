package com.progressoft.jip.ui.field;

import com.progressoft.jip.ui.menu.MenuContext;

@FunctionalInterface
public interface FieldThatNeedMenuContext<T extends MenuContext> {

    void setMenuContext(T menuContext);

}
