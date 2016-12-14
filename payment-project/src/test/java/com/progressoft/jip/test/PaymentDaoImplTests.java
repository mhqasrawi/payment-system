package com.progressoft.jip.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.junit.Test;

import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.account.dao.impl.JDBCAccountDAO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.account.service.AccountPersistenceServiceImpl;
import com.progressoft.jip.payment.customer.CustomerDTOImpl;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.iban.dao.impl.JDBCIBANDAO;
import com.progressoft.jip.payment.iban.service.IBANPersistenceService;
import com.progressoft.jip.payment.iban.service.impl.IBANPersistenceServiceImpl;
import com.progressoft.jip.payment.impl.PaymentDAOImpl;
import com.progressoft.jip.payment.impl.PaymentDTOImpl;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTOImpl;
import com.progressoft.jip.payment.purpose.dao.impl.JDBCPaymentPurposeDAO;
import com.progressoft.jip.payment.purpose.dao.impl.PaymentPurposeDAO;
import com.progressoft.jip.payment.purpose.service.PaymentPurposePersistenceService;
import com.progressoft.jip.payment.purpose.service.PaymentPurposePersistenceServiceImpl;
import com.progressoft.jip.payment.service.PaymentPersistenceService;

import static org.junit.Assert.assertEquals;

import org.apache.commons.collections.IteratorUtils;
import org.junit.Before;

public class PaymentDaoImplTests extends DataSourceConfig {

	DataSource dataSource;

	PaymentPersistenceService paymentPersistenceService;
	AccountPersistenceService accountPersistenceService;
	private IBANPersistenceService ibanPersistenceService;
	public PaymentDAO paymentDAO;
	private PaymentPurposePersistenceService paymentPurposePersistenceService;;

	@Before
	public void setup() throws Exception {
		this.dataSource = configureDataSource();
		paymentDAO = new PaymentDAOImpl(dataSource);
		AccountDAO accountDAO = new JDBCAccountDAO(dataSource);
		IBANDAO ibandao = new JDBCIBANDAO(dataSource);
		ibanPersistenceService = new IBANPersistenceServiceImpl(ibandao);
		PaymentPurposeDAO paymentPurposeDAO = new JDBCPaymentPurposeDAO(dataSource);
		paymentPurposePersistenceService = new PaymentPurposePersistenceServiceImpl(paymentPurposeDAO);
		accountPersistenceService = new AccountPersistenceServiceImpl(accountDAO, ibanPersistenceService);
		// paymentPersistenceService=new PaymentPe
	}

	@Test
	public void GivenEmptyTable_WhenInsertPayment_ThenIDNotNull() {

		insertPayment(1);

	}

	@Test
	public void GivenInsertedPayment_WhenGetById_ThenShouldReturned() {
		PaymentDTO insertPayment = insertPayment(1);
		assertEquals(2, insertPayment.getOrderingAccount().getId());
		assertEquals(2, insertPayment.getId());
		PaymentDTO byId = paymentDAO.getById(insertPayment.getId());
		assertEquals(byId.getId(), insertPayment.getId());
	}

	@Test
	public void GivenTwoInsertedPayment_WhenGetAllPAymentsByAccountNumber_ThenTwoPAymentsReturned() {
		AccountDTOImpl orderingAccount = buildAccountDTO(0);
		AccountDTOImpl benficaryAccount = buildAccountDTO(1);
		AccountDTO save = accountPersistenceService.save(orderingAccount);
		AccountDTO save2 = accountPersistenceService.save(benficaryAccount);
		//PaymentDTO insertPayment = insertPayment2(orderingAccount, benficaryAccount, 0);
	//	PaymentDTO insertPayment2 = insertPayment2(orderingAccount, benficaryAccount, 1);
		Iterable<PaymentDTO> all = paymentDAO.get("12340");
		final Iterator<PaymentDTO> iterator = all.iterator();
		long count = Stream.of(all).count();
		assertEquals(2, count);
	}

	private PaymentDTO insertPayment(int i) {
		AccountDTOImpl orderingAccount = buildAccountDTO(i);
		AccountDTOImpl benficaryAccount = buildAccountDTO(i);
		IBANDTO savedIBAN = getIbandto(0);
		orderingAccount.setIbandto(savedIBAN);
		benficaryAccount.setIbandto(savedIBAN);
		AccountDTO save = accountPersistenceService.save(orderingAccount);
		AccountDTO save2 = accountPersistenceService.save(benficaryAccount);
		assertEquals(2, save.getId());
		assertEquals(3, save2.getId());

		PaymentDTOImpl paymentDTOImpl = new PaymentDTOImpl();

		paymentDTOImpl.setBeneficiaryIBAN(save2.getIban());
		paymentDTOImpl.setOrderingAccount(save);
		paymentDTOImpl.setBeneficiaryName(save.getCustomerDTO().getName());

		paymentDTOImpl.setTransferCurrency(Currency.getInstance("JOD"));
		paymentDTOImpl.setPaymentAmount(new BigDecimal("1000"));
		PaymentPurposeDTOImpl paymentPurpose = new PaymentPurposeDTOImpl();
		paymentPurpose.setDescription("desc");
		paymentPurpose.setShortCode("sala");
		PaymentPurposeDTO save3 = paymentPurposePersistenceService.save(paymentPurpose);
		assertEquals(paymentPurpose.getShortCode(), save3.getShortCode());
		paymentDTOImpl.setPaymentPurpose(paymentPurpose);
		final String date = LocalDateTime.now().toString();
		paymentDTOImpl.setPaymentDate(LocalDateTime.now());
		PaymentDTO save4 = paymentDAO.save(paymentDTOImpl);
		assertEquals(2, save4.getId());
		assertEquals(2, save4.getOrderingAccount().getId());
		return save4;
	}

	private PaymentDTO insertPayment2(AccountDTOImpl orderingAccount, AccountDTOImpl benficaryAccount, int i) {

		IBANDTO savedIBAN = getIbandto(0);
		orderingAccount.setIbandto(savedIBAN);
		benficaryAccount.setIbandto(savedIBAN);
		// AccountDTO save = accountPersistenceService.save(orderingAccount);
		// AccountDTO save2 = accountPersistenceService.save(benficaryAccount);
		// assertEquals(2, save.getId());
		// assertEquals(3, save2.getId());

		PaymentDTOImpl paymentDTOImpl = new PaymentDTOImpl();

		paymentDTOImpl.setBeneficiaryIBAN(benficaryAccount.getIban());
		paymentDTOImpl.setOrderingAccount(orderingAccount);
		paymentDTOImpl.setBeneficiaryName(orderingAccount.getCustomerDTO().getName());

		paymentDTOImpl.setTransferCurrency(Currency.getInstance("JOD"));
		paymentDTOImpl.setPaymentAmount(new BigDecimal("1000"));
		PaymentPurposeDTOImpl paymentPurpose = new PaymentPurposeDTOImpl();
		paymentPurpose.setDescription("desc" + i);
		paymentPurpose.setShortCode("sala" + i);
		PaymentPurposeDTO save3 = paymentPurposePersistenceService.save(paymentPurpose);
		// assertEquals(paymentPurpose.getShortCode(), save3.getShortCode());
		paymentDTOImpl.setPaymentPurpose(paymentPurpose);
		final String date = new Date(System.currentTimeMillis()).toString();
		paymentDTOImpl.setPaymentDate(LocalDateTime.now());
		PaymentDTO save4 = paymentDAO.save(paymentDTOImpl);
		// assertEquals(2, save4.getId());
		// assertEquals(2, save4.getOrderingAccount().getId());
		return save4;
	}

	private AccountDTO saveAccountDTO(AccountDTOImpl accountDTO, IBANDTO savedIBAN) {
		accountDTO.setIbandto(savedIBAN);
		accountDTO.setIbanId(savedIBAN.getId());
		return accountPersistenceService.save(accountDTO);
	}

	private AccountDTOImpl buildAccountDTO(int postFix) {
		AccountDTOImpl accountDTO = new AccountDTOImpl();

		accountDTO.setAccountName("Salary" + postFix);
		accountDTO.setAccountNumber("1234" + postFix);
		accountDTO.setAccountStatus(AccountDTO.AccountStatus.ACTIVE);
		accountDTO.setCurrency(Currency.getInstance("JOD"));
		CustomerDTOImpl customerDTO = new CustomerDTOImpl();
		customerDTO.setName("Mohd Awad" + postFix);
		accountDTO.setCustomerDTO(customerDTO);
		return accountDTO;
	}

	private IBANDTO getIbandto(int postFix) {
		IBANDTOImpl ibandto = new IBANDTOImpl();
		ibandto.setCountryCode("JOD" + postFix);
		ibandto.setIbanValue("IBAN Value" + postFix);
		return ibanPersistenceService.save(ibandto);
	}

}
