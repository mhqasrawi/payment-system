package com.progressoft.jip.payment.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.PaymentPurposeDAO;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.account.dao.impl.JDBCAccountDAO;
import com.progressoft.jip.payment.iban.dao.impl.JDBCIBANDAO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public class PaymentDAOImpl implements PaymentDAO {

	private DataSource dataSource;
	private String paymentTable = "";
	private String paymentIdColumn = "";
	private String orderingAccountIDColumn = "";
	private String beneficiaryIBANIDColumn = "";
	private String beneficiaryNameColumn = "";
	private String paymentAmountColumn = "";
	private String transferCurrencyColumn = "";
	private String paymentDateColumn = "";
	private String paymentPurposeShortCodeColumn = "";

	public PaymentDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(PaymentDTO paymentDTO) {
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "insert into " + paymentTable + " (" + paymentIdColumn + "," + orderingAccountIDColumn + ","
				+ beneficiaryIBANIDColumn + "," + beneficiaryNameColumn + "," + paymentAmountColumn + ","
				+ transferCurrencyColumn + "," + paymentDateColumn + "," + paymentPurposeShortCodeColumn
				+ ") values(?,?,?,?,?,?,?)";
		try {
			runner.update(sql, paymentDTO.getId(), paymentDTO.getOrderingAccount().getId(),
					paymentDTO.getBeneficiaryIBAN().getId(), paymentDTO.getBeneficiaryName(),
					paymentDTO.getPaymentAmount(), paymentDTO.getTransferCurrency().getCurrencyCode(), new Date(),
					paymentDTO.getPaymentPurpose());
		} catch (SQLException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public PaymentDTO getById(Integer id) {
		QueryRunner runner = new QueryRunner(dataSource);

		String sql = "select " + paymentIdColumn + " as payment," + orderingAccountIDColumn + " as orderingAccountID, "
				+ beneficiaryIBANIDColumn + " as beneficiaryIBANID," + beneficiaryNameColumn + " as beneficiaryName,"
				+ paymentAmountColumn + " as paymentAmount," + transferCurrencyColumn + " as transferCurrency,"
				+ paymentDateColumn + " as paymentDate," + paymentPurposeShortCodeColumn
				+ " as paymentPurposeShortCode from " + paymentTable + " where id = " + String.valueOf(id);

		try {
			Map<String, Object> paymentMap = runner.query(sql, new MapHandler());
			PaymentDTOImpl paymentDTOImpl = new PaymentDTOImpl();

			JDBCAccountDAO jdbcAccountDAO = new JDBCAccountDAO(dataSource);
			JDBCIBANDAO jdbcibandao = new JDBCIBANDAO(dataSource);

			PaymentPurposeDAO paymentPurposeDAO = new PaymentPurposeDAO() {
				@Override
				public void save(PaymentPurposeDTO paymentPurpose) {
				}

				@Override
				public PaymentPurposeDTO get(String shortCode) {
					return null;
				}
			};

			paymentDTOImpl.setId(id);
			paymentDTOImpl
					.setOrderingAccount(jdbcAccountDAO.getById((Integer) paymentMap.get(orderingAccountIDColumn)));
			paymentDTOImpl.setBeneficiaryIBAN(jdbcibandao.getById((Integer) paymentMap.get(beneficiaryIBANIDColumn)));
			paymentDTOImpl.setBeneficiaryName((String) paymentMap.get(beneficiaryNameColumn));
			paymentDTOImpl.setPaymentAmount((BigDecimal) paymentMap.get(paymentAmountColumn));
			paymentDTOImpl.setTransferCurrency((Currency) paymentMap.get(transferCurrencyColumn));
			paymentDTOImpl.setPaymentDate((LocalDateTime) paymentMap.get(paymentDateColumn));
			paymentDTOImpl
					.setPaymentPurpose(paymentPurposeDAO.get((String) paymentMap.get(paymentPurposeShortCodeColumn)));

			return paymentDTOImpl;
		} catch (SQLException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Iterable<PaymentDTO> getAll() {
		QueryRunner runner = new QueryRunner(dataSource);

		String sql = "select " + paymentIdColumn + " as payment," + orderingAccountIDColumn + " as orderingAccountID, "
				+ beneficiaryIBANIDColumn + " as beneficiaryIBANID," + beneficiaryNameColumn + " as beneficiaryName,"
				+ paymentAmountColumn + " as paymentAmount," + transferCurrencyColumn + " as transferCurrency,"
				+ paymentDateColumn + " as paymentDate," + paymentPurposeShortCodeColumn
				+ " as paymentPurposeShortCode from " + paymentTable;

		try {
			List<PaymentDTO> listOfPayments = new ArrayList<>();
			List<Map<String, Object>> query = runner.query(sql, new MapListHandler());

			for (Map<String, Object> map : query) {
				PaymentDTOImpl paymentDTOImpl = new PaymentDTOImpl();
				JDBCAccountDAO jdbcAccountDAO = new JDBCAccountDAO(dataSource);
				JDBCIBANDAO jdbcibandao = new JDBCIBANDAO(dataSource);

				PaymentPurposeDAO paymentPurposeDAO = new PaymentPurposeDAO() {
					@Override
					public void save(PaymentPurposeDTO paymentPurpose) {
					}

					@Override
					public PaymentPurposeDTO get(String shortCode) {
						return null;
					}
				};
				paymentDTOImpl.setId((Integer) map.get(paymentIdColumn));
				paymentDTOImpl.setOrderingAccount(jdbcAccountDAO.getById((Integer) map.get(orderingAccountIDColumn)));
				paymentDTOImpl.setBeneficiaryIBAN(jdbcibandao.getById((Integer) map.get(beneficiaryIBANIDColumn)));
				paymentDTOImpl.setBeneficiaryName((String) map.get(beneficiaryNameColumn));
				paymentDTOImpl.setPaymentAmount((BigDecimal) map.get(paymentAmountColumn));
				paymentDTOImpl.setTransferCurrency((Currency) map.get(transferCurrencyColumn));
				paymentDTOImpl.setPaymentDate((LocalDateTime) map.get(paymentDateColumn));
				paymentDTOImpl
						.setPaymentPurpose(paymentPurposeDAO.get((String) map.get(paymentPurposeShortCodeColumn)));

				listOfPayments.add(paymentDTOImpl);
			}
			return listOfPayments;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Iterable<PaymentDTO> get(String accountNumber) {
		QueryRunner runner = new QueryRunner(dataSource);
		AccountDAO accountDAO = new JDBCAccountDAO(dataSource);
		AccountDTO orderingAccount = accountDAO.get(accountNumber);
		Integer id = orderingAccount.getId();

		String sql = "select " + paymentIdColumn + " as payment," + orderingAccountIDColumn + " as orderingAccountID, "
				+ beneficiaryIBANIDColumn + " as beneficiaryIBANID," + beneficiaryNameColumn + " as beneficiaryName,"
				+ paymentAmountColumn + " as paymentAmount," + transferCurrencyColumn + " as transferCurrency,"
				+ paymentDateColumn + " as paymentDate," + paymentPurposeShortCodeColumn
				+ " as paymentPurposeShortCode from " + paymentTable + " where " + orderingAccountIDColumn + " = "
				+ String.valueOf(id);

		try {
			List<PaymentDTO> listOfPayments = new ArrayList<>();
			List<Map<String, Object>> query = runner.query(sql, new MapListHandler());

			for (Map<String, Object> map : query) {
				PaymentDTOImpl paymentDTOImpl = new PaymentDTOImpl();
				JDBCIBANDAO jdbcibandao = new JDBCIBANDAO(dataSource);
				PaymentPurposeDAO paymentPurposeDAO = new PaymentPurposeDAO() {
					@Override
					public void save(PaymentPurposeDTO paymentPurpose) {
					}

					@Override
					public PaymentPurposeDTO get(String shortCode) {
						return null;
					}
				};

				paymentDTOImpl.setId((Integer) map.get(paymentIdColumn));
				paymentDTOImpl.setOrderingAccount(orderingAccount);
				paymentDTOImpl.setBeneficiaryIBAN(jdbcibandao.getById((Integer) map.get(beneficiaryIBANIDColumn)));
				paymentDTOImpl.setBeneficiaryName((String) map.get(beneficiaryNameColumn));
				paymentDTOImpl.setPaymentAmount((BigDecimal) map.get(paymentAmountColumn));
				paymentDTOImpl.setTransferCurrency((Currency) map.get(transferCurrencyColumn));
				paymentDTOImpl.setPaymentDate((LocalDateTime) map.get(paymentDateColumn));
				paymentDTOImpl
						.setPaymentPurpose(paymentPurposeDAO.get((String) map.get(paymentPurposeShortCodeColumn)));

				listOfPayments.add(paymentDTOImpl);
			}
			return listOfPayments;
		} catch (SQLException e) {
			throw new IllegalArgumentException();
		}
	}

}
