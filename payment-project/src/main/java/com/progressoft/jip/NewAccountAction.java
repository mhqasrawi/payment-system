package com.progressoft.jip;

import java.util.HashMap;
import java.util.Map;

import com.progressoft.jip.payment.account.service.AccountPersistenceService;

public class NewAccountAction implements Action {

	private static final String FORM_VALUE = "FormValue";
	private Action action;
	private AccountPersistenceService accountPersistenceService;
	private Map<String,Object> values = new HashMap<>();

	public NewAccountAction(Action action) {
		this.action = action;
	}

	@Override
	public MenuContext doAction(MenuContext menuContext) {
		menuContext.put(FORM_VALUE, null);
		action.doAction(menuContext);
		Form form = menuContext.get(FORM_VALUE);
		for(Field field :form.getFields()){
			values.put(field.getName(),field.getValue());
		}
		return menuContext;
	}

}
