package com.progressoft.jip.ui.web.form.parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StreamParameterParser implements ParameterParser {

    private static final int PARAMETER_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String EQUAL_REGEX = "=";
    private static final String AND_REGEX = "&";

    @Override
    public Iterable<ParameterValueTuple> getParameters(InputStream is) {
        validateArgument(is);
        List<ParameterValueTuple> parametersValuesTuples = parseStream(is);
        return parametersValuesTuples;
    }

    private List<ParameterValueTuple> parseStream(InputStream is) {
        List<ParameterValueTuple> parametersValuesTuples = new ArrayList<>();
        for (String parameterAndValue : splitParameterAndValue(removeLineSeperater(getBufferReader(is)))) {
            parametersValuesTuples.add(parseParameterAndValue(parameterAndValue));
        }
        return parametersValuesTuples;
    }

    private ParameterValueTuple parseParameterAndValue(String parameterAndValue) {
        String[] splitedParameterAndValue = parameterAndValue.split(EQUAL_REGEX);
        validateParameterAndValueFormat(parameterAndValue, splitedParameterAndValue);
        String parameter = splitedParameterAndValue[PARAMETER_INDEX];
        String value = splitedParameterAndValue[VALUE_INDEX];
        ParameterValueTuple parameterValueTuple = new ParameterValueTuple(parameter, value);
        return parameterValueTuple;
    }

    private void validateParameterAndValueFormat(String parameterAndValue, String[] splitedParameterAndValue) {
        if (splitedParameterAndValue.length != 2) {
            throw new ParameterParserInvalidParameterException(
                    String.format("%s %s %s", "parameter contain invalid format content, content must satisfy "
                            + "parameter_1=value_1&parameter_2=value_2", "while the line content is ", parameterAndValue));
        }
    }

    private String[] splitParameterAndValue(String bufferContent) {
        String[] parametersAndValues = bufferContent.split(AND_REGEX);
        return parametersAndValues;
    }

    private String removeLineSeperater(BufferedReader bufferedReader) {
        String bufferContent = "";
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                bufferContent = bufferContent.concat(line);
            }
        } catch (IOException e) {
            throw new ParameterParserInvalidParameterException("error while read is content ", e);
        }
        return bufferContent;
    }

    private BufferedReader getBufferReader(InputStream is) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        return bufferedReader;
    }

    private void validateArgument(InputStream is) {
        if (Objects.isNull(is))
            throw new ParameterParserInvalidParameterException("null input stream");

    }

}
