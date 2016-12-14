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
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.customer.CustomerDTOImpl;
import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;

public class JDBCAccountDAO implements AccountDAO {

	private static final String DELETE_ACCOUNT_STATMENT = "delete from account WHERE account_number" + "=?";

	private static final String SELECT_ALL_ACCOUNT = "select  account_number , id , account_name,"
			+ "account_currency,account_status , customer_name , iban_id" + " from account";
	private static final String UPDATE_ACCOUNT_STATMENT = "update account set account_number=? , account_name=?,account_currency=?,account_status =?,customer_name=? WHERE id=?";
	private static final String INSERT_ACCOUNT_STATMENT = "insert into account (id,account_number,account_name,account_currency,"
			+ "account_status,customer_name,iban_id)values(?,?,?,?,?,?,?)";
	private static final String SELECT_STATMENT_BASED_ON_ACCOUNT_NUMBER = "select  id , account_name,"
			+ "account_currency,account_status , customer_name,iban_id" + " from account" + " WHERE account_number='";
	private static final String SELECT_STATMENT_BASED_ON_ACCOUNT_ID = "select  account_number , account_name,"
			+ "account_currency,account_status,customer_name,iban_id" + " from account" + " WHERE id=";
	private static final String SELECT_STATMENT_BASED_ON_CUSTOMER_NAME = "select  account_number , id , account_name,"
			+ "account_currency,account_status , customer_name" + " from account WHERE customer_name='";
	private final QueryRunner queryRunner;
	private final IdDAO idDAO;
	private static final String TABLE_NAME = "account";

	public JDBCAccountDAO(DataSource dataSource) {
		this.queryRunner = new QueryRunner(dataSource);
		this.idDAO = new IdDAOImpl(dataSource);
	}

	public AccountDTO save(AccountDTO account) {
		if (account.getId() > 1) {
			return updateIfExist(account);
		}
		try {
			int status = account.getAccountStatus().getIndex();
			int id = idDAO.save(TABLE_NAME);
			this.queryRunner.update(INSERT_ACCOUNT_STATMENT, id, account.getAccountNumber(), account.getAccountName(),
					account.getCurreny().getCurrencyCode(), status, account.getCustomerDTO().getName(),
					account.getIban().getId());
			AccountDTOImpl accountDTO = constructNewAccountDTO(account, id);
			return accountDTO;
		} catch (SQLException e) {
			throw new DAOException("Error While Saving Account: " + account.getAccountName(), e);
		}

	}

	private AccountDTO updateIfExist(AccountDTO account) {
		update(account);
		return constructNewAccountDTO(account, account.getId());
	}

	private AccountDTOImpl constructNewAccountDTO(AccountDTO account, int maxId) {
		AccountDTOImpl accountDTO = new AccountDTOImpl();
		accountDTO.setId(maxId);
		accountDTO.setAccountNumber(account.getAccountNumber());
		accountDTO.setAccountName(account.getAccountName());
		accountDTO.setIbandto(account.getIban());
		accountDTO.setIbanId(account.getIbanId());
		accountDTO.setCurrency(account.getCurreny());
		accountDTO.setAccountStatus(account.getAccountStatus());
		accountDTO.setCustomerDTO(account.getCustomerDTO());
		accountDTO.setIbanId(account.getIbanId());
		return accountDTO;
	}

	private void update(AccountDTO account) {
		try {
			this.queryRunner.update(UPDATE_ACCOUNT_STATMENT, account.getAccountNumber(), account.getAccountName(),
					account.getCurreny().getCurrencyCode(), account.getAccountStatus().getIndex(),
					account.getCustomerDTO().getName(), account.getId());
		} catch (SQLException e) {
			throw new DAOException("Error While Updating Account: " + account.getAccountNumber(), e);
		}
	}

	public boolean delete(String accountNumber) {
		try {
			int deleted = this.queryRunner.update(DELETE_ACCOUNT_STATMENT, accountNumber);
			if (deleted == 0)
				throw new DAOException("NO Account Deleted : " + accountNumber);
		} catch (SQLException e) {
			throw new DAOException("Error While Deleting Account : " + accountNumber, e);
		}
		return true;
	}

