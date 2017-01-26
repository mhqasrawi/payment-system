package com.progressoft.jip.ui.web.form.parameter;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public final class CustomStringMatcher extends BaseMatcher<String> {

    private final String expectedValue;

    public CustomStringMatcher(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Override
    public void describeTo(Description arg0) {

    }

    @Override
    public boolean matches(Object arg0) {
        String value = (String) arg0;
        return value.startsWith(expectedValue);
    }
}