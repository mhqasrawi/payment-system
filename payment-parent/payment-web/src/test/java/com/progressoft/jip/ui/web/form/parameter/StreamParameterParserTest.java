package com.progressoft.jip.ui.web.form.parameter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import junit.framework.Assert;

public class StreamParameterParserTest {

	private static final String PARAMETER_1 = "parameter_1";
	private static final String VALUE_1 = "value_1";
	private static final String VALUE_2 = "value_2";
	private static final String PARAMETER_2 = "parameter_2";;
	
	
	
	private ParameterParser parameterParser;
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setup() {
		parameterParser = new StreamParameterParser();
	}

	@Test
	public void whenPassNullStream_ThenThrowParameterParserExceptionWithNullParameterMessage() {
		InputStream is = null;
		expectedException.expect(ParameterParserInvalidParameterException.class);
		expectedException.expectMessage("null input stream");
		parameterParser.getParameters(is);
	}

	public void whenPassEmptyStream_ThenReturnEmptyIterableParameterValueTuple() {
		InputStream is = new ByteArrayInputStream("".getBytes());
		Iterable<ParameterValueTuple> parameterValueTuples = parameterParser.getParameters(is);
		Assert.assertFalse(parameterValueTuples.iterator().hasNext());
	}

	@Test
	public void whenPassStreamWithOneParameter_ThenReturnParameter() {
		InputStream is = new ByteArrayInputStream("parameter_1=value_1".getBytes());
		Iterable<ParameterValueTuple> parameters = parameterParser.getParameters(is);
		Iterator<ParameterValueTuple> parameterIterator = parameters.iterator();
		Assert.assertTrue(parameterIterator.hasNext());
		ParameterValueTuple parameterValueTuple = parameterIterator.next();
		Assert.assertEquals(new ParameterValueTuple(PARAMETER_1, VALUE_1), parameterValueTuple);
		Assert.assertFalse(parameterIterator.hasNext());
	}

	@Test
	public void whenPassStreamWithTwoParameter_ThenReturnTwoParameter() {
		InputStream is = new ByteArrayInputStream(
				(PARAMETER_1 + "=" + VALUE_1 + "&" + PARAMETER_2 + "=" + VALUE_2).getBytes());
		Iterable<ParameterValueTuple> parameters = parameterParser.getParameters(is);
		Iterator<ParameterValueTuple> parameterIterator = parameters.iterator();
		Assert.assertTrue(parameterIterator.hasNext());
		ParameterValueTuple firstParameterValueTuple = parameterIterator.next();
		Assert.assertEquals(new ParameterValueTuple(PARAMETER_1, VALUE_1), firstParameterValueTuple);
		Assert.assertTrue(parameterIterator.hasNext());
		ParameterValueTuple secoundParameterValueTuple = parameterIterator.next();
		Assert.assertEquals(new ParameterValueTuple(PARAMETER_2, VALUE_2), secoundParameterValueTuple);
		Assert.assertFalse(parameterIterator.hasNext());
	}

	@Test
	public void whenPassInvalidStreamContent_ThenThrowParameterParserExceptionWithInvalidParameterFormat() {
		InputStream is = new ByteArrayInputStream(PARAMETER_1.getBytes());
		expectedException.expect(ParameterParserInvalidParameterException.class);
		expectedException.expectMessage(new CustomStringMatcher("parameter contain invalid format content,"
				+ " content must satisfy parameter_1=value_1&parameter_2=value_2"));
		parameterParser.getParameters(is);
	}

}
