package com.progressoft.jip.test;

import static org.junit.Assert.assertEquals;

import java.util.Currency;
import java.util.Iterator;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.payment.DAOException;
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
import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;

/**
 * Created by mhqasrawi on 02/12/16.
 */
public class AccountDAOTest extends DataSourceConfig {

    private DataSource dataSource;
    private AccountPersistenceService accountPersistenceService;
    private IBANPersistenceService ibanPersistenceService;


    @Before
    public void setup() throws Exception {
        dataSource = configureDataSource();
        AccountDAO accountDAO = new JDBCAccountDAO(dataSource);
        IdDAO idDao = new IdDAOImpl(dataSource);
        IBANDAO ibandao = new JDBCIBANDAO(dataSource,idDao);
        ibanPersistenceService = new IBANPersistenceServiceImpl(ibandao);
        accountPersistenceService = new AccountPersistenceServiceImpl(accountDAO, ibanPersistenceService);
    }

    @Test
    public void GivenEmptyTable_InsertNewAccount_ThenAccountReturnedWithSave() {
        AccountDTOImpl accountDTO = buildAccountDTO(0);

        IBANDTO savedIBAN = getIbandto(0);
        assertEquals(2, savedIBAN.getId());

        AccountDTO savedAccount = saveAccountDTO(accountDTO, savedIBAN);
        assertEquals(2, savedAccount.getId());

    }

    @Test
    public void GivenInsertAccount_WhenGetAccountById_ThenShouldBeReturned() {
        AccountDTOImpl accountDTO = buildAccountDTO(0);
        IBANDTO ibandto = getIbandto(0);
        AccountDTO accountDTO1 = saveAccountDTO(accountDTO, ibandto);
        AccountDTO byId = accountPersistenceService.getById(accountDTO1.getId());
        assertEquals(accountDTO1.getId(), byId.getId());

    }

    @Test
    public void GivenInsertTwoAccounts_WhenGetSecondAccountID_ThenIDMustBe_3() {
        for (int i = 0; i < 2; i++) {
            AccountDTOImpl accountDTO = buildAccountDTO(i);
            IBANDTO ibandto = getIbandto(i);
            AccountDTO accountDTO1 = saveAccountDTO(accountDTO, ibandto);
        }


        AccountDTO byId = accountPersistenceService.getById(3);
        assertEquals(3, byId.getId());
    }

    @Test
    public void GivenWhenInserTreeAccounts_WhenRetriveAllAccounts_ThenSizeShouldBe_3(){
        for (int i=0;i<3;i++){
            AccountDTOImpl accountDTO = buildAccountDTO(i);
            IBANDTO ibandto = getIbandto(i);
            AccountDTO accountDTO1 = saveAccountDTO(accountDTO, ibandto);
        }

        Iterable<AccountDTO> all = accountPersistenceService.getAll();
        int size=0;
        Iterator<AccountDTO> iterator = all.iterator();
        while (iterator.hasNext()){
            size+=1;
            iterator.next();
        }
        assertEquals(3,size);
    }

    @Test
    public void GivenAccount_WhenUpdateAccountCurrency_ThenShouldReturnUpdatedCurrency(){
        AccountDTOImpl accountDTO = buildAccountDTO(0);
        IBANDTO ibandto = getIbandto(0);
        AccountDTO accountDTO1 = saveAccountDTO(accountDTO, ibandto);

        accountDTO.setCurrency(Currency.getInstance("USD"));
        AccountDTO save = accountPersistenceService.save(accountDTO);
        assertEquals(Currency.getInstance("USD").getCurrencyCode(),save.getCurreny().getCurrencyCode());

    }

    @Test
    public void GivenAccount_WhenUpdateAccountName_ThenShouldReturnUpdatedName(){
        AccountDTOImpl accountDTO = buildAccountDTO(0);
        IBANDTO ibandto = getIbandto(0);
        AccountDTO accountDTO1 = saveAccountDTO(accountDTO, ibandto);

        accountDTO.setAccountName("New Name");
        AccountDTO save = accountPersistenceService.save(accountDTO);
        assertEquals("New Name",save.getAccountName());

    }
    @Test(expected = DAOException.class)
    public void GivenEmptyTable_WhenGetAccountById_ThenDAOExcetpionThrown(){
        accountPersistenceService.getById(5);
    }
    @Test(expected = DAOException.class)
    public void GivenEmptyTable_WhenGetAccountByNumber_ThenDAOExceptionThrown(){
        accountPersistenceService.getAccount("32342");
    }
    @Test(expected = DAOException.class)
    public void GivenEmptyTable_WhenGetAllAccounts_ThenDAOExceptionThrown(){
        Iterable<AccountDTO> all = accountPersistenceService.getAll();
    }


    private AccountDTO saveAccountDTO(AccountDTOImpl accountDTO, IBANDTO savedIBAN) {
        accountDTO.setIbandto(savedIBAN);
        accountDTO.setIbanId(savedIBAN.getId());
        return accountPersistenceService.save(accountDTO);
    }

    private IBANDTO getIbandto(int postFix) {
        IBANDTOImpl ibandto = new IBANDTOImpl();
        ibandto.setCountryCode("JOD" + postFix);
        ibandto.setIbanValue("IBAN Value" + postFix);
        return ibanPersistenceService.save(ibandto);
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

}
