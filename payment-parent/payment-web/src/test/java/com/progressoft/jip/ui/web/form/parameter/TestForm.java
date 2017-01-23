package com.progressoft.jip.ui.web.form.parameter;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.field.IntegerField;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.FormImpl;

public class TestForm extends FormImpl<PaymentMenuContext, TestDto> {

	public static final String FIELD_1 = "field1";
	public static final String FIELD_2 = "field2";
	public static final String FIELD_3 = "field3";
	public static final String DESCRIPTION = "Description";

	private Field<String> firstField;
	private Field<String> secoundField;
	private Field<Integer> thirdIntegerField;

	public TestForm() {
		super("Test Form");
		init();
	}

	public void init() {
		firstField = new StringField().setDescription(DESCRIPTION).setName(FIELD_1);
		secoundField = new StringField().setDescription(DESCRIPTION).setName(FIELD_2);
		thirdIntegerField = new IntegerField().setDescription(DESCRIPTION).setName(FIELD_3);
		addField(firstField).addField(secoundField).addField(thirdIntegerField);
	}

	@Override
	public Class<TestDto> getClassType() {
		return TestDto.class;
	}

}
