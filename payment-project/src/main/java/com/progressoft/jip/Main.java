package com.progressoft.jip;

import java.util.Arrays;

public class Main {

	public static MenuRenderManger renderManger = new ConsoleMenuRender(System.in, System.out);

	public static void main(String[] args) {
		Form form = new FormImpl("Enter IBAN Number");
		StringField iban = new StringField();
		iban.setDescription("Enter You Iban Number");
		iban.setName("IBAN");
		form.addField(iban);
		Menu mainMenu = new MenuImpl("Choice Option", Arrays.asList(new MenuImpl("Accses Your Account",
				Arrays.asList(new MenuImpl("Sub Menu From Account", new ShowFormAction(renderManger, form))), (n) -> {
					System.out.println("You Enter Accsess Your Account Option");
					return null;
				})));
		renderManger.renderMenu(mainMenu);
	}

}
