package com.progressoft.jip.ui.web.form;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.ui.web.form.manger.SingleWebFormManger;
import com.progressoft.jip.ui.web.form.parameter.ParameterParser;
import com.progressoft.jip.ui.web.form.parameter.StreamParameterParser;
import com.progressoft.jip.ui.web.form.parameter.TestDto;

public class WebFormControllerTest {
	
	private static final String PARAMETER_1 = "parameter_1";
	private static final String VALUE_1 = "value_1";
	private static final String VALUE_2 = "value_2";
	private static final String PARAMETER_2 = "parameter_2";;
	

	private WebFormController<TestDto> webFormController;
	private SingleWebFormManger<PaymentMenuContext, TestDto> singleWebFormManger;
	private ParameterParser parameterParser;

	@Before
	public void init() {
		parameterParser = new StreamParameterParser(); 
		singleWebFormManger = mock(SingleWebFormManger.class);
		webFormController = new WebFormController(parameterParser, singleWebFormManger);
	}

	@Test
	public void whenCallProcessStreamWithOneParameterThenCallSingleFormSetValueMethod() {
		InputStream is = new ByteArrayInputStream("parameter_1=value_1".getBytes());
		webFormController.process(is);
		verify(singleWebFormManger, times(1)).submitFieldValue("parameter_1", "value_1");
	}
	
	@Test
	public void whenCallProcessStreamWithTwoParameterThenCallSingleFormSetValueMethod() {
		InputStream is = new ByteArrayInputStream(
				(PARAMETER_1 + "=" + VALUE_1 + "&" + PARAMETER_2 + "=" + VALUE_2).getBytes());
		webFormController.process(is);
		verify(singleWebFormManger, times(1)).submitFieldValue(PARAMETER_1, VALUE_1);
		verify(singleWebFormManger, times(1)).submitFieldValue(PARAMETER_2, VALUE_2);
	}

	
}
