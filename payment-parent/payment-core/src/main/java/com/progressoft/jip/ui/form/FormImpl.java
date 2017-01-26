package com.progressoft.jip.ui.form;

import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.menu.MenuContext;

import java.util.ArrayList;
import java.util.List;

public abstract class FormImpl<C extends MenuContext, T> implements Form<C, T> {

    private List<Field<?>> fields = new ArrayList<>();
    private List<Field<?>> hiddenFields = new ArrayList<>();
    private SubmitAction<C, T> submitAction = (c, t) -> {
        return;
    };
    private String description = "";

    public FormImpl(String description) {
        this.description = description;
    }

    @Override
    public Iterable<Field<?>> getFields() {
        return fields;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public FormImpl<C, T> addField(Field<?> field) {
        fields.add(field);
        return this;
    }

    public FormImpl<C, T> addHiddenField(Field<?> field) {
        hiddenFields.add(field);
        return this;
    }

    @Override
    public Iterable<Field<?>> getAllFields() {
        List<Field<?>> allFields = new ArrayList<>();
        allFields.addAll(fields);
        allFields.addAll(hiddenFields);
        return allFields;
    }

    public void addSubmitAction(SubmitAction<C, T> submitAction) {
        this.submitAction = submitAction;
    }

    @Override
    public SubmitAction<C, T> getSubmitAction() {
        return submitAction;
    }

    public abstract Class<T> getClassType();
}
