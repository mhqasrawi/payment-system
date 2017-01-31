package com.progressoft.jip.ui.account;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.usecase.ChooseAccountUseCase;
import com.progressoft.jip.session.PaymentMenuContextConstant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/pikup_account")
public class PickupAccountServlet extends HttpServlet {

    private static final long serialVersionUID = 1699665816065799380L;
    private ImplementationProvider implementationProvider;

    @Override
    public void init() throws ServletException {
        super.init();
        implementationProvider = (ImplementationProvider) getServletContext().getAttribute(ImplementationProvider.DEPENDENCY_PROVIDER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/pikup-account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChooseAccountUseCase pikupAccountUseCase = new ChooseAccountUseCase((PaymentMenuContext) req.getSession().getAttribute(PaymentMenuContextConstant.PAYMENT_MENU_CONTEXT), implementationProvider.getImplementation(AccountPersistenceService.class));
        String accountNumber = req.getParameter("account-number");
        pikupAccountUseCase.loadAccountByAccountNumber(accountNumber);
        resp.sendRedirect(req.getContextPath()+"/accountInfo");
}

}
