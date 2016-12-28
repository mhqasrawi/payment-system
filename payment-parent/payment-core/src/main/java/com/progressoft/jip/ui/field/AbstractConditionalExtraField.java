package com.progressoft.jip.ui.field;

import com.progressoft.jip.ui.menu.MenuContext;

/**
 *
 *
 */
public abstract class AbstractConditionalExtraField<T,V extends MenuContext> extends AbstractField<T> {

	public abstract boolean isVisiable(V menuContext);

}
