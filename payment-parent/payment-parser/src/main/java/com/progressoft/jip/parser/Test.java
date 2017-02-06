package com.progressoft.jip.parser;

import java.nio.file.Paths;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/application-context.xml");
		CsvFileProcessor csvFileProcessor = context.getBean(CsvFileProcessor.class);
		csvFileProcessor.process(Paths.get("C:\\Users\\trn162\\Desktop\\New folder (2)\\payment.csv"));
		System.out.println("done");
	}

}
