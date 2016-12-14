package com.progressoft.jip.ui.field;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author u612
 *
 */
public class PathField extends AbstractField<Path> {

	@Override
	public AbstractField<Path> setValue(String value) {
		this.value = Paths.get(value);
		return this;
	}

}
