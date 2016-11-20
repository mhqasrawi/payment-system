package com.progressoft.jip.payment.iban.impl;

import java.io.IOException;
import java.util.Iterator;

public interface IBANFormatsReader {
	Iterator<IBANPattern> readAll() throws IOException;
}
