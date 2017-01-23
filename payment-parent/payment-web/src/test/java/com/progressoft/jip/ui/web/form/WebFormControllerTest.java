package com.progressoft.jip.ui.web.form;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.form.FormImpl;
import com.progressoft.jip.ui.web.form.manger.DynamicProxySingleWebFormManger;
import com.progressoft.jip.ui.web.form.manger.SingleWebFormManger;
import com.progressoft.jip.ui.web.form.parameter.ParameterParser;
import com.progressoft.jip.ui.web.form.parameter.StreamParameterParser;
import com.progressoft.jip.ui.web.form.parameter.TestDto;
import com.progressoft.jip.ui.web.form.parameter.TestForm;

@RunWith(Enclosed.class)
public class WebFormControllerTest {

	private static final String PARAMETER_1 = "parameter_1";
	private static final String VALUE_1 = "value_1";
	private static final String VALUE_2 = "value_2";
	private static final String PARAMETER_2 = "parameter_2";;

	private static WebFormController<TestDto> webFormController;
	private static SingleWebFormManger<PaymentMenuContext, TestDto> singleWebFormManger;
	private static ParameterParser parameterParser;

	@Ignore
	abstract static class MockedSingleWebFormManger {

		@Before
		public void setup() {
			parameterParser = new StreamParameterParser();
			singleWebFormManger = mock(SingleWebFormManger.class);
			webFormController = new WebFormController(parameterParser, singleWebFormManger);
		}

	}

	public static class TestDummyWebFormManger extends MockedSingleWebFormManger {

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

	@Ignore
	abstract public static class ReadImplementationSingleWebFormManger {

		@Before
		public void setup() {
			parameterParser = new StreamParameterParser();
			singleWebFormManger = new DynamicProxySingleWebFormManger(new TestForm());
			webFormController = new WebFormController(parameterParser, singleWebFormManger);
		}
	}

	public static class TestRealImplementation extends ReadImplementationSingleWebFormManger {

		@Test
		public void whenCallPRocessStreamWithInvalidValueheCallSingleFormSetValueMethodShouldThrowException()
				throws Exception {
			InputStream is = new ByteArrayInputStream((TestForm.FIELD_3 + "=" + "AAAA").getBytes());
			Iterable<FormError> errors = webFormController.process(is);
			Iterator<FormError> iterator = errors.iterator();
			assertTrue(iterator.hasNext());
			iterator.next();
			assertFalse(iterator.hasNext());
		}
	}

	public static class NormalTestCase {
		{
			webFormController = new WebFormController(parameterParser, singleWebFormManger);
		}

		@Test
		public void whenCallAttachForm_ThenInitMethodWillCallAndImplementationProviderWillCalledToInjectObjectDependency() {
			AtomicBoolean isInitMethodCalled = new AtomicBoolean(false);
			ImplementationProvider implementationProvider = Mockito.mock(ImplementationProvider.class);
			Form<PaymentMenuContext, ?> form = new FormImpl<PaymentMenuContext, TestDto>(null) {

				@Override
				public Class<TestDto> getClassType() {
					return TestDto.class;
				}

				public void init() {
					isInitMethodCalled.set(true);
				}

			};
			webFormController.attachForm(implementationProvider, form);
			verify(implementationProvider, times(1)).injectObjectDependency(form);
			Assert.assertTrue("Init Method Didn't Invoke", isInitMethodCalled.get());
		}
	}
}
