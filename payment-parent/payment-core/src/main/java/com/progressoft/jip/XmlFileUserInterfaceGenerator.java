package com.progressoft.jip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.inject.Inject;

import com.progressoft.jip.configuration.Configuration;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.xml.ActionGenerator;
import com.progressoft.jip.ui.xml.UserInterfaceGenerator;
import com.progressoft.jip.ui.xml.XmlUserInterfaceGenerator;

/**
 * @author Ahmad.Jardat
 *
 */
public class XmlFileUserInterfaceGenerator {

	private static final String UI_XML_PATH = "ui.xml.path";

	@Inject
	private Configuration configuration;
	
	@Inject
	private ActionGenerator actionGenerator;

	private UserInterfaceGenerator<PaymentMenuContext> uiGenerator;

	public Menu<PaymentMenuContext> generateUi() throws FileNotFoundException {
		String filePath = configuration.getProperty(UI_XML_PATH);
		uiGenerator = new XmlUserInterfaceGenerator<>(new FileInputStream(new File(filePath)),actionGenerator);
		return uiGenerator.generateUserInterface();
	}
}
