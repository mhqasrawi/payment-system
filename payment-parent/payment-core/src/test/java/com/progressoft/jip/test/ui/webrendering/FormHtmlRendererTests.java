package com.progressoft.jip.test.ui.webrendering;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.webrendering.HtmlRendererException;
import com.progressoft.jip.ui.webrendering.impl.FormHtmlRenderer;

public class FormHtmlRendererTests {
	private static final String EXPECTED_FORM_HTML = "<div class=\"container\"><h2>form description</h2><form><div class=\"form-group\"><div class=\"form-group\"><label for=\"field-id-1\">field description 1</label><input type=\"text\" class=\"form-control\" id=\"field-id-1\"></div><div class=\"form-group\"><label for=\"field-id-2\">field description 2</label><input type=\"text\" class=\"form-control\" id=\"field-id-2\"></div><button type=\"submit\" class=\"btn btn-default\">Submit</button></form></div>";

	@Test
	public void canCreateNewFormHtmlRenderer() {
		new FormHtmlRenderer(new FormMock());
	}

	@Test(expected = HtmlRendererException.class)
	public void givenFormWithNullFieldsThenRendererShouldThrowException() throws Exception {
		FormMock formMock = new FormMock();
		formMock.setFields(null);
		new FormHtmlRenderer(formMock).renderToHtml();
	}

	@Test(expected = HtmlRendererException.class)
	public void givenFormWithEmptyFieldsThenRendererShouldThrowException() throws Exception {
		FormMock formMock = new FormMock();
		formMock.setFields(new ArrayList<Field<?>>());
		new FormHtmlRenderer(formMock).renderToHtml();
	}

	@Test
	public void givenFormThenShouldReturnCorrectHtml() {
		List<Field<?>> fields = new ArrayList<>();

		FieldMock<String> fieldMock1 = new FieldMock<>();
		fieldMock1.setDescription("field description 1");
		fieldMock1.setName("field-id-1");
		fields.add(fieldMock1);

		FieldMock<String> fieldMock2 = new FieldMock<>();
		fieldMock2.setDescription("field description 2");
		fieldMock2.setName("field-id-2");
		fields.add(fieldMock2);

		FormMock formMock = new FormMock();
		formMock.setDescription("form description");

		formMock.setFields(fields);
		System.out.println(new FormHtmlRenderer(formMock).renderToHtml());
		assertEquals(EXPECTED_FORM_HTML, new FormHtmlRenderer(formMock).renderToHtml());
	}

}
