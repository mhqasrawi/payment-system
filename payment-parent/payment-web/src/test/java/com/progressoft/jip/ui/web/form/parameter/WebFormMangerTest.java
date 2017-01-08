package com.progressoft.jip.ui.web.form.parameter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.progressoft.jip.MenuContextImpl;
import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.field.IntegerField;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.FormImpl;
import com.progressoft.jip.ui.web.form.manger.DynamicProxySingleWebFormManger;
import com.progressoft.jip.ui.web.form.manger.NoSuchFieldException;
import com.progressoft.jip.ui.web.form.manger.SingleWebFormManger;

import junit.framework.Assert;

public class WebFormMangerTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static final String FIELD_1 = "field1";
	private static final String FIELD_2 = "field2";
	private static final String FIELD_3 = "field3";
	private static final String DESCRIPTION = "Description";
	private static final String FORM_ID = "Form ID";

	private Field<String> firstField;
	private Field<String> secoundField;
	private Field<Integer> thirdIntegerField;
	private FormImpl<PaymentMenuContext, TestDto> form;
	private SingleWebFormManger<PaymentMenuContext, TestDto> webFormManger;

	@Before
	public void setup() {
		firstField = new StringField().setDescription(DESCRIPTION).setName(FIELD_1);
		secoundField = new StringField().setDescription(DESCRIPTION).setName(FIELD_2);
		thirdIntegerField = new IntegerField().setDescription(DESCRIPTION).setName(FIELD_3);
		form = new FormImpl<PaymentMenuContext, TestDto>(FORM_ID) {
			public Class<TestDto> getClassType() {
				return TestDto.class;
			};
		}.addField(firstField).addField(secoundField).addField(thirdIntegerField);
		webFormManger = new DynamicProxySingleWebFormManger<PaymentMenuContext, TestDto>(form);
	}

	@Test
	public void givenWebFormManger_WhenSubmitFieldValueCalled_ThenSetValueWillCall() {
		webFormManger.submitFieldValue(FIELD_1, "Hi");
		Assert.assertEquals("Hi", form.getFieldByName(FIELD_1).getValue());
	}

	@Test
	public void givenWebFormManger_WhenSubmitTwoFieldValue_ThenEachFieldWillSubmitWithProperValue() {
		webFormManger.submitFieldValue(FIELD_1, "Hi1");
		webFormManger.submitFieldValue(FIELD_2, "Hi2");
		Assert.assertEquals("Hi1", form.getFieldByName(FIELD_1).getValue());
		Assert.assertEquals("Hi2", form.getFieldByName(FIELD_2).getValue());
	}

	@Test
	public void givenWebFormManger_WhenSubmitFieldWithUnproperValue_ThenRuntimeExceptionWillThrown() {
		webFormManger.submitFieldValue(FIELD_1, "Hi1");
		webFormManger.submitFieldValue(FIELD_2, "Hi2");
		Assert.assertEquals("Hi1", form.getFieldByName(FIELD_1).getValue());
		Assert.assertEquals("Hi2", form.getFieldByName(FIELD_2).getValue());
		expectedException.expect(RuntimeException.class);
		webFormManger.submitFieldValue(FIELD_3, "Hi3");
	}

	@Test
	public void givenWebFormManger_WhenSubmitFieldWithInvalidId_ThenNoSuchFieldExceptionWillThrown() {
		expectedException.expect(NoSuchFieldException.class);
		webFormManger.submitFieldValue("UnExistingField", "Hi");
	}

	@Test
	public void givenWebFormMangerWithTwoSubmittedFieldValue_WhenCallSubmitAction_ThenSubmitActionWillCallWithProperValue() {
		PaymentMenuContext menuContext = new MenuContextImpl();
		webFormManger.submitFieldValue(FIELD_1, "Hi1");
		webFormManger.submitFieldValue(FIELD_2, "Hi2");
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
