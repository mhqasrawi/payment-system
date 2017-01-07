package com.progressoft.jip.test.ui.webrendering;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.webrendering.WebRenderingException;
import com.progressoft.jip.ui.webrendering.form.impl.FormHtmlRenderer;

public class FormHtmlRendererTests {

	private static final String EXPECTED_FORM_HTML = "<div class=\"container\"><h2>form description</h2><form><div class=\"form-group\"><div class=\"form-group\"><label for=\"field-id-1\">field description 1</label><input type=\"text\" class=\"form-control\" name=\"field-id-1\"></div><div class=\"form-group\"><label for=\"field-id-2\">field description 2</label><input type=\"text\" class=\"form-control\" name=\"field-id-2\"></div><button type=\"submit\" class=\"btn btn-default\">Submit</button></form></div>";
	private FormHtmlRenderer formHtmlRenderer = new FormHtmlRenderer();
	
	@Test(expected = WebRenderingException.class)
	public void givenFormWithNullFieldsThenRendererShouldThrowException() throws Exception {
		FakeForm fakeForm = new FakeForm();
		fakeForm.setFields(null);
		formHtmlRenderer.renderToHtml(fakeForm);
	}

	@Test(expected = WebRenderingException.class)
	public void givenFormWithEmptyFieldsThenRendererShouldThrowException() throws Exception {
		FakeForm fakeForm = new FakeForm();
		fakeForm.setFields(new ArrayList<Field<?>>());
		formHtmlRenderer.renderToHtml(fakeForm);
	}

	@Test
	public void givenFormThenShouldReturnCorrectHtml() {
		List<Field<?>> fields = new ArrayList<>();

		FakeField<String> fakeField = new FakeField<>();
		fakeField.setDescription("field description 1");
		fakeField.setName("field-id-1");
		fields.add(fakeField);

		FakeField<String> fakeField2 = new FakeField<>();
		fakeField2.setDescription("field description 2");
		fakeField2.setName("field-id-2");
		fields.add(fakeField2);

		FakeForm fakeForm = new FakeForm();
		fakeForm.setDescription("form description");
		fakeForm.setFields(fields);
		assertEquals(EXPECTED_FORM_HTML, formHtmlRenderer.renderToHtml(fakeForm));
	}

}
