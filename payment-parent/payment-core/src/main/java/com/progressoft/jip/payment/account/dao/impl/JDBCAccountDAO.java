package com.progressoft.jip.payment.account.dao.impl;

import com.progressoft.jip.payment.DAOException;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTO.AccountStatus;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.customer.CustomerDTOImpl;
import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class JDBCAccountDAO implements AccountDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCAccountDAO.class);

    private static final String DELETE_ACCOUNT_STATMENT = "delete from account WHERE account_number" + "=?";

    private static final String SELECT_ALL_ACCOUNT = "select  account_number , id , account_name,"
            + "account_currency,account_status , customer_name , iban_id , rule,rule_info,balance " + " from account";
    private static final String UPDATE_ACCOUNT_STATMENT = "update account set account_number=? , account_name=?,account_currency=?,account_status =?,customer_name=?,rule=?,rule_info =?,balance=? WHERE id=?";
    private static final String INSERT_ACCOUNT_STATMENT = "insert into account (id,account_number,account_name,account_currency,"
            + "account_status,customer_name,iban_id,rule,rule_info,balance)values(?,?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_STATMENT_BASED_ON_ACCOUNT_NUMBER = "select  id , account_name,"
            + "account_currency,account_status , customer_name,iban_id,rule,rule_info,balance" + " from account"
            + " WHERE account_number='";
    private static final String SELECT_STATMENT_BASED_ON_ACCOUNT_ID = "select  account_number , account_name,"
            + "account_currency,account_status,customer_name,iban_id,rule,rule_info,balance" + " from account"
            + " WHERE id=";
    private static final String SELECT_STATMENT_BASED_ON_CUSTOMER_NAME = "select  account_number , id , account_name,"
            + "account_currency,account_status , customer_name,rule,rule_info,balance"
            + " from account WHERE customer_name='";
    private static final String TABLE_NAME = "account";
    private final QueryRunner queryRunner;
    private final IdDAO idDAO;
    private DataSource dataSource;

    public JDBCAccountDAO(DataSource dataSource) {
        this.dataSource = dataSource;
        this.queryRunner = new QueryRunner(dataSource);
        this.idDAO = new IdDAOImpl(dataSource);
    }

    @Override
    public AccountDTO save(AccountDTO account) {
        if (account.getId() > 1) {
            return updateIfExist(account);
        }
        try {
            int status = account.getAccountStatus().getIndex();
            int id = idDAO.save(TABLE_NAME);
            this.queryRunner.update(INSERT_ACCOUNT_STATMENT, id, account.getAccountNumber(), account.getAccountName(),
                    account.getCurreny().getCurrencyCode(), status, account.getCustomerDTO().getName(),
                    account.getIban().getId(), account.getPaymentRule(), account.getPaymentRuleInfo(),
                    account.getBalance());
            AccountDTOImpl accountDTO = constructNewAccountDTO(account, id);
            return accountDTO;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
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
        accountDTO.setPaymentRule(account.getPaymentRule());
        accountDTO.setPaymentRuleInfo(account.getPaymentRuleInfo());
        accountDTO.setBalance(account.getBalance());
        return accountDTO;
    }

    private void update(AccountDTO account) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            this.dataSource.getConnection().setAutoCommit(false);
            this.queryRunner.query(connection,
                    String.format("select 1 from %s where id=%d for update", TABLE_NAME, account.getId()),
                    new MapHandler());
            int update = this.queryRunner.update(UPDATE_ACCOUNT_STATMENT, account.getAccountNumber(),
                    account.getAccountName(), account.getCurreny().getCurrencyCode(),
                    account.getAccountStatus().getIndex(), account.getCustomerDTO().getName(), account.getPaymentRule(),
                    account.getPaymentRuleInfo(), account.getBalance().toString(), account.getId());
            System.out.println(update);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Error While Updating Account: " + account.getAccountNumber(), e);
        } finally {
            try {
                if (connection != null) {
                    connection.commit();
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public boolean delete(String accountNumber) {
        try {
            int deleted = this.queryRunner.update(DELETE_ACCOUNT_STATMENT, accountNumber);
            if (deleted == 0)
                throw new DAOException("NO Account Deleted : " + accountNumber);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Error While Deleting Account : " + accountNumber, e);
        }
        return true;
    }

    @Override
    public AccountDTO get(String accountNumber) {
        String sql = SELECT_STATMENT_BASED_ON_ACCOUNT_NUMBER + accountNumber + "'";

        try {
            Map<String, Object> account = this.queryRunner.query(sql, new MapHandler());
            if (account == null) {
                LOGGER.error("No Account With Number");
                throw new DAOException("No Account With Number " + accountNumber);
            }
            AccountDTOImpl accountDTO = populateAccountDTO(account);
            accountDTO.setAccountNumber(accountNumber);

            return accountDTO;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Error While Fetching Account Number: " + accountNumber, e);
        }

    }

    @Override
    public AccountDTO getById(int id) {
        String sql = SELECT_STATMENT_BASED_ON_ACCOUNT_ID + id;

        try {
            Map<String, Object> account = this.queryRunner.query(sql, new MapHandler());
            if (account != null) {

                AccountDTOImpl accountDTO = populateAccountDTO(account);
                accountDTO.setId(id);

                return accountDTO;

            } else {
                LOGGER.error("cannot fetch account by id Table Is Empty" + id);

                throw new DAOException("cannot fetch account by id Table Is Empty" + id);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("cannot fetch account by id " + id, e);
        }
    }

    @Override
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
            LOGGER.error(e.getMessage(), e);
            throw new DAOException("Error While Fetching All Accounts", e);
        }
        if (accountsDTO.isEmpty()) {
            LOGGER.error("There's No Accounts , Table Is Empty");
            throw new DAOException("There's No Accounts , Table Is Empty");
        }

        return new Iterable<AccountDTO>() {

            public Iterator<AccountDTO> iterator() {
                return accountsDTO.iterator();
            }
        };
    }

    @Override
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
            LOGGER.error(e.getMessage(), e);
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
        accountDTO.setPaymentRule((String) account.get("rule"));
        accountDTO.setPaymentRuleInfo((String) account.get("rule_info"));
        accountDTO.setBalance(new BigDecimal((String) (account.get("balance") == null ? "0" : account.get("balance"))));
        if (account.get("id") != null)
            accountDTO.setId((Integer) account.get("id"));
        return accountDTO;
    }
}