package com.progressoft.jip.payment.purpose.dao.impl;

import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTOImpl;
import com.progressoft.jip.payment.purpose.service.PaymentPurposePersistenceService;
import com.progressoft.jip.payment.purpose.service.PaymentPurposePersistenceServiceImpl;
import com.progressoft.jip.test.DataSourceConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

public class PurposeTest extends DataSourceConfig {

    private DataSource dataSource;
    private PaymentPurposePersistenceService paymentPurposePersistenceService;

    @Before
    public void setup() throws Exception {
        dataSource = configureDataSource();
        PaymentPurposeDAO paymentPurposeDAO = new JDBCPaymentPurposeDAO(dataSource);
        paymentPurposePersistenceService = new PaymentPurposePersistenceServiceImpl(paymentPurposeDAO);

    }

    @Test
    public void GivenPurposeObject_WhenInsertIntoDB_ThenNewRecordInserted() {
        PaymentPurposeDTOImpl paymentPurposeDTO = new PaymentPurposeDTOImpl();
        paymentPurposeDTO.setDescription("DES");
        paymentPurposeDTO.setShortCode("SALA");
        PaymentPurposeDTO save = paymentPurposePersistenceService.save(paymentPurposeDTO);
        Assert.assertEquals(paymentPurposeDTO.getShortCode(), save.getShortCode());

    }

    @Test
    public void GivenInsertedPurpose_WhenGetByShortCode_ThenPurposeReturned() {
        PaymentPurposeDTOImpl paymentPurposeDTO = new PaymentPurposeDTOImpl();
        paymentPurposeDTO.setDescription("DES");
        paymentPurposeDTO.setShortCode("SALA");
        PaymentPurposeDTO save = paymentPurposePersistenceService.save(paymentPurposeDTO);

        PaymentPurposeDTO paymentPurpose = paymentPurposePersistenceService.getPaymentPurpose(save.getShortCode());
        Assert.assertEquals(paymentPurposeDTO.getShortCode(), paymentPurpose.getShortCode());

    }

}
