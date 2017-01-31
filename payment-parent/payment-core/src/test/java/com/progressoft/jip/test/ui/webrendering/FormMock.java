package com.progressoft.jip.test.ui.webrendering;


import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.form.Form;

public class FormMock implements Form {

    private Iterable<Field<?>> fields;
    private String description;

    @Override
    public Iterable<Field<?>> getFields() {
        return fields;
    }

    public void setFields(Iterable<Field<?>> fields) {
        this.fields = fields;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Iterable<Field<?>> getAllFields() {
        return null;
    }

    @Override
    public SubmitAction getSubmitAction() {
        return null;
    }

    @Override
    public Class getClassType() {
        return null;
    }

}
