package com.progressoft.jip.ui.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.service.PaymentPurposePersistenceService;
import com.progressoft.jip.payment.usecase.LoadAllPaymentPurposeUseCase;
import com.progressoft.jip.payment.usecase.NewPaymentUseCase;
import com.progressoft.jip.session.PaymentMenuContextConstant;

@WebServlet(urlPatterns = "/" + NewPaymentServlet.URL)
public class NewPaymentServlet extends HttpServlet {

    private static final String CURRENCIES = "currencies";

    private static final String PAGE_CONTENT = "pageContent";

    private static final String BASE_JSP_URL = "/WEB-INF/views/base.jsp";

    private static final long serialVersionUID = -739079731396671416L;

    private static final String JSP_PAGE_URL = "/WEB-INF/views/new-payment.jsp";

    static final String URL = "new-payment";

    private List<PaymentPurposeDTO> allPaymentPurpose;

    private List<String> currencies = new ArrayList<String>();

    private ImplementationProvider implementationProvider;

    @Override
    public void init() throws ServletException {
        currencies.add("JOD");
        currencies.add("KWD");
        currencies.add("USD");

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
        String beneficaryIban = req.getParameter("beneficary-iban");
        String beneficiaryName = req.getParameter("beneficiary-name");
        String paymentAmount = req.getParameter("payment-amount");
        String transferCurrency = req.getParameter("transfer-currency");
        String paymentDate = req.getParameter("payment-date");
        String paymentPurpose = req.getParameter("payment-purpose");

        PaymentInfo info = new PaymentInfoImpl(
                (PaymentMenuContext) req.getAttribute(PaymentMenuContextConstant.PAYMENT_MENU_CONTEXT))
                .buildPaymentInfo(beneficaryIban, beneficiaryName, paymentAmount, transferCurrency, paymentDate,
                        paymentPurpose);
        NewPaymentUseCase newPaymentUseCase = new NewPaymentUseCase(implementationProvider.getImplementation(PaymentDAO.class),
                implementationProvider.getImplementation(IBANDAO.class),
                implementationProvider.getImplementation(AccountDAO.class));
        newPaymentUseCase.processPayment(info);
    }

    private void refreshAttribute(HttpServletRequest req) {
        req.setAttribute(PAGE_CONTENT, JSP_PAGE_URL);
        req.setAttribute(CURRENCIES, currencies);
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
            String[] date = paymentDate.split("/");
            this.paymentDate = LocalDateTime.of(
                    LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0])),
                    LocalTime.now());
            this.beneficiaryName = beneficiaryName;
            this.paymentPurposeDTO = allPaymentPurpose.stream().filter(p -> paymentPurpose.equals(p.getShortCode()))
                    .findAny().orElse(null);
            IBANDTOImpl ibandtoImpl = new IBANDTOImpl();
            ibandtoImpl.setIbanValue(beneficaryIban.substring(2));
            ibandtoImpl.setCountryCode(beneficaryIban.substring(0, 2));
            this.beneficiaryIBAN = ibandtoImpl;
            this.paymentAmount = BigDecimal.valueOf(Long.valueOf(paymentAmount));
            this.transferCurrency = Currency.getInstance(transferCurrency);
            return this;
        }

    }

}
