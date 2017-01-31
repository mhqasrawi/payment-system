package com.progressoft.jip.ui.webrendering.field.impl;

import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.webrendering.WebRenderingException;
import com.progressoft.jip.ui.webrendering.field.FieldRenderer;

import java.util.Objects;

public class VisibleFieldHtmlRenderer implements FieldRenderer {

    @Override
    public String renderToHtml(Field<?> field) {
        if (Objects.isNull(field.getName())) {
            throw new WebRenderingException("Given field has a null name");
        }
        if ("".equals(field.getName().trim())) {
            throw new WebRenderingException("Given field has an empty name");
        }
        return new StringBuilder().append("<div class=\"form-group\">").append("<label for=\"").append(field.getName())
                .append("\">").append(field.getDescription()).append("</label>")
                .append("<input type=\"text\" class=\"form-control\" name=\"").append(field.getName()).append("\">")
                .append("</div>").toString();
    }
}
