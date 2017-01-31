package com.progressoft.jip.ui.account;

import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.usecase.ChooseAccountUseCase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/pikup_account")
public class PickupAccountServlet extends HttpServlet {

    public static final String CURRENT_ACCOUNT = "CurrentAccount";

    private static final long serialVersionUID = 1699665816065799380L;


    private ChooseAccountUseCase pikupAccountUseCase;

    private AccountPersistenceService accountService = null;

    @Override
    public void init() throws ServletException {
        //TODO get implementaion of pickAccountUSeCase from ImplProvider
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/pikup-account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountNumber = req.getParameter("account-number");
        pikupAccountUseCase.loadAccountByAccountNumber(accountNumber);
    }

}
