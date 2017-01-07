package com.progressoft.jip.payment.usecase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.PaymentUseCaseProcessor;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public class NewPaymentUseCase implements PaymentUseCaseProcessor {

	private final PaymentDAO paymentDao;
	private final IBANDAO ibanDao;
	private final AccountDAO accountDao;

	public NewPaymentUseCase(PaymentDAO paymentDao, IBANDAO ibanDao, AccountDAO accountDao) {
		this.paymentDao = paymentDao;
		this.ibanDao = ibanDao;
		this.accountDao = accountDao;
	}

	@Override
	public void processPayment(PaymentInfo paymentInfo) {
		BigDecimal netBalance = paymentInfo.getOrderingAccount().getBalance().subtract(paymentInfo.getPaymentAmount());
		if (netBalance.compareTo(BigDecimal.ZERO) >= 0) {
			accountDao.save(getAccountProxy(paymentInfo, netBalance));
		}
		paymentDao.save(new PaymentDtoImpl(paymentInfo, ibanDao));
	}

	private AccountDTO getAccountProxy(PaymentInfo paymentInfo, BigDecimal netBalance) {
		ClassLoader loader = NewPaymentUseCase.class.getClassLoader();
		return (AccountDTO) Proxy.newProxyInstance(loader, new Class[] { AccountDTO.class }, new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] arg2) throws Throwable {
				if ("getBalance".equals(method.getName())) {
					return netBalance;
				}
				return method.invoke(paymentInfo.getOrderingAccount(), arg2);
			}
		});
	}

	private static class PaymentDtoImpl implements PaymentDTO {
		private PaymentInfo paymentInfo;
		private IBANDAO ibanDao;

		public PaymentDtoImpl(PaymentInfo paymentInfo, IBANDAO ibanDao) {
			this.paymentInfo = paymentInfo;
			this.ibanDao = ibanDao;
		}

		@Override
		public int getId() {
			return 1;
		}

		@Override
		public AccountDTO getOrderingAccount() {
			return paymentInfo.getOrderingAccount();
		}

		@Override
		public IBANDTO getBeneficiaryIBAN() {
			return ibanDao.get(paymentInfo.getBeneficiaryIBAN().getIBANValue());
		}

		@Override
		public String getBeneficiaryName() {
			return paymentInfo.getBeneficiaryName();
		}

		@Override
		public BigDecimal getPaymentAmount() {
			return paymentInfo.getPaymentAmount();
		}

		@Override
		public Currency getTransferCurrency() {
			return paymentInfo.getTransferCurrency();
		}

		@Override
		public LocalDateTime getPaymentDate() {
			return paymentInfo.getPaymentDate();
		}

		@Override
		public PaymentPurposeDTO getPaymentPurpose() {
			return paymentInfo.getPaymentPurpose();
		}

	}
}