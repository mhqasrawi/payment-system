package com.progressoft.jip.test.ui.webrendering;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.progressoft.jip.ui.webrendering.HtmlRendererException;
import com.progressoft.jip.ui.webrendering.impl.VisibleFieldHtmlRenderer;

public class VisibleFieldHtmlRendererTests {
	private static final String EXPECTED_FIELD_HTML = "<div class=\"form-group\"><label for=\"field-id\">field description</label><input type=\"text\" class=\"form-control\" id=\"field-id\"></div>";

	@Test
	public void canCreateNewVisibleFieldHtmlRenderer() throws Exception {
		new VisibleFieldHtmlRenderer(new FieldMock<>());
	}
	
	@Test(expected = HtmlRendererException.class)
	public void givenNullFieldNameThenRendererShouldThrowException() {
		FieldMock<String> fieldMock = new FieldMock<>();
		fieldMock.setDescription("field description");
		fieldMock.setName(null);
		new VisibleFieldHtmlRenderer(fieldMock).renderToHtml();
	}
	
	@Test(expected = HtmlRendererException.class)
	public void givenEmptyFieldNameThenRendererShouldThrowException() {
		FieldMock<String> fieldMock = new FieldMock<>();
		fieldMock.setDescription("field description");
		fieldMock.setName(" ");
		new VisibleFieldHtmlRenderer(fieldMock).renderToHtml();
	}


	@Test
	public void givenFieldThenRendererShouldReturnCorrectHtml() {
		FieldMock<String> fieldMock = new FieldMock<>();
		fieldMock.setDescription("field description");
		fieldMock.setName("field-id");
		assertEquals(EXPECTED_FIELD_HTML, new VisibleFieldHtmlRenderer(fieldMock).renderToHtml());
	}
}
