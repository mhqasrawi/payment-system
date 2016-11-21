package com.progressoft.jip;

import java.util.List;

import com.progressoft.jip.ui.dynamic.menu.FormToObjectBuilderImpl;
import com.progressoft.jip.ui.dynamic.menu.ObjectProcessingStrategy;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.Menu;

public class PaymentFormToObjectBuilderImpl<INTERFACE_TYPE>
		extends FormToObjectBuilderImpl<PaymentMenuContext, INTERFACE_TYPE>
		implements PaymentFormToObjectBuilder<INTERFACE_TYPE> {

	public PaymentFormToObjectBuilderImpl<INTERFACE_TYPE> setDescription(String description) {
		super.setDescription(description);
		return this;
	}

	public PaymentFormToObjectBuilderImpl<INTERFACE_TYPE> setForm(Form form) {
		super.setForm(form);
		return this;
	}

	public PaymentFormToObjectBuilderImpl<INTERFACE_TYPE> setInterfaceType(Class<INTERFACE_TYPE> interfaceClass) {
		super.setInterfaceType(interfaceClass);
		return this;
	}

	public PaymentFormToObjectBuilderImpl<INTERFACE_TYPE> setProcessingStrategy(
			ObjectProcessingStrategy<PaymentMenuContext, INTERFACE_TYPE> objectProssingStratege) {
		super.setProcessingStrategy(objectProssingStratege);
		return this;
	}
	
	public PaymentFormToObjectBuilderImpl<INTERFACE_TYPE> setSubMenu(List<Menu<PaymentMenuContext>> subMenu) {
		super.setSubMenu(subMenu);
		return this;
	}

}
