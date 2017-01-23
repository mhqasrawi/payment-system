package com.progressoft.jip.ui.web.form.parameter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.progressoft.jip.MenuContextImpl;
import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.form.FormImpl;
import com.progressoft.jip.ui.web.form.manger.DynamicProxySingleWebFormManger;
import com.progressoft.jip.ui.web.form.manger.NoSuchFieldException;
import com.progressoft.jip.ui.web.form.manger.SingleWebFormManger;

import junit.framework.Assert;

public class SingleWebFormMangerTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	
	private FormImpl<PaymentMenuContext, TestDto> form ;
	private SingleWebFormManger<PaymentMenuContext, TestDto> webFormManger;

	@Before
	public void setup() {
		form = new TestForm();
		webFormManger = new DynamicProxySingleWebFormManger<PaymentMenuContext, TestDto>(form);
	}

	@Test
	public void givenWebFormManger_WhenSubmitFieldValueCalled_ThenSetValueWillCall() {
		webFormManger.submitFieldValue(TestForm.FIELD_1, "Hi");
		Assert.assertEquals("Hi", form.getFieldByName(TestForm.FIELD_1).getValue());
	}

	@Test
	public void givenWebFormManger_WhenSubmitTwoFieldValue_ThenEachFieldWillSubmitWithProperValue() {
		webFormManger.submitFieldValue(TestForm.FIELD_1, "Hi1");
		webFormManger.submitFieldValue(TestForm.FIELD_2, "Hi2");
		Assert.assertEquals("Hi1", form.getFieldByName(TestForm.FIELD_1).getValue());
		Assert.assertEquals("Hi2", form.getFieldByName(TestForm.FIELD_2).getValue());
	}

	@Test
	public void givenWebFormManger_WhenSubmitFieldWithUnproperValue_ThenRuntimeExceptionWillThrown() {
		webFormManger.submitFieldValue(TestForm.FIELD_1, "Hi1");
		webFormManger.submitFieldValue(TestForm.FIELD_2, "Hi2");
		Assert.assertEquals("Hi1", form.getFieldByName(TestForm.FIELD_1).getValue());
		Assert.assertEquals("Hi2", form.getFieldByName(TestForm.FIELD_2).getValue());
		expectedException.expect(RuntimeException.class);
		webFormManger.submitFieldValue(TestForm.FIELD_3, "Hi3");
	}

	@Test
	public void givenWebFormManger_WhenSubmitFieldWithInvalidId_ThenNoSuchFieldExceptionWillThrown() {
		expectedException.expect(NoSuchFieldException.class);
		webFormManger.submitFieldValue("UnExistingField", "Hi");
	}

	@Test
	public void givenWebFormMangerWithTwoSubmittedFieldValue_WhenCallSubmitAction_ThenSubmitActionWillCallWithProperValue() {
		PaymentMenuContext menuContext = new MenuContextImpl();
		webFormManger.submitFieldValue(TestForm.FIELD_1, "Hi1");
		webFormManger.submitFieldValue(TestForm.FIELD_2, "Hi2");
		SubmitAction<PaymentMenuContext, TestDto> submitAction = new SubmitAction<PaymentMenuContext, TestDto>() {
			@Override
			public void submitAction(PaymentMenuContext paymentMenuContext, TestDto object) {
				Assert.assertEquals(menuContext, paymentMenuContext);
				Assert.assertEquals(object.getField1(), "Hi1");
				Assert.assertEquals(object.getField2(), "Hi2");
			}
		};
		form.addSubmitAction(submitAction);
		webFormManger.submitAction(menuContext);
	}
}
