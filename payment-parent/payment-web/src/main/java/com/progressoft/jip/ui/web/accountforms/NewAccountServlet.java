package com.progressoft.jip.ui.web.accountforms;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jip.MenuContextImpl;
import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.configuration.Configuration;
import com.progressoft.jip.currency.currenciesprovider.CurrencyProvider;
import com.progressoft.jip.currency.currenciesprovider.CurrencyProviderImpl;
import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.iban.IBANValidationException;
import com.progressoft.jip.payment.iban.IBANValidator;
import com.progressoft.jip.payment.usecase.NewAccountUseCase;
import com.progressoft.jip.ui.field.AccountStatusField;
import com.progressoft.jip.ui.field.BigDecimalField;
import com.progressoft.jip.ui.field.CurrencyField;
import com.progressoft.jip.ui.field.CustomerField;
import com.progressoft.jip.ui.field.IBANField;

/**
 * @author u623
 *
 */
@WebServlet(urlPatterns = "/newAccount")
public class NewAccountServlet extends HttpServlet {
	private ImplementationProvider implementationProvider;
	private Iterable<String> listAllCurrency;
	String property;

	@Override
	public void init(ServletConfig config) throws ServletException {
		implementationProvider = (ImplementationProvider) config.getServletContext()
				.getAttribute(ImplementationProvider.DEPENDENCY_PROVIDER);
		Configuration configuration = implementationProvider.getImplementation(Configuration.class);
		 property = configuration.getProperty("currency.code.file");
		System.out.println(property);
		
	
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forwardToNewAccountForm(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AccountPersistenceService persistenceService = implementationProvider
				.getImplementation(AccountPersistenceService.class);
		PaymentMenuContext paymentMenuContext = new MenuContextImpl();
		NewAccountUseCase accountUseCase = new NewAccountUseCase(persistenceService);
		AccountDTOImpl account = new AccountDTOImpl();

		try {
			account.setAccountNumber(req.getParameter("accountNumber"));
			setNewAccountInfo(req, account);
			accountUseCase.process(paymentMenuContext, account);
			resp.sendRedirect("/accountInfo?accountNumber=" + account.getAccountNumber());

		} catch (IBANValidationException e) {
			req.setAttribute("IBANError", e);
			forwardToNewAccountForm(req, resp);
		}

	}

	private void forwardToNewAccountForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CurrencyProvider currencyProvider = new CurrencyProviderImpl(property);
		listAllCurrency = currencyProvider.listAllCurrency();
		req.setAttribute("currencyList", listAllCurrency);

		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/newaccount-view.jsp");
		requestDispatcher.forward(req, resp);
	}

	private void setNewAccountInfo(HttpServletRequest req, AccountDTOImpl account) {
		IBANValidator ibanValidator = implementationProvider.getImplementation(IBANValidator.class);

		IBANField ibanField = new IBANField(ibanValidator);
		ibanField.setValue(req.getParameter("ibandto"));

		account.setIbandto(ibanField.getValue());

		account.setAccountName(req.getParameter("accountName"));

		CurrencyField currencyField = new CurrencyField();
		currencyField.setValue(req.getParameter("currency"));
		account.setCurrency(currencyField.getValue());

		AccountStatusField statusField = new AccountStatusField();
		statusField.setValue(req.getParameter("accountStatus"));
		account.setAccountStatus(statusField.getValue());

		CustomerField customerField = new CustomerField();
		customerField.setValue(req.getParameter("customerDTO"));
		account.setCustomerDTO(customerField.getValue());

		account.setPaymentRule(req.getParameter("paymentRule"));
		account.setPaymentRuleInfo(req.getParameter("paymentRuleInfo"));

		BigDecimalField decimalField = new BigDecimalField();
		decimalField.setValue(req.getParameter("balance"));
		account.setBalance(decimalField.getValue());
	}
}
