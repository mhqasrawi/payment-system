package com.progressoft.jip.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.progressoft.jip.MenuContextImpl;
import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.ui.menu.Menu;

public class PaymentMenuContextConfigurator implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setAttribute(PaymentMenuContextConstant.PAYMENT_MENU_CONTEXT, new MenuContextImpl());
		PaymentMenuContext context= (PaymentMenuContext) se.getSession().getAttribute(PaymentMenuContextConstant.PAYMENT_MENU_CONTEXT);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

	}

}
