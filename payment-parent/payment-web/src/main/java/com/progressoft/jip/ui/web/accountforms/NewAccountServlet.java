package com.progressoft.jip.ui.web.accountforms;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.currency.currenciesprovider.CurrencyCodeProvider;
import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.customer.CustomerDTOImpl;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.iban.IBANValidationException;
import com.progressoft.jip.payment.iban.IBANValidator;
import com.progressoft.jip.payment.usecase.NewAccountUseCase;
import com.progressoft.jip.session.PaymentMenuContextConstant;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author u623 i8i
 */
@WebServlet(urlPatterns = "/newAccount")
public class NewAccountServlet extends HttpServlet {

	private static final long serialVersionUID = -3033338791739821939L;

	private ImplementationProvider implementationProvider;

	@Override
	public void init(ServletConfig config) throws ServletException {

		implementationProvider = (ImplementationProvider) config.getServletContext()
				.getAttribute(ImplementationProvider.DEPENDENCY_PROVIDER);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forwardToNewAccountForm(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PaymentMenuContext paymentMenuContext = (PaymentMenuContext) req.getSession()
				.getAttribute(PaymentMenuContextConstant.PAYMENT_MENU_CONTEXT);

		NewAccountUseCase accountUseCase = implementationProvider.getImplementation(NewAccountUseCase.class);
		AccountDTOImpl accountDto = new AccountDTOImpl();
		try {
			setNewAccountInfo(req, accountDto);
			accountUseCase.process(paymentMenuContext, accountDto);
//			req.setAttribute("pageContent", "/WEB-INF/views/accountInfo.jsp");
			req.getRequestDispatcher("/accountInfo").forward(req, resp);
		} catch (IBANValidationException | NumberFormatException e) {
			req.setAttribute("IBANError", e);
			forwardToNewAccountForm(req, resp);
		}

	}

	private void setNewAccountInfo(HttpServletRequest req, AccountDTOImpl accountDto) {
		accountDto.setIbandto(parseIban(req));
		accountDto.setAccountNumber(req.getParameter("accountNumber"));
		accountDto.setAccountName(req.getParameter("accountName"));
		accountDto.setCurrency(Currency.getInstance(req.getParameter("currency")));
		accountDto.setAccountStatus(AccountDTO.AccountStatus.valueOf(req.getParameter("accountStatus")));
		CustomerDTOImpl customer = new CustomerDTOImpl();
		customer.setName(req.getParameter("customerDTO"));
		accountDto.setCustomerDTO(customer);
		accountDto.setPaymentRule(req.getParameter("paymentRule"));
		accountDto.setPaymentRuleInfo(req.getParameter("paymentRuleInfo"));
		accountDto.setBalance(new BigDecimal(req.getParameter("balance")));
	}

	private IBANDTOImpl parseIban(HttpServletRequest req) {
		IBANDTOImpl ibandto = new IBANDTOImpl();
		ibandto.setIbanValue(req.getParameter("ibandto"));
		validateIban(ibandto);
		return ibandto;
	}

	private void validateIban(IBANDTOImpl ibandto) {
		IBANValidator ibanValidator = implementationProvider.getImplementation(IBANValidator.class);
		ibanValidator.validate(ibandto);
	}

	private void forwardToNewAccountForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CurrencyCodeProvider provider = implementationProvider.getImplementation(CurrencyCodeProvider.class);
		Iterable<String> listAllCurrency = provider.listAllCurrency();
		req.setAttribute("currencyList", listAllCurrency);
		req.getRequestDispatcher("/newaccount-view.jsp").forward(req, resp);

	}

}