	public AccountDTO get(String accountNumber) {
		String sql = SELECT_STATMENT_BASED_ON_ACCOUNT_NUMBER + accountNumber + "'";

		try {
			Map<String, Object> account = this.queryRunner.query(sql, new MapHandler());
			if (account == null)
				throw new DAOException("No Account With Number " + accountNumber);
			AccountDTOImpl accountDTO = populateAccountDTO(account);
			accountDTO.setAccountNumber(accountNumber);

			return accountDTO;
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching Account Number: " + accountNumber, e);
		}

	}

	public AccountDTO getById(int id) {
		String sql = SELECT_STATMENT_BASED_ON_ACCOUNT_ID + id;

		try {
			Map<String, Object> account = this.queryRunner.query(sql, new MapHandler());
			if (account != null) {

				AccountDTOImpl accountDTO = populateAccountDTO(account);
				accountDTO.setId(id);

				return accountDTO;

			} else {
				throw new DAOException("cannot fetch account by id Table Is Empty" + id);
			}
		} catch (SQLException e) {
			throw new DAOException("cannot fetch account by id " + id, e);
		}
	}

	public Iterable<AccountDTO> getAll() {
		final List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>();
		try {
			List<Map<String, Object>> accounts = this.queryRunner.query(SELECT_ALL_ACCOUNT, new MapListHandler());
			for (Map<String, Object> accountMap : accounts) {
				AccountDTOImpl accountDTOImpl = populateAccountDTO(accountMap);
				accountDTOImpl.setId((int) accountMap.get("id"));
				accountDTOImpl.setAccountNumber((String) accountMap.get("account_number"));
				accountsDTO.add(accountDTOImpl);
			}
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching All Accounts", e);
		}
		if (accountsDTO.size() == 0) {
			throw new DAOException("There's No Accounts , Table Is Empty");
		}

		return new Iterable<AccountDTO>() {

			public Iterator<AccountDTO> iterator() {
				return accountsDTO.iterator();
			}
		};
	}

	public Iterable<AccountDTO> getAccountsByCustomerName(String customerName) {
		String get_all_accounts = SELECT_STATMENT_BASED_ON_CUSTOMER_NAME + customerName + "'";

		final List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>();
		try {
			List<Map<String, Object>> accounts = this.queryRunner.query(get_all_accounts, new MapListHandler());
			for (Map<String, Object> accountMap : accounts) {
				AccountDTOImpl accountDTOImpl = populateAccountDTO(accountMap);
				accountDTOImpl.setId((int) accountMap.get("account_id"));
				accountDTOImpl.setAccountNumber((String) accountMap.get("account_number"));

				accountsDTO.add(accountDTOImpl);

			}
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching All Accounts for customer: " + customerName, e);
		}

		return new Iterable<AccountDTO>() {

			public Iterator<AccountDTO> iterator() {
				return accountsDTO.iterator();
			}
		};
	}

	private AccountDTOImpl populateAccountDTO(Map<String, Object> account) {
		AccountDTOImpl accountDTO = new AccountDTOImpl();
		accountDTO.setAccountName((String) account.get("account_name"));
		Currency currency = Currency.getInstance((String) account.get("account_currency"));
		accountDTO.setCurrency(currency);

		Object status = account.get("account_status");
		AccountStatus accountStatus = AccountStatus.values()[(Integer) status];
		accountDTO.setAccountStatus(accountStatus);
		CustomerDTOImpl customerDTOImpl = new CustomerDTOImpl();
		customerDTOImpl.setName((String) account.get("customer_name"));
		accountDTO.setCustomerDTO(customerDTOImpl);
		accountDTO.setIbanId(Integer.parseInt(account.get("iban_id").toString()));
		accountDTO.setAccountNumber((String) account.get("account_number"));
		if (account.get("id") != null)
			accountDTO.setId((Integer)account.get("id"));
		return accountDTO;

	}

}
