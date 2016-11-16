package com.progressoft.jip.payment.account.dao;

import java.sql.SQLException;
import java.util.Currency;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.progressoft.jip.payment.DAOException;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.iban.dao.impl.JDBCIBANDAO;
import com.progressoft.jip.payment.iban.service.IBANPersistenceService;
import com.progressoft.jip.payment.iban.service.impl.IBANPersistenceServiceImpl;
import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;

public class JDBCAccountDAO implements AccountDAO {

	private final DataSource dataSource;

	private final QueryRunner queryRunner;
	private final IdDAO idDAO;
	private static final String TABLE_NAME = "account";

	public JDBCAccountDAO(DataSource dataSource) {
		this.dataSource = dataSource;
		this.queryRunner = new QueryRunner(dataSource);
		this.idDAO = new IdDAOImpl(dataSource);
	}

	public AccountDTO save(AccountDTO account) {
		String sql = "insert into account (_id,account_no,account_name,account_currency,"
				+ "account_status)values(?,?,?,?,?)";

		try {
			int index = account.getAccountStatus().getIndex();
			long maxId = idDAO.getMaxId(TABLE_NAME);
			int insertedRows = this.queryRunner.update(sql, maxId, account.getAccountNumber(), account.getAccountName(),
					account.getCurreny().getCurrencyCode(), account.getAccountStatus().getAccountStatus(index));
			if(insertedRows>0){
			idDAO.insert(TABLE_NAME);
			AccountDTOImpl accountDTO = constructAccount(account, maxId);
			return accountDTO;
			}
			else{
				throw new DAOException("Error While Inserting Account: " + account.getAccountName());

			}
				

		} catch (SQLException e) {
			throw new DAOException("Error While Inserting Account: " + account.getAccountName());
		}

	}

	private AccountDTOImpl constructAccount(AccountDTO account, long maxId) {
		AccountDTOImpl accountDTO = new AccountDTOImpl();
		accountDTO.setId(maxId);
		accountDTO.setAccountNumber(account.getAccountNumber());
		accountDTO.setAccountName(account.getAccountName());
		accountDTO.setIbandto(account.getIban());
		accountDTO.setCurrency(account.getCurreny());
		accountDTO.setAccountStatus(account.getAccountStatus());
		return accountDTO;
	}

	public AccountDTO update(AccountDTO account) {
		String sql="update account set account_no=? , account_name=?,"
		return null;
	}

	public boolean delete(String accountNumber) {
		return false;
	}

	public AccountDTO get(String accountNumber) {
		return null;
	}

	public AccountDTO getById(String id) {
		return null;
	}

	public Iterable<AccountDTO> getAll() {
		return null;
	}

}
