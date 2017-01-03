package com.progressoft.jip.test.ui.webrendering;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.progressoft.jip.ui.webrendering.WebRenderingException;
import com.progressoft.jip.ui.webrendering.field.impl.VisibleFieldHtmlRenderer;

public class VisibleFieldHtmlRendererTests {
	
	private static final String EXPECTED_FIELD_HTML = "<div class=\"form-group\"><label for=\"field-id\">field description</label><input type=\"text\" class=\"form-control\" id=\"field-id\"></div>";
	private VisibleFieldHtmlRenderer visibleFieldHtmlRenderer = new VisibleFieldHtmlRenderer();
	
	@Test(expected = WebRenderingException.class)
	public void givenNullFieldNameThenRendererShouldThrowException() {
		FakeField<String> fakeField = new FakeField<>();
		fakeField.setDescription("field description");
		fakeField.setName(null);
		visibleFieldHtmlRenderer.renderToHtml(fakeField);
	}
	
	@Test(expected = WebRenderingException.class)
	public void givenEmptyFieldNameThenRendererShouldThrowException() {
		FakeField<String> fakeField = new FakeField<>();
		fakeField.setDescription("field description");
		fakeField.setName(" ");
		visibleFieldHtmlRenderer.renderToHtml(fakeField);
	}


	@Test
	public void givenFieldThenRendererShouldReturnCorrectHtml() {
		FakeField<String> fakeField = new FakeField<>();
		fakeField.setDescription("field description");
		fakeField.setName("field-id");
		assertEquals(EXPECTED_FIELD_HTML, visibleFieldHtmlRenderer.renderToHtml(fakeField));
	}
}
