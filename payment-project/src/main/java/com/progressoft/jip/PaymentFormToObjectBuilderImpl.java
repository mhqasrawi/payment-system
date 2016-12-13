package com.progressoft.jip;

import java.util.List;

import com.progressoft.jip.ui.dynamic.menu.FormToObjectBuilderImpl;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.Menu;

public class PaymentFormToObjectBuilderImpl<T>
		extends FormToObjectBuilderImpl<PaymentMenuContext, T>
		implements PaymentFormToObjectBuilder<T> {

	public PaymentFormToObjectBuilderImpl<T> setDescription(String description) {
		super.setDescription(description);
		return this;
	}

	public PaymentFormToObjectBuilderImpl<T> setForm(Form form) {
		super.setForm(form);
		return this;
	}

	public PaymentFormToObjectBuilderImpl<T> setInterfaceType(Class<T> interfaceClass) {
		super.setInterfaceType(interfaceClass);
		return this;
	}

	public PaymentFormToObjectBuilderImpl<T> setProcessingStrategy(
			SubmitAction<PaymentMenuContext, T> objectProssingStratege) {
		super.setProcessingStrategy(objectProssingStratege);
		return this;
	}
	
	public PaymentFormToObjectBuilderImpl<T> setSubMenu(List<Menu<PaymentMenuContext>> subMenu) {
		super.setSubMenu(subMenu);
		return this;
	}

}
