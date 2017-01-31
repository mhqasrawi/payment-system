package com.progressoft.jip.ui.field;

public abstract class AbstractField<T> implements Field<T> {

    protected T value;
    private String name;
    private String description;

    @Override
    public String getName() {
        return name;
    }

    public AbstractField<T> setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public T getValue() {
        return value;
    }

    abstract public AbstractField<T> setValue(String value);

    @Override
    public String getDescription() {
        return description;
    }

    public AbstractField<T> setDescription(String description) {
        this.description = description;
        return this;
    }


}
