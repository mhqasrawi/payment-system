package com.progressoft.jip.payment.account.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.progressoft.jip.payment.DAOException;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTO.AccountStatus;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.iban.dao.impl.JDBCIBANDAO;
import com.progressoft.jip.payment.iban.service.IBANPersistenceService;
import com.progressoft.jip.payment.iban.service.impl.IBANPersistenceServiceImpl;
import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;

public class JDBCAccountDAO implements AccountDAO {


	private final QueryRunner queryRunner;
	private final IdDAO idDAO;
	private static final String TABLE_NAME = "account";

	public JDBCAccountDAO(DataSource dataSource) {
		this.queryRunner = new QueryRunner(dataSource);
		this.idDAO = new IdDAOImpl(dataSource);
	}

	public AccountDTO save(AccountDTO account) {
		if (account.getId() == 0) {
			update(account);
			constructNewAccountDTO(account, account.getId());
		}
		String insert_account = "insert into account (_id,account_no,account_name,account_currency,"
				+ "account_status)values(?,?,?,?,?)";

		try {
			int status = account.getAccountStatus().getIndex();
			long id = idDAO.insert(TABLE_NAME);
			int insertedRows = this.queryRunner.update(insert_account, id, account.getAccountNumber(), account.getAccountName(),
					account.getCurreny().getCurrencyCode(), status);
			if (insertedRows > 0) {
				AccountDTOImpl accountDTO = constructNewAccountDTO(account, id);
				return accountDTO;
			} else {
				throw new DAOException("Error While Saving Account: " + account.getAccountName());

			}

		} catch (SQLException e) {
			throw new DAOException("Error While Saving Account: " + account.getAccountName());
		}

	}

	private AccountDTOImpl constructNewAccountDTO(AccountDTO account, long maxId) {
		AccountDTOImpl accountDTO = new AccountDTOImpl();
		accountDTO.setId(maxId);
		accountDTO.setAccountNumber(account.getAccountNumber());
		accountDTO.setAccountName(account.getAccountName());
		accountDTO.setIbandto(account.getIban());
		accountDTO.setCurrency(account.getCurreny());
		accountDTO.setAccountStatus(account.getAccountStatus());
		return accountDTO;
	}

	private void update(AccountDTO account) {
		String update_account = "update account set account_no=? , account_name=?,account_currency=?,account_status WHERE account_id=?";
		try {
			int updated = this.queryRunner.update(update_account, account.getId());
			if (updated == 0)
				throw new DAOException("Error While Updating Account: " + account.getAccountNumber());

		} catch (SQLException e) {
			throw new DAOException("Error While Updating Account: " + account.getAccountNumber());
		}
	}

	public boolean delete(String accountNumber) {
		String delete_account = "delete from account WHERE account_number=?";
		try {
			int deleted = this.queryRunner.update(delete_account, accountNumber);
			if (deleted == 0)
				throw new DAOException("Error While Deleting Account : " + accountNumber);
		} catch (SQLException e) {
			throw new DAOException("Error While Deleting Account : " + accountNumber);
		}
		return true;
	}

	public AccountDTO get(String accountNumber, IBANPersistenceService ibanPersistenceServiceImpl) {
		AccountDTOImpl accountDTO = new AccountDTOImpl();
		String sql = "select  account_id , account_name," + "account_currency,account_status" + " from account"
				+ " WHERE account_number= ' " + accountNumber + "'";

		try {
			Map<String, Object> account = this.queryRunner.query(sql, new MapHandler());
			if (account != null) {
				accountDTO.setAccountNumber(accountNumber);
				populateAccountDTO(ibanPersistenceServiceImpl, accountDTO, account);

			}
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching Account Number: " + accountNumber);
		}
		return accountDTO;

	}

	public AccountDTO getById(String id, IBANPersistenceService ibanPersistenceServiceImpl) {
		AccountDTOImpl accountDTO = new AccountDTOImpl();
		String sql = "select  account_number , account_name," + "account_currency,account_status" + " from account"
				+ " WHERE account_id= ' " + id + "'";

		try {
			Map<String, Object> account = this.queryRunner.query(sql, new MapHandler());
			if (account != null) {
				accountDTO.setId((Long) account.get("account_id"));

				populateAccountDTO(ibanPersistenceServiceImpl, accountDTO, account);

			}
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching Account ID: " + id);
		}
		return accountDTO;
	}

	public Iterable<AccountDTO> getAll(IBANPersistenceService ibanPersistenceServiceImpl) {
		String get_all_accounts = "select  account_number , account_id , account_name,"
				+ "account_currency,account_status" + " from account";
		
		final List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>();
		try {
			List<Map<String, Object>> accounts = this.queryRunner.query(get_all_accounts, new MapListHandler());
			for (Map<String, Object> accountMap : accounts) {
				
				AccountDTOImpl accountDTOImpl = new AccountDTOImpl();
				
				accountDTOImpl.setId((Long) accountMap.get("account_id"));
				accountDTOImpl.setId((Long) accountMap.get("account_number"));
				
				populateAccountDTO(ibanPersistenceServiceImpl, accountDTOImpl, accountMap);
				accountsDTO.add(accountDTOImpl);

			}
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching All Accounts");
		}

		return new Iterable<AccountDTO>() {
			
			public Iterator<AccountDTO> iterator() {
				return accountsDTO.iterator();
			}
		};
	}

	private void populateAccountDTO(IBANPersistenceService ibanPersistenceServiceImpl, AccountDTOImpl accountDTO,
			Map<String, Object> account) {
		accountDTO.setAccountName((String) account.get("account_name"));

		Currency currency = Currency.getInstance((String) account.get("account_currency"));
		accountDTO.setCurrency(currency);

		Object status = account.get("account_status");
		AccountStatus accountStatus = AccountStatus.values()[(Integer) status];
		accountDTO.setAccountStatus(accountStatus);

		IBANDTO iban = ibanPersistenceServiceImpl.getIBANById(accountDTO.getAccountNumber());
		accountDTO.setIbandto(iban);
	}
}
