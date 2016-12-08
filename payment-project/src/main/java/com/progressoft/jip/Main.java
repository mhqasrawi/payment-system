package com.progressoft.jip;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.menu.MenuImpl;
import com.progressoft.jip.ui.menu.MenuRenderManger;

public class Main {

	private static final String APP_CONTEXT_LOCATION = "app.context.location";
	public static PaymentMenuContext context = new MenuContextImpl();
	public static MenuRenderManger<PaymentMenuContext> renderManger = new ConsoleMenuRender<PaymentMenuContext>(
			System.in, System.out, context);

	private final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {

		String appContextLocation = System.getProperty(APP_CONTEXT_LOCATION);
		FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext(appContextLocation);
		Main bean = appContext.getBean(Main.class);

		context.put(MenuContext.MENU_RENDER_MANGER, renderManger);
		MenusDefenation bean2 = appContext.getBean(MenusDefenation.class);
		MainMenu mainMenu = new MainMenu(Arrays.asList(bean2.getPICKUP_ACCOUNT_MENU(), bean2.getADD_NEW_ACCOUNT_MENU(),
				new MenuImpl<PaymentMenuContext>("Exit", (c) -> {
					System.exit(0);
					return c;
				})));

		renderManger.renderMenu(mainMenu);
	}

	
}
