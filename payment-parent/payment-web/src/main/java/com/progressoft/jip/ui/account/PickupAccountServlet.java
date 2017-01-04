package com.progressoft.jip.ui.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.progressoft.jip.PikupAccountUseCase;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;

@WebServlet(urlPatterns = "/pikup_account")
public class PickupAccountServlet extends HttpServlet {

	public static final String CURRENT_ACCOUNT = "CurrentAccount";

	private static final long serialVersionUID = 1699665816065799380L;

	private PikupAccountUseCase pikupAccountUseCase;

	private AccountPersistenceService accountService = null;

	@Override
	public void init() throws ServletException {
		pikupAccountUseCase = new PikupAccountUseCase(accountService );
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/jsp/pikup-account.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String accountNumber = req.getParameter("account-number");
		HttpSession session = req.getSession();
		session.setAttribute(CURRENT_ACCOUNT, pikupAccountUseCase.getAccountByAccountNumber(accountNumber));
	}

}
