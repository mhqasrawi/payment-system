package com.progressoft.jip.payment.report;

import java.util.LinkedList;
import java.util.Objects;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.account.AccountDTO;

public abstract class AbstractReportGenerator implements ReportGenerator {
	private final String PAYMENT_PURPOSE_TAG = "payment-purpose";
	private final String PAYMENT_DATE_TAG = "payment-date";
	private final String TRANSFER_CURRENCY_TAG = "transfer-currency";
	private final String PAYMENT_AMOUNT_TAG = "payment-amount";
	private final String BENEFICIARY_NAME_TAG = "beneficiary-name";
	private final String BENEFICIARY_IBAN_TAG = "beneficiary-iban";
	private final String ORDERING_ACCOUNT_TAG = "ordering-account";
	private final String STATUS_TAG = "status";
	private final String CURRENCY_TAG = "currency";
	private final String CUSTOMER_NAME_TAG = "customer-name";
	private final String ACCOUNT_NAME_TAG = "name";
	private final String ACCOUNT_IBAN_TAG = "iban";
	private final String ACCOUNT_NUMBER_TAG = "number";
	protected final String REPORT_NAME_TAG = "customer-payments-report";
	protected ReportSettings settings;

	@Override
	public void generateReport(ReportSettings settings) {
		if (Objects.isNull(settings.getPayments())) {
			throw new NullPointerException();
		} else if (!settings.getPayments().iterator().hasNext()) {
			throw new IllegalArgumentException();
		}
		this.settings = settings;
		startWrite();
		writePayments(settings.getPayments());
		endWrite();
	}

	private void writePayments(Iterable<PaymentDTO> payments) {
		for (PaymentDTO payment : payments) {
			startPayment();
			AccountDTO orderingAccount = payment.getOrderingAccount();
			writeOrderingAccountInfo(orderingAccount);
			writeBeneficiaryInfo(payment);
			endPayment();
		}
	}

	private void writeOrderingAccountInfo(AccountDTO orderingAccount) {
		LinkedList<ReportNode<String>> subNodes = new LinkedList<>();
		subNodes.add(new SingleReportNode(ACCOUNT_NUMBER_TAG, orderingAccount.getAccountNumber()));
		subNodes.add(new SingleReportNode(ACCOUNT_IBAN_TAG, orderingAccount.getIban().getIBANValue()));
		subNodes.add(new SingleReportNode(ACCOUNT_NAME_TAG, orderingAccount.getAccountName()));
		subNodes.add(new SingleReportNode(CUSTOMER_NAME_TAG, orderingAccount.getCustomerDTO().getName()));
		subNodes.add(new SingleReportNode(CURRENCY_TAG, orderingAccount.getCurreny().getDisplayName()));
		subNodes.add(new SingleReportNode(STATUS_TAG, orderingAccount.getAccountStatus().toString()));
		writeNode(new HierarchicalReportNode(ORDERING_ACCOUNT_TAG, subNodes));
	}

	private void writeBeneficiaryInfo(PaymentDTO payment) {
		writeNode(new SingleReportNode(BENEFICIARY_IBAN_TAG, payment.getBeneficiaryIBAN().getIBANValue()));
		writeNode(new SingleReportNode(BENEFICIARY_NAME_TAG, payment.getBeneficiaryName()));
		writeNode(new SingleReportNode(PAYMENT_AMOUNT_TAG, String.valueOf(payment.getPaymentAmount())));
		writeNode(new SingleReportNode(TRANSFER_CURRENCY_TAG, payment.getTransferCurrency().getDisplayName()));
		writeNode(new SingleReportNode(PAYMENT_DATE_TAG, payment.getPaymentDate().toString()));
		writeNode(new SingleReportNode(PAYMENT_PURPOSE_TAG, payment.getPaymentPurpose().getDescription()));
	}

	protected abstract void startWrite();

	protected abstract void startPayment();

	protected abstract void writeNode(ReportNode node);

	protected abstract void endPayment();

	protected abstract void endWrite();
}