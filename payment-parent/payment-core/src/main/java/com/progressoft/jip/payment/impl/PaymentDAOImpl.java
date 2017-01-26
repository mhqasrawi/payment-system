package com.progressoft.jip.payment.impl;

import com.progressoft.jip.payment.DAOException;
import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.account.dao.impl.JDBCAccountDAO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.dao.impl.JDBCIBANDAO;
import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;
import com.progressoft.jip.payment.purpose.dao.impl.JDBCPaymentPurposeDAO;
import com.progressoft.jip.payment.purpose.dao.impl.PaymentPurposeDAO;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

public class PaymentDAOImpl implements PaymentDAO {

    private static final String TABLE_NAME = "payment";
    private DataSource dataSource;
    private String paymentIdColumn = "id";
    private String orderingAccountIDColumn = "order_account_number";
    private String beneficiaryIBANIDColumn = "benficiary_account_iban";
    private String beneficiaryNameColumn = "benficiary_account_name";
    private String paymentAmountColumn = "payment_amount";
    private String transferCurrencyColumn = "transfer_currency";
    private String paymentDateColumn = "payment_date";
    private String paymentPurposeShortCodeColumn = "payment_purpose";
    private IdDAO idDAO;

    public PaymentDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        idDAO = new IdDAOImpl(dataSource);

    }

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {

        int id = idDAO.save(TABLE_NAME);
        QueryRunner runner = new QueryRunner(dataSource);
        String sql = "insert into " + TABLE_NAME + " (" + paymentIdColumn + "," + orderingAccountIDColumn + ","
                + beneficiaryIBANIDColumn + "," + beneficiaryNameColumn + "," + paymentAmountColumn + ","
                + transferCurrencyColumn + "," + paymentDateColumn + "," + paymentPurposeShortCodeColumn
                + ") values(?,?,?,?,?,?,?,?)";
        try {

            int update = runner.update(sql, id, paymentDTO.getOrderingAccount().getAccountNumber(),
                    paymentDTO.getBeneficiaryIBAN().getId(), paymentDTO.getBeneficiaryName(),
                    paymentDTO.getPaymentAmount(), paymentDTO.getTransferCurrency().getCurrencyCode(),
                    LocalDateTime.now().toString(), paymentDTO.getPaymentPurpose().getShortCode());
            if (update > 0) {
                PaymentDTOImpl paymentDTOImpl = new PaymentDTOImpl();

                paymentDTOImpl.setBeneficiaryIBAN(paymentDTO.getBeneficiaryIBAN());
                paymentDTOImpl.setBeneficiaryName(paymentDTO.getBeneficiaryName());
                paymentDTOImpl.setId(id);
                paymentDTOImpl.setOrderingAccount(paymentDTO.getOrderingAccount());
                paymentDTOImpl.setPaymentPurpose(paymentDTO.getPaymentPurpose());

                paymentDTOImpl.setTransferCurrency(paymentDTO.getTransferCurrency());

                paymentDTOImpl.setPaymentAmount(paymentDTO.getPaymentAmount());
                paymentDTOImpl.setPaymentDate(paymentDTO.getPaymentDate());
                return paymentDTOImpl;
            }
            throw new DAOException("cannot insert payment ");
        } catch (SQLException e) {
            throw new DAOException("error while saving Payment ", e);

        }
    }

    @Override
    public PaymentDTO getById(int id) {
        QueryRunner runner = new QueryRunner(dataSource);

        String sql = "select " + " * from " + TABLE_NAME + " where id = " + id;
        try {
            Map<String, Object> paymentMap = runner.query(sql, new MapHandler());
            PaymentDTOImpl paymentDTOImpl = new PaymentDTOImpl();

            JDBCAccountDAO jdbcAccountDAO = new JDBCAccountDAO(dataSource);
            JDBCIBANDAO jdbcibandao = new JDBCIBANDAO(dataSource);

            PaymentPurposeDAO paymentPurposeDAO = new JDBCPaymentPurposeDAO(dataSource);

            paymentDTOImpl.setId(id);
            paymentDTOImpl.setOrderingAccount(jdbcAccountDAO.get((String) paymentMap.get(orderingAccountIDColumn)));
            paymentDTOImpl.setBeneficiaryIBAN(jdbcibandao.getById((Integer) paymentMap.get(beneficiaryIBANIDColumn)));
            paymentDTOImpl.setBeneficiaryName((String) paymentMap.get(beneficiaryNameColumn));
            paymentDTOImpl.setPaymentAmount((BigDecimal) paymentMap.get(paymentAmountColumn));
            paymentDTOImpl.setTransferCurrency(Currency.getInstance((String) paymentMap.get(transferCurrencyColumn)));
            final String date = (String) paymentMap.get(paymentDateColumn);
            final LocalDateTime parsedTime = LocalDateTime.parse(date);
            paymentDTOImpl.setPaymentDate(parsedTime);
            paymentDTOImpl
                    .setPaymentPurpose(paymentPurposeDAO.get((String) paymentMap.get(paymentPurposeShortCodeColumn)));

            return paymentDTOImpl;
        } catch (SQLException e) {
            throw new DAOException("error while getting Payment By ID : : " + id, e);

        }
    }

    @Override
    public Iterable<PaymentDTO> getAll() {
        QueryRunner runner = new QueryRunner(dataSource);

        String sql = "select " + " * from " + TABLE_NAME;

        try {
            List<PaymentDTO> listOfPayments = new ArrayList<>();
            List<Map<String, Object>> query = runner.query(sql, new MapListHandler());
            for (Map<String, Object> map : query) {
                PaymentDTOImpl paymentDTOImpl = new PaymentDTOImpl();
                JDBCAccountDAO jdbcAccountDAO = new JDBCAccountDAO(dataSource);
                JDBCIBANDAO jdbcibandao = new JDBCIBANDAO(dataSource);

                PaymentPurposeDAO paymentPurposeDAO = new JDBCPaymentPurposeDAO(dataSource);
                paymentDTOImpl.setId((Integer) map.get(paymentIdColumn));
                paymentDTOImpl.setOrderingAccount(jdbcAccountDAO.getById((Integer) map.get(orderingAccountIDColumn)));
                paymentDTOImpl.setBeneficiaryIBAN(jdbcibandao.getById((Integer) map.get(beneficiaryIBANIDColumn)));
                paymentDTOImpl.setBeneficiaryName((String) map.get(beneficiaryNameColumn));
                paymentDTOImpl.setPaymentAmount((BigDecimal) map.get(paymentAmountColumn));
                paymentDTOImpl.setTransferCurrency((Currency) map.get(transferCurrencyColumn));
                final String dateStr = (String) map.get(paymentDateColumn);
                LocalDateTime date = LocalDateTime.parse(dateStr);
                paymentDTOImpl.setPaymentDate(date);
                paymentDTOImpl
                        .setPaymentPurpose(paymentPurposeDAO.get((String) map.get(paymentPurposeShortCodeColumn)));

                listOfPayments.add(paymentDTOImpl);
            }
            return listOfPayments;
        } catch (SQLException e) {
            throw new DAOException("error while getting All Payments ", e);

        }
    }

    @Override
    public Iterable<PaymentDTO> get(String accountNumber) {
        QueryRunner runner = new QueryRunner(dataSource);
        AccountDAO accountDAO = new JDBCAccountDAO(dataSource);
        AccountDTOImpl orderingAccount = (AccountDTOImpl) accountDAO.get(accountNumber);


        String sql = "select " + " * from " + TABLE_NAME + " where " + orderingAccountIDColumn + " = " + accountNumber;

        try {
            List<PaymentDTO> listOfPayments = new ArrayList<>();
            List<Map<String, Object>> query = runner.query(sql, new MapListHandler());

            for (Map<String, Object> map : query) {
                PaymentDTOImpl paymentDTOImpl = new PaymentDTOImpl();
                JDBCIBANDAO jdbcibandao = new JDBCIBANDAO(dataSource);
                PaymentPurposeDAO paymentPurposeDAO = new JDBCPaymentPurposeDAO(dataSource);

                paymentDTOImpl.setId((Integer) map.get(paymentIdColumn));
                paymentDTOImpl.setOrderingAccount(orderingAccount);

                IBANDTO benificaryIBAN = jdbcibandao.getById((Integer) map.get(beneficiaryIBANIDColumn));
                IBANDTO orderingIBAN = jdbcibandao.getById(orderingAccount.getIbanId());
                orderingAccount.setIbandto(orderingIBAN);
                paymentDTOImpl.setBeneficiaryIBAN(benificaryIBAN);
                paymentDTOImpl.setBeneficiaryName((String) map.get(beneficiaryNameColumn));
                paymentDTOImpl.setPaymentAmount((BigDecimal) map.get(paymentAmountColumn));
                paymentDTOImpl.setTransferCurrency(Currency.getInstance((String) map.get(transferCurrencyColumn)));
                final String dateStr = (String) map.get(paymentDateColumn);
                LocalDateTime date = LocalDateTime.parse(dateStr);
                paymentDTOImpl.setPaymentDate(date);
                paymentDTOImpl
                        .setPaymentPurpose(paymentPurposeDAO.get((String) map.get(paymentPurposeShortCodeColumn)));

                listOfPayments.add(paymentDTOImpl);
            }
            if (!listOfPayments.isEmpty())
                return listOfPayments;
            throw new NoPaymentsException("There's No Payments done before for account " + accountNumber);
        } catch (SQLException e) {
            throw new DAOException("error while getting Payments for account number: " + accountNumber, e);
        }
    }

}
