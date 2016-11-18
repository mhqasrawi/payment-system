package com.progressoft.jip;

import java.util.Arrays;

import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.menu.MenuImpl;
import com.progressoft.jip.ui.menu.MenuRenderManger;

public class Main {
	public static PaymentMenuContext context = new MenuContextImpl();
	public static MenuRenderManger<PaymentMenuContext> renderManger = new ConsoleMenuRender<PaymentMenuContext>(
			System.in, System.out, context);

	public static void main(String[] args) {
		context.put(MenuContext.MENU_RENDER_MANGER, renderManger);
		MainMenu mainMenu = new MainMenu(Arrays.asList(Menus.PICKUP_ACCOUNT_MENU.build(), Menus.ADD_NEW_ACCOUNT_MENU.build(),
				new MenuImpl<PaymentMenuContext>("Exit", (c) -> {
					System.exit(0);
					return c;
				})));
		renderManger.renderMenu(mainMenu);
	}

}
