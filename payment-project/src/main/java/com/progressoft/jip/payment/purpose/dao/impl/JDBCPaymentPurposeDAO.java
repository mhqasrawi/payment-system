
package com.progressoft.jip.payment.purpose.dao.impl;

import java.sql.SQLException;
import java.util.Map;

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
	private static final String TABLE_NAME = "payment_purpose";
	private static final String INSERT_PAYMENTPURPOSE_STATMENT = "insert into payment_purpose values(?,?)";

	public JDBCPaymentPurposeDAO(DataSource dataSource) {
		this.queryRunner = new QueryRunner(dataSource);
		this.idDAO = new IdDAOImpl(dataSource);
	}

	@Override
	public PaymentPurposeDTO save(PaymentPurposeDTO paymentPurpose) {
		// if (paymentPurpose.getId() > 1) {
		//
		// }

		try {
			// long id = idDAO.save(TABLE_NAME);
			this.queryRunner.update(INSERT_PAYMENTPURPOSE_STATMENT, paymentPurpose.getShortCode(),
					paymentPurpose.getDescription());
			return paymentPurpose;
			// AccountDTOImpl accountDTO = constructNewAccountDTO(account, id);
		} catch (SQLException e) {
			throw new DAOException("Error While Saving Account: " + e);
		}
	}

	@Override
	public PaymentPurposeDTO get(String shortCode) {
		try {
			Map<String, Object> query = this.queryRunner.query("select * from payment_purpose where short_code=?",
					new MapHandler(), shortCode);

			PaymentPurposeDTOImpl paymentPurposeDTOImpl = new PaymentPurposeDTOImpl();
			paymentPurposeDTOImpl.setShortCode((String) query.get("short_code"));
			paymentPurposeDTOImpl.setDescription((String) query.get("description"));
			return paymentPurposeDTOImpl;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
