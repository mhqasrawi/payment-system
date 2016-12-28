package com.progressoft.jip.ui.webrendering.impl;

import java.util.Objects;

import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.webrendering.HtmlRenderer;
import com.progressoft.jip.ui.webrendering.HtmlRendererException;

public class VisibleFieldHtmlRenderer implements HtmlRenderer {
	private Field<?> field;

	public VisibleFieldHtmlRenderer(Field<?> field) {
		this.field = field;
	}

	@Override
	public String renderToHtml() {
		if(Objects.isNull(field.getName())) {
			throw new HtmlRendererException("Given field has a null name");
		}
		
		if("".equals(field.getName().trim())) {
			throw new HtmlRendererException("Given field has an empty name");
		}
		
		return new StringBuilder().append("<div class=\"form-group\">").append("<label for=\"").append(field.getName())
				.append("\">").append(field.getDescription()).append("</label>")
				.append("<input type=\"text\" class=\"form-control\" id=\"").append(field.getName()).append("\">")
				.append("</div>").toString();
	}
}
