package com.progressoft.jip.ui.web.form;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.web.form.manger.SingleWebFormManger;
import com.progressoft.jip.ui.web.form.parameter.ParameterParser;
import com.progressoft.jip.ui.web.form.parameter.ParameterValueTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WebFormController<T> {

    private static final String INIT = "init";
    private final Logger logger = LoggerFactory.getLogger(WebFormController.class);
    private final ParameterParser parameterParser;
    private final SingleWebFormManger<PaymentMenuContext, T> single;

    public WebFormController(ParameterParser parameterParser, SingleWebFormManger<PaymentMenuContext, T> single) {
        this.parameterParser = parameterParser;
        this.single = single;
    }

    public void attachForm(ImplementationProvider implementationProvider, Form<PaymentMenuContext, ?> form) {
        implementationProvider.injectObjectDependency(form);
        callInitMethod(form);
    }

    private void callInitMethod(Form<PaymentMenuContext, ?> form) {
        Method method;
        try {
            logger.trace(" try to call init method for form : {} ", form);
            method = form.getClass().getMethod(INIT);
            method.invoke(form);
            logger.trace(" init method called for form : {} ", form);
        } catch (Exception e) {
            logger.error("Error while call init method for form ", e);
        }
    }

    public Iterable<FormError> process(InputStream is) {
        List<FormError> formErros = new ArrayList<>();
        Iterator<ParameterValueTuple> iterator = parameterParser.getParameters(is).iterator();
        while (iterator.hasNext()) {
            assignParameter(formErros, iterator.next());
        }
        return formErros;
    }

    private void assignParameter(List<FormError> formErros, ParameterValueTuple parameterValueTuple) {
        try {
            single.submitFieldValue(parameterValueTuple.getParameterName(), parameterValueTuple.getValue());
        } catch (RuntimeException ex) {
            formErros.add(new FormError(parameterValueTuple, ex.getMessage()));
            logger.error(ex.getMessage(), ex);
        }
    }

}
