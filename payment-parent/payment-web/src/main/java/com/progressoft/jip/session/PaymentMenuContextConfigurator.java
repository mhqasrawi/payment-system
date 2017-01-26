package com.progressoft.jip.session;

import com.progressoft.jip.MenuContextImpl;
import com.progressoft.jip.PaymentMenuContext;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class PaymentMenuContextConfigurator implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("PAYMENT_MENU_CONTEXT", new MenuContextImpl());
        PaymentMenuContext context = (PaymentMenuContext) se.getSession().getAttribute(PaymentMenuContextConstant.PAYMENT_MENU_CONTEXT);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }

}
