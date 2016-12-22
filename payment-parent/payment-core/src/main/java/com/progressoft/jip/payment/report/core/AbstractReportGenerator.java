package com.progressoft.jip.payment.report.core;

import java.util.LinkedList;
import java.util.Objects;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.report.impl.HierarchicalReportNode;
import com.progressoft.jip.payment.report.impl.TerminalReportNode;

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
	protected final String PAYMENT_TAG = "payment";
	protected final String REPORT_NAME_TAG = "customer-payments-report";
	protected String supportedFileExtension = null;
	protected ReportSettings settings;

	@Override
	public void generateReport(ReportSettings settings) {
		this.settings = settings;
		validateSettings();
		startWrite();
		writePayments(this.settings.getPayments());
		endWrite();
	}

	private void validateSettings() {
		if (isNull(settings) || isNull(settings.getPayments(), settings.getFileName(), settings.getPath())) {
			throw new NullPointerException("Report settings fields cannot be null");
		} 
		if (!settings.getPayments().iterator().hasNext() || this.settings.getFileName().isEmpty()) {
			throw new IllegalArgumentException("Invalid arguments in report settings");
		} 
	}
	
	private boolean isNull(Object... objs) {
		for(Object obj : objs) {
			if(Objects.isNull(obj)) {
				return true;
			}
		}
		return false;
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

	@SuppressWarnings("rawtypes")
	private void writeOrderingAccountInfo(AccountDTO orderingAccount) {
		LinkedList<ReportNode> subNodes = new LinkedList<>();
		subNodes.add(new TerminalReportNode(ACCOUNT_NUMBER_TAG, orderingAccount.getAccountNumber()));
		subNodes.add(new TerminalReportNode(ACCOUNT_IBAN_TAG, orderingAccount.getIban().getIBANValue()));
		subNodes.add(new TerminalReportNode(ACCOUNT_NAME_TAG, orderingAccount.getAccountName()));
		subNodes.add(new TerminalReportNode(CUSTOMER_NAME_TAG, orderingAccount.getCustomerDTO().getName()));
		subNodes.add(new TerminalReportNode(CURRENCY_TAG, orderingAccount.getCurreny().getDisplayName()));
		subNodes.add(new TerminalReportNode(STATUS_TAG, orderingAccount.getAccountStatus().toString()));
		LinkedList<ReportNode> subNodes2 = new LinkedList<>();
		subNodes2.add(new TerminalReportNode(CURRENCY_TAG, orderingAccount.getCurreny().getDisplayName()));
		subNodes2.add(new TerminalReportNode(STATUS_TAG, orderingAccount.getAccountStatus().toString()));
		HierarchicalReportNode h = new HierarchicalReportNode("test node", subNodes2);
		subNodes.add(h);
		writeNode(new HierarchicalReportNode(ORDERING_ACCOUNT_TAG, subNodes));
	}

	private void writeBeneficiaryInfo(PaymentDTO payment) {
		writeNode(new TerminalReportNode(BENEFICIARY_IBAN_TAG, payment.getBeneficiaryIBAN().getIBANValue()));
		writeNode(new TerminalReportNode(BENEFICIARY_NAME_TAG, payment.getBeneficiaryName()));
		writeNode(new TerminalReportNode(PAYMENT_AMOUNT_TAG, String.valueOf(payment.getPaymentAmount())));
		writeNode(new TerminalReportNode(TRANSFER_CURRENCY_TAG, payment.getTransferCurrency().getDisplayName()));
		writeNode(new TerminalReportNode(PAYMENT_DATE_TAG, payment.getPaymentDate().toString()));
		writeNode(new TerminalReportNode(PAYMENT_PURPOSE_TAG, payment.getPaymentPurpose().getDescription()));
	}

	protected void writeAndHandleException(WriteOperation writeOperation, WriteOperation onException, String message) {
		try {
			writeOperation.execute();
		} catch (Exception e1) {
			try {
				onException.execute();
			} catch (Exception e2) {
			}
			throw new ReportException(message, e1);
		}
	}

	protected abstract void startWrite();

	protected abstract void startPayment();

	@SuppressWarnings("rawtypes")
	protected abstract void writeNode(ReportNode node);

	protected abstract void endPayment();

	protected abstract void endWrite();

	protected interface WriteOperation {
		
		static final WriteOperation noOp = () -> {};
		
		void execute() throws Exception;
	}
}