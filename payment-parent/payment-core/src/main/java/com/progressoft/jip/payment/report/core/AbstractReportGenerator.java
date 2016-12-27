package com.progressoft.jip.payment.report.core;

import java.util.LinkedList;
import java.util.Objects;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.report.impl.HierarchicalReportNode;
import com.progressoft.jip.payment.report.impl.TerminalReportNode;

public abstract class AbstractReportGenerator implements ReportGenerator {
	private static final String PAYMENT_PURPOSE_TAG = "payment-purpose";
	private static final String PAYMENT_DATE_TAG = "payment-date";
	private static final String TRANSFER_CURRENCY_TAG = "transfer-currency";
	private static final String PAYMENT_AMOUNT_TAG = "payment-amount";
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
	protected String supportedFileExtension = null;
	protected ReportSettings settings;

	@Override
	public void generateReport(ReportSettings settings) {
		this.settings = settings;
		validateSettings();
		startWrite();
		writePayments(settings.getPayments());
		endWrite();
	}

	protected abstract void startWrite();

	protected abstract void startPayment();

	@SuppressWarnings("rawtypes")
	protected abstract void writeNode(ReportNode node);

	protected abstract void endPayment();

	protected abstract void endWrite();

	@FunctionalInterface
	protected interface WriteOperation {

		public static final WriteOperation noOp = () -> {
		};

		void execute() throws Exception;
	}

	protected void writeAndHandleException(WriteOperation writeOperation, WriteOperation onException, String message) {
		try {
			writeOperation.execute();
		} catch (Exception e1) {
			try {
				onException.execute();
			} catch (Exception e2) {
			}
			throw new ReportGeneratorException(message, e1);
		}
	}

	private void validateSettings() {
		if (isNull(settings) || isNull(settings.getPayments(), settings.getFileName(), settings.getPath())) {
			throw new ReportGeneratorException("Report settings fields cannot be null");
		}
		if (!settings.getPayments().iterator().hasNext() || this.settings.getFileName().isEmpty()) {
			throw new ReportGeneratorException("Invalid arguments in report settings");
		}
	}

	private boolean isNull(Object... objs) {
		for (Object obj : objs) {
			if (Objects.isNull(obj)) {
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
}