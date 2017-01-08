package com.progressoft.jip.ui.web.form;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.ui.web.form.manger.SingleWebFormManger;
import com.progressoft.jip.ui.web.form.parameter.ParameterParser;

public class WebFormController<T> {
	
	private final ParameterParser parameterParser;
	private final SingleWebFormManger<PaymentMenuContext,T> single;

	public WebFormController(ParameterParser parameterParser, SingleWebFormManger<PaymentMenuContext, T> single) {
		this.parameterParser = parameterParser;
		this.single = single;
	}

	
}
