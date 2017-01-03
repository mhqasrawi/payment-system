package com.progressoft.jip.ui.webrendering.field;

import com.progressoft.jip.ui.field.Field;

@FunctionalInterface
public interface FieldRenderer {

	String renderToHtml(Field<?> field);

}
