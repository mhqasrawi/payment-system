package com.progressoft.jip.ui.webrendering.impl;

import java.util.Iterator;
import java.util.Objects;

import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.webrendering.HtmlRenderer;
import com.progressoft.jip.ui.webrendering.HtmlRendererException;

public class FormHtmlRenderer implements HtmlRenderer {
	private Form form;

	public FormHtmlRenderer(Form form) {
		this.form = form;
	}

	@Override
	public String renderToHtml() {
		if(Objects.isNull(form.getFields())) {
			throw new HtmlRendererException("Given form contains null fields");
		}
		
		Iterator<Field<?>> iterator = form.getFields().iterator();
		if(!iterator.hasNext()) {
			throw new HtmlRendererException("Given form contains empty fields");
		}
		
		StringBuilder formHtml = new StringBuilder();
		formHtml.append("<div class=\"container\">").append("<h2>").append(form.getDescription()).append("</h2>")
				.append("<form>").append("<div class=\"form-group\">");

		for (Field<?> field : form.getFields()) {
			formHtml.append(new VisibleFieldHtmlRenderer(field).renderToHtml());
		}

		formHtml.append("<button type=\"submit\" class=\"btn btn-default\">").append("Submit").append("</button>");
		formHtml.append("</form>");
		formHtml.append("</div>");
		return formHtml.toString();
	}
}
