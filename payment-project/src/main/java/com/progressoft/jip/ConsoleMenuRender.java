package com.progressoft.jip;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleMenuRender implements MenuRenderManger {

	private Scanner scanner;
	private PrintStream printStream;
	
	public ConsoleMenuRender(InputStream is,PrintStream os) {
		scanner = new Scanner(is);
		printStream =os;
	}

	/* (non-Javadoc)
	 * @see com.progressoft.jip.MenuRenderManger#renderMenu(com.progressoft.jip.Menu)
	 */
	@Override
	public void renderMenu(Menu menu) {
		menu.getRelatedAction().doAction(null);
		if (menu.getSubMenu().size() != 0) {
			printStream.println(menu.getDescription());
			int optionCount = 1;
			for (Menu subMenu : menu.getSubMenu()) {
				printStream.println(String.format("	%d - %s", optionCount++, subMenu.getDescription()));
			}
			int subMenuNumber = scanner.nextInt();
			Menu subMenu = menu.getSubMenu().get(subMenuNumber - 1);
			renderMenu(subMenu);
		}
	}


	public void renderForm(Form form) {
		printStream.println(form.getDescription());
		printStream.println();
		for (Field<?> field : form.getFields()) {
			printStream.print(String.format("	%s  :", field.getDescription()));
			String lineFromConsole = "";
			while (lineFromConsole.length() == 0) {
				lineFromConsole = scanner.nextLine();
			}
			field.setValue(lineFromConsole);
		}
	}

}
