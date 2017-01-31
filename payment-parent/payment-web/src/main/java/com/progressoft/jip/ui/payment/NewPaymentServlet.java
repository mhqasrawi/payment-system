package com.progressoft.jip.ui.payment;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.service.PaymentPurposePersistenceService;
import com.progressoft.jip.payment.usecase.LoadAllPaymentPurposeUseCase;
import com.progressoft.jip.payment.usecase.NewPaymentUseCase;
import com.progressoft.jip.session.PaymentMenuContextConstant;
import com.progressoft.jip.ui.payment.conversion.TypeConverters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@WebServlet(urlPatterns = "/" + NewPaymentServlet.URL)
public class NewPaymentServlet extends HttpServlet {
    //TODO FIX IT OR E7REG IT

    static final String URL = "new-payment";
    private static final String CURRENCIES = "currencies";
    private static final String PAGE_CONTENT = "pageContent";
    private static final String BASE_JSP_URL = "/WEB-INF/views/base.jsp";
    private static final long serialVersionUID = -739079731396671416L;
    private static final String JSP_PAGE_URL = "/WEB-INF/views/new-payment.jsp";
    private List<PaymentPurposeDTO> allPaymentPurpose;

    private List<String> currencyList = new ArrayList<>();

    private ImplementationProvider implementationProvider;

    @Override
    public void init() throws ServletException {
        currencyList.add("JOD");
        currencyList.add("KWD");
        currencyList.add("USD");

        implementationProvider = (ImplementationProvider) getServletContext()
                .getAttribute(ImplementationProvider.DEPENDENCY_PROVIDER);
        allPaymentPurpose = new ArrayList<>();
        allPaymentPurpose = (List<PaymentPurposeDTO>) new LoadAllPaymentPurposeUseCase(
                implementationProvider.getImplementation(PaymentPurposePersistenceService.class)).loadPaymentPurpose();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        refreshAttribute(req);
        req.getRequestDispatcher(BASE_JSP_URL).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaymentInfo info = new PaymentInfoImpl(
                (PaymentMenuContext) req.getAttribute(PaymentMenuContextConstant.PAYMENT_MENU_CONTEXT))
                .buildPaymentInfo(req.getParameter("beneficary-iban"), req.getParameter("beneficiary-name"), req.getParameter("payment-amount"), req.getParameter("transfer-currency"), req.getParameter("payment-date"),
                        req.getParameter("payment-purpose"));
        NewPaymentUseCase newPaymentUseCase = new NewPaymentUseCase(implementationProvider.getImplementation(PaymentDAO.class),
                implementationProvider.getImplementation(IBANDAO.class),
                implementationProvider.getImplementation(AccountDAO.class));
        newPaymentUseCase.processPayment(info);
    }

    private void refreshAttribute(HttpServletRequest req) {
        req.setAttribute(PAGE_CONTENT, JSP_PAGE_URL);
        req.setAttribute(CURRENCIES, currencyList);
        allPaymentPurpose = new ArrayList<>();
        allPaymentPurpose = (List<PaymentPurposeDTO>) new LoadAllPaymentPurposeUseCase(
                implementationProvider.getImplementation(PaymentPurposePersistenceService.class)).loadPaymentPurpose();
        req.setAttribute("purposes", allPaymentPurpose);
    }

    private class PaymentInfoImpl implements PaymentInfo {
        private AccountDTO orderingAccount;
        private String beneficiaryName;
        private LocalDateTime paymentDate;
        private PaymentPurposeDTO paymentPurposeDTO;
        private IBANDTO beneficiaryIBAN;
        private BigDecimal paymentAmount;

        private Currency transferCurrency;

        public PaymentInfoImpl(PaymentMenuContext context) {
            this.orderingAccount = context.getCurrentAccount();
        }

        @Override
        public AccountDTO getOrderingAccount() {
            return orderingAccount;
        }

        @Override
        public IBANDTO getBeneficiaryIBAN() {
            return beneficiaryIBAN;
        }

        @Override
        public String getBeneficiaryName() {
            return beneficiaryName;
        }

        @Override
        public BigDecimal getPaymentAmount() {
            return paymentAmount;
        }

        @Override
        public Currency getTransferCurrency() {
            return transferCurrency;
        }

        @Override
        public LocalDateTime getPaymentDate() {
            return paymentDate;
        }

        @Override
        public PaymentPurposeDTO getPaymentPurpose() {
            return paymentPurposeDTO;
        }

        public PaymentInfo buildPaymentInfo(String beneficaryIban, String beneficiaryName, String paymentAmount,
                                            String transferCurrency, String paymentDate, String paymentPurpose) {
            TypeConverters typeConverters = new TypeConverters(implementationProvider);
            this.paymentDate = typeConverters.convertString(LocalDateTime.class, paymentDate);
            this.paymentPurposeDTO = typeConverters.convertString(PaymentPurposeDTO.class, paymentPurpose);
            this.beneficiaryIBAN = typeConverters.convertString(IBANDTO.class, beneficaryIban);
            this.paymentAmount = typeConverters.convertString(BigDecimal.class, paymentAmount);
            this.transferCurrency = typeConverters.convertString(Currency.class, transferCurrency);
            this.beneficiaryName = beneficiaryName;
            return this;
        }
    }
}
