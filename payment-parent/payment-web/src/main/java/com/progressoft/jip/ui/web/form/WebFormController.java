package com.progressoft.jip.ui.web.form;

import java.io.InputStream;
import java.util.Iterator;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.ui.web.form.manger.SingleWebFormManger;
import com.progressoft.jip.ui.web.form.parameter.ParameterParser;
import com.progressoft.jip.ui.web.form.parameter.ParameterValueTuple;

public class WebFormController<T> {
	
	private final ParameterParser parameterParser;
	private final SingleWebFormManger<PaymentMenuContext,T> single;

	public WebFormController(ParameterParser parameterParser, SingleWebFormManger<PaymentMenuContext, T> single) {
		this.parameterParser = parameterParser;
		this.single = single;
	}

	public void process(InputStream is) {
		Iterable<ParameterValueTuple> parameters = parameterParser.getParameters(is);
		Iterator<ParameterValueTuple> iterator = parameters.iterator();
		while(iterator.hasNext()){
			ParameterValueTuple next = iterator.next();
			single.submitFieldValue(next.getParameterName(), next.getValue());
		}
	}

	
}
