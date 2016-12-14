
package com.progressoft.jip.payment.purpose.dao.impl;

import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

import com.progressoft.jip.payment.DAOException;
import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTOImpl;

public class JDBCPaymentPurposeDAO implements PaymentPurposeDAO {

	private final QueryRunner queryRunner;
	private final IdDAO idDAO;
	private static final String INSERT_PAYMENTPURPOSE_STATMENT = "insert into payment_purpose values(?,?)";

	@Inject
	public JDBCPaymentPurposeDAO(DataSource dataSource) {
		this.queryRunner = new QueryRunner(dataSource);
		this.idDAO = new IdDAOImpl(dataSource);
	}

	@Override
	public PaymentPurposeDTO save(PaymentPurposeDTO paymentPurpose) {
		if (Objects.isNull(paymentPurpose) || Objects.isNull(paymentPurpose.getDescription())
				|| Objects.isNull(paymentPurpose.getShortCode()))
			throw new DAOException("Null Pointer ");

		try {
			this.queryRunner.update(INSERT_PAYMENTPURPOSE_STATMENT, paymentPurpose.getShortCode(),
					paymentPurpose.getDescription());
			return paymentPurpose;
		} catch (SQLException e) {
			throw new DAOException("Error While Saving Account: " + e);
		}
	}

	@Override
	public PaymentPurposeDTO get(String shortCode) {
		if (Objects.isNull(shortCode))
			throw new DAOException("Null Short Code");
		try {
			Map<String, Object> query = this.queryRunner.query("select * from payment_purpose where short_code=?",
					new MapHandler(), shortCode);
			if (query == null)
				throw new DAOException("No Payment With Short Code "+shortCode);
			PaymentPurposeDTOImpl paymentPurposeDTOImpl = new PaymentPurposeDTOImpl();
			paymentPurposeDTOImpl.setShortCode((String) query.get("short_code"));
			paymentPurposeDTOImpl.setDescription((String) query.get("description"));
			return paymentPurposeDTOImpl;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
