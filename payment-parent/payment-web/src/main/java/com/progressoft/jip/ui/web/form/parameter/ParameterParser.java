package com.progressoft.jip.ui.web.form.parameter;

import java.io.InputStream;

public interface ParameterParser {

    Iterable<ParameterValueTuple> getParameters(InputStream is);

}