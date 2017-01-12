package com.progressoft.jip.ui.web.accountforms;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;

/**
 * @author u623
 *
 */
@WebServlet(urlPatterns = "/accountInfo")
public class ShowAccountInfoServlet extends HttpServlet {
	private ImplementationProvider implementationProvider;

	@Override
	public void init(ServletConfig config) throws ServletException {
		implementationProvider = (ImplementationProvider) config.getServletContext()
				.getAttribute(ImplementationProvider.DEPENDENCY_PROVIDER);
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String accountNumberParameter = req.getParameter("accountNumber");
		AccountPersistenceService persistenceService = implementationProvider
				.getImplementation(AccountPersistenceService.class);
		AccountDTO account = persistenceService.getAccount(accountNumberParameter);
		req.setAttribute("account", account);
		req.setAttribute("pageContent", "/WEB-INF/views/accountInfo.jsp");
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/base.jsp");
		requestDispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("/accountInfo");
	}
}
