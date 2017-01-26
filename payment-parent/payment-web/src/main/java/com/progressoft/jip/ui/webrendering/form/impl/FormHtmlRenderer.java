package com.progressoft.jip.ui.webrendering.form.impl;

import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.webrendering.WebRenderingException;
import com.progressoft.jip.ui.webrendering.field.impl.VisibleFieldHtmlRenderer;
import com.progressoft.jip.ui.webrendering.form.FormRenderer;

import java.util.Iterator;
import java.util.Objects;

public class FormHtmlRenderer<C extends MenuContext, T> implements FormRenderer<C, T> {

    @Override
    public String renderToHtml(Form<C, T> form) {
        if (Objects.isNull(form.getFields())) {
            throw new WebRenderingException("Given form contains null fields");
        }

        Iterator<Field<?>> iterator = form.getFields().iterator();
        if (!iterator.hasNext()) {
            throw new WebRenderingException("Given form contains empty fields");
        }

        StringBuilder formHtml = new StringBuilder();
        formHtml.append("<div class=\"container\">").append("<h2>").append(form.getDescription()).append("</h2>")
                .append("<form>").append("<div class=\"form-group\">");

        VisibleFieldHtmlRenderer visibleFieldHtmlRenderer = new VisibleFieldHtmlRenderer();
        for (Field<?> field : form.getFields()) {
            formHtml.append(visibleFieldHtmlRenderer.renderToHtml(field));
        }

        formHtml.append("<button type=\"submit\" class=\"btn btn-default\">").append("Submit").append("</button>");
        formHtml.append("</form>");
        formHtml.append("</div>");
        return formHtml.toString();
    }
}
