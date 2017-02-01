package com.progressoft.jip.payment.report.impl;

import java.util.LinkedList;
import java.util.List;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.report.core.ReportNode;
import com.progressoft.jip.payment.report.core.ReportNodeProvider;
import com.progressoft.jip.payment.report.core.ReportSettingsSpi;

@SuppressWarnings("rawtypes")
public class ReportNodeProviderImpl implements ReportNodeProvider {
	private static final String BALANCE_TAG = "balance";
	private static final String PAYMENT_PURPOSE_TAG = "payment-purpose";
	private static final String PAYMENT_DATE_TAG = "payment-date";
	private static final String TRANSFER_CURRENCY_TAG = "transfer-currency";
	private static final String PAYMENT_AMOUNT_TAG = "payment-amount";
	private static final String PAYMENT_AMOUNT_TRANSCRIPTION_TAG = "payment-amount-transcription";
	private static final String BENEFICIARY_NAME_TAG = "beneficiary-name";
	private static final String BENEFICIARY_IBAN_TAG = "beneficiary-iban";
	private static final String ORDERING_ACCOUNT_TAG = "ordering-account";
	private static final String STATUS_TAG = "status";
	private static final String CURRENCY_TAG = "currency";
	private static final String CUSTOMER_NAME_TAG = "customer-name";
	private static final String ACCOUNT_NAME_TAG = "name";
	private static final String ACCOUNT_IBAN_TAG = "iban";
	private static final String ACCOUNT_NUMBER_TAG = "number";
	protected static final String PAYMENT_TAG = "payment";
	protected static final String REPORT_NAME_TAG = "customer-payments-report";
	private ReportSettingsSpi settings;
	private List<ReportNode> nodes = new LinkedList<>();
	
	@Override
	public void setReportSettings(ReportSettingsSpi settings) {
		this.settings = settings;
	}
	
	@Override
	public Iterable<ReportNode> getNodes(PaymentDTO payment) {
		AccountDTO orderingAccount = payment.getOrderingAccount();
		addOrderingAccountInfo(orderingAccount);
		// TODO remove parse
		addBeneficiaryInfo(payment, this.settings.newTranscriberInstance()
				.transcript(Float.parseFloat(payment.getPaymentAmount().toString())));
		return nodes;
	}
	
	private void addOrderingAccountInfo(AccountDTO orderingAccount) {
		LinkedList<ReportNode> subNodes = new LinkedList<>();
		subNodes.add(newNode(ACCOUNT_NUMBER_TAG, orderingAccount.getAccountNumber()));
		subNodes.add(newNode(ACCOUNT_IBAN_TAG, orderingAccount.getIban().getIBANValue()));
		subNodes.add(newNode(ACCOUNT_NAME_TAG, orderingAccount.getAccountName()));
		subNodes.add(newNode(CUSTOMER_NAME_TAG, orderingAccount.getCustomerDTO().getName()));
		subNodes.add(newNode(BALANCE_TAG, orderingAccount.getBalance().toString()));
		subNodes.add(newNode(CURRENCY_TAG, orderingAccount.getCurreny().getDisplayName()));
		subNodes.add(newNode(STATUS_TAG, orderingAccount.getAccountStatus().toString()));
		nodes.add(newNode(ORDERING_ACCOUNT_TAG, subNodes));
	}

	private void addBeneficiaryInfo(PaymentDTO payment, String transcribedPayment) {
		nodes.add(newNode(BENEFICIARY_IBAN_TAG, payment.getBeneficiaryIBAN().getIBANValue()));
		nodes.add(newNode(BENEFICIARY_NAME_TAG, payment.getBeneficiaryName()));
		nodes.add(newNode(PAYMENT_AMOUNT_TAG, String.valueOf(payment.getPaymentAmount())));
		nodes.add(newNode(PAYMENT_AMOUNT_TRANSCRIPTION_TAG, transcribedPayment));
		nodes.add(newNode(TRANSFER_CURRENCY_TAG, payment.getTransferCurrency().getDisplayName()));
		nodes.add(newNode(PAYMENT_DATE_TAG, payment.getSettlementDate().toString()));
		nodes.add(newNode(PAYMENT_PURPOSE_TAG, payment.getPaymentPurpose().getDescription()));
	}

	@SuppressWarnings("unchecked")
	private ReportNode newNode(String name, Object value) {
		return (value instanceof String) ? new TerminalReportNode(name, (String) value)
				: new HierarchicalReportNode(name, (Iterable<ReportNode>) value);
	}
}