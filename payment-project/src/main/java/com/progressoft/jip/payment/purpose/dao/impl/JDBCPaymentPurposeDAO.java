package com.progressoft.jip.payment.purpose.dao.impl;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.progressoft.jip.payment.DAOException;
import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public class JDBCPaymentPurposeDAO implements PaymentPurposeDAO {

	private final QueryRunner queryRunner;
	private final IdDAO idDAO;
	private static final String TABLE_NAME = "payment_purpose";
	private static final String INSERT_PAYMENTPURPOSE_STATMENT = "insert into payment_purpose ;";

	public JDBCPaymentPurposeDAO(DataSource dataSource) {
		this.queryRunner = new QueryRunner(dataSource);
		this.idDAO = new IdDAOImpl(dataSource);
	}

	@Override
	public PaymentPurposeDTO save(PaymentPurposeDTO paymentPurpose) {
		if (paymentPurpose.getId() > 1) {

		}

		try {
			long id = idDAO.save(TABLE_NAME);
			this.queryRunner.update(INSERT_PAYMENTPURPOSE_STATMENT, id, paymentPurpose.getShortCode(),
					paymentPurpose.getDescription());
			// AccountDTOImpl accountDTO = constructNewAccountDTO(account, id);
			return null;
		} catch (SQLException e) {
			throw new DAOException("Error While Saving Account: " + e);
		}
	}

	@Override
	public PaymentPurposeDTO get(String shortCode) {
		return null;
	}

}
