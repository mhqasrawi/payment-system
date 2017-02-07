package com.progressoft.jip.watchers;

import java.nio.file.Path;

import com.progressoft.jip.parser.CsvFileProcessor;

public class EventImpl implements Event<Path> {

	private CsvFileProcessor processor;

	public void setProcessor(CsvFileProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void handleEvent(Path path) {
		processor.process(path);
	}

}
