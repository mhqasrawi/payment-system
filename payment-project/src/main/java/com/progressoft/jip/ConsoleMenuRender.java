package com.progressoft.jip;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.menu.MenuRenderManger;

public class ConsoleMenuRender<T extends MenuContext> implements MenuRenderManger<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleMenuRender.class);
	
	private PrintStream printStream;
	private T menuContext;
	private Scanner scanner;
	private Menu<T> mainMenu;

	public ConsoleMenuRender(InputStream is, PrintStream os, T menuContext) {
		printStream = os;
		this.scanner = new Scanner(is);
		this.menuContext = menuContext;
		menuContext.put(MenuContext.MENU_RENDER_MANGER, this);
	}

	@Override
	public void renderMenu(Menu<T> menu) {
		this.mainMenu = menu;
		showMenu(menu);
	}

	private void showMenu(Menu<T> menu) {
		showMenu(menu, false);
	}

	@SuppressWarnings("unchecked")
	private void showMenu(Menu<T> menu, boolean skipAction) {
		try {
			if (!skipAction) {
				doMenuAction(menu);
			}
			if (menu.getSubMenu().size() != 0) {
				printDescreption(menu);
				showSubMenu(menu);
				while (!showChoosenSubMenu(menu, getEnteredOption()))
					;
			} else {
				showMenu(menuContext.popMenuStack(), true);
			}
		} catch (RuntimeException exception) {
			LOGGER.error(exception.getMessage(),exception);
			printStream.println(exception.getMessage());
			showMenu(menuContext.popMenuStack(), true);
		}
	}

	private void showSubMenu(Menu<T> menu) {
		int optionCount = 1;
		for (Menu<T> subMenu : menu.getSubMenu()) {
			printStream.println(String.format("	%d - %s", optionCount++, subMenu.getDescription()));
		}
		if (mainMenu != menu) {
			printStream.println(String.format("	%d - %s", optionCount++, "Back"));
		}
	}

	private void doMenuAction(Menu<T> menu) {
		menu.doAction(menuContext);
	}

	@SuppressWarnings("unchecked")
	private boolean showChoosenSubMenu(Menu<T> menu, int subMenuNumber) {
		if (subMenuNumber - 1 > menu.getSubMenu().size()) {
			printStream.println("Invalid Input Please Try Again");
			return false;
		}
		if (subMenuNumber - 1 == menu.getSubMenu().size()) {
			showMenu(menuContext.popMenuStack());
			return false;
		}
		Menu<T> subMenu = menu.getSubMenu().get(subMenuNumber - 1);
		menuContext.pushMenuStack(menu);
		showMenu(subMenu);
		return true;
	}

	private int getEnteredOption() {
		int subMenuNumber = scanner.nextInt();
		return subMenuNumber;
	}

	private void printDescreption(Menu<T> menu) {
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
			printStream.println(String.format("Error : ", ex.getMessage()));
			LOGGER.error(ex.getMessage(),ex);
			showField(field);
		}
	}

}
