package com.progressoft.jip.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.progressoft.jip.MenuContextImpl;
import com.progressoft.jip.PaymentMenuContext;

@WebListener
public class PaymentMenuContextConfiguratorListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(PaymentMenuContextConstant.PAYMENT_MENU_CONTEXT, new MenuContextImpl());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // Not Used
    }

}
