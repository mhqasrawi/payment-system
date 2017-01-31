package com.progressoft.jip.ui.web.form;

import com.progressoft.jip.ui.web.form.parameter.ParameterValueTuple;

/**
 * @author u612
 */
public class FormError {

    private final ParameterValueTuple parameterValueTuple;
    private final String message;

    public FormError(ParameterValueTuple parameterValueTuple, String message) {
        this.message = message;
        this.parameterValueTuple = parameterValueTuple;
    }

    public ParameterValueTuple getParameterValueTuple() {
        return parameterValueTuple;
    }

    public String getMessage() {
        return message;
    }

}
