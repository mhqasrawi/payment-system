package com.progressoft.jip;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleMenuRender implements MenuRenderManger {

	private PrintStream printStream;
	private MenuContext menuContext = new MenuContextImpl();
	private Scanner scanner;
	private Menu mainMenu;

	public ConsoleMenuRender(InputStream is, PrintStream os) {
		printStream = os;
		this.scanner = new Scanner(is);
		menuContext.put(MenuContext.MENU_RENDER_MANGER, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.progressoft.jip.MenuRenderManger#renderMenu(com.progressoft.jip.Menu)
	 */
	@Override
	public void renderMenu(Menu menu) {
		this.mainMenu = menu;
		showMenu(menu);
	}

	private void showMenu(Menu menu) {
		doMenuAction(menu);
		if (menu.getSubMenu().size() != 0) {
			printDescreption(menu);
			showSubMenu(menu);
			while (!showChoosenSubMenu(menu, getEnteredOption()))
				;
		}else{
			showMenu(menuContext.popMenuStack());
		}
	}

	private void showSubMenu(Menu menu) {
		int optionCount = 1;
		for (Menu subMenu : menu.getSubMenu()) {
			printStream.println(String.format("	%d - %s", optionCount++, subMenu.getDescription()));
		}
		if (mainMenu != menu) {
			printStream.println(String.format("	%d - %s", optionCount++, "Back"));
		}
	}

	private void doMenuAction(Menu menu) {
		menu.getRelatedAction().doAction(menuContext);
	}

	private boolean showChoosenSubMenu(Menu menu, int subMenuNumber) {
		if (subMenuNumber - 1 > menu.getSubMenu().size()) {
			printStream.println("Invalid Input Please Try Again");
			return false;
		}
		if (subMenuNumber - 1 == menu.getSubMenu().size()) {
			showMenu(menuContext.popMenuStack());
			return false;
		}
		Menu subMenu = menu.getSubMenu().get(subMenuNumber - 1);
		menuContext.pushMenuStack(menu);
		showMenu(subMenu);
		return true;
	}

	private int getEnteredOption() {
		int subMenuNumber = scanner.nextInt();
		return subMenuNumber;
	}

	private void printDescreption(Menu menu) {
		printStream.println(menu.getDescription());
	}

	public void renderForm(Form form) {
		printStream.println(form.getDescription());
		printStream.println();
		for (Field<?> field : form.getFields()) {
			showField(field);
		}
	}

	private void showField(Field<?> field) {
		try {
			printStream.print(String.format("	%s  :", field.getDescription()));
			String lineFromConsole = "";
			while (lineFromConsole.length() < 2) {
				lineFromConsole = scanner.nextLine();
			}
			field.setValue(lineFromConsole);
		} catch (Exception ex) {
			ex.printStackTrace(printStream);
			showField(field);
		}
	}

}
