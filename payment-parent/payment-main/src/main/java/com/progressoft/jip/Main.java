package com.progressoft.jip;

import java.io.FileNotFoundException;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.menu.MenuRenderManger;

public class Main {

	private static final String APP_CONTEXT_LOCATION = "app.context.location";
	public static final PaymentMenuContext context = new MenuContextImpl();
	public static final MenuRenderManger<PaymentMenuContext> renderManger = new ConsoleMenuRender<>(
			System.in, System.out, context);

	private Main() {

	}

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		String appContextLocation = System.getProperty(APP_CONTEXT_LOCATION);
		FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext(appContextLocation);
		XmlFileUserInterfaceGenerator uiGenerator = appContext.getBean(XmlFileUserInterfaceGenerator.class);
		context.put(MenuContext.MENU_RENDER_MANGER, renderManger);
		renderManger.renderMenu(uiGenerator.generateUi());

	}

}
