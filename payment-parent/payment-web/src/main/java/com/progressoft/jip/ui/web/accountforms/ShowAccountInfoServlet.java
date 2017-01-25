package com.progressoft.jip.ui.web.accountforms;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.session.PaymentMenuContextConstant;

/**
 * @author u623
 */
@WebServlet(urlPatterns = "/accountInfo")
public class ShowAccountInfoServlet extends HttpServlet {
//    private ImplementationProvider implementationProvider;

//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        implementationProvider = (ImplementationProvider) config.getServletContext()
//                .getAttribute(ImplementationProvider.DEPENDENCY_PROVIDER);
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        PaymentMenuContext context =
//                (PaymentMenuContext) req.getSession().
//                        getAttribute(PaymentMenuContextConstant.PAYMENT_MENU_CONTEXT);
//        AccountDTO account = context.getCurrentAccount();
//        req.setAttribute("account", account);
    	req.setAttribute("pageContent", "/WEB-INF/views/accountInfo.jsp");
		req.getRequestDispatcher("/WEB-INF/views/base.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/accountInfo");
    }
}
