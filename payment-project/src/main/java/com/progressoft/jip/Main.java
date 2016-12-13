package com.progressoft.jip;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.menu.MenuRenderManger;

@Configurable
public class Main {

	private static final String APP_CONTEXT_LOCATION = "app.context.location";
	public static PaymentMenuContext context = new MenuContextImpl();
	public static MenuRenderManger<PaymentMenuContext> renderManger = new ConsoleMenuRender<PaymentMenuContext>(
			System.in, System.out, context);

	private final static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws FileNotFoundException {

		String appContextLocation = System.getProperty(APP_CONTEXT_LOCATION);
		FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext(appContextLocation);
		
		XmlFileUserInterfaceGenerator uiGenerator = appContext.getBean(XmlFileUserInterfaceGenerator.class);		
		context.put(MenuContext.MENU_RENDER_MANGER, renderManger);
		renderManger.renderMenu(uiGenerator.generateUi());
	}

	public Main() {

	}
}
