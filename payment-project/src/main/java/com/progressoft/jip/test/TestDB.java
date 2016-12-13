package com.progressoft.jip.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.iban.service.IBANPersistenceService;
import com.progressoft.jip.payment.service.PaymentPersistenceService;

public class TestDB {

	public static QueryRunner runner;
	public static AccountPersistenceService accountPersistenceService;
	public static IBANPersistenceService ibanPersistenceService;

	public static PaymentPersistenceService paymentPersistenceService;

	public static void main(String[] args) {

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

		dataSource.setUrl("jdbc:mysql://localhost:3306/payment_db");

		dataSource.setUsername("root");

		dataSource.setPassword("root");

		try (Connection connection = dataSource.getConnection()) {
			runner = new QueryRunner(dataSource);

			testAccount();
			testIBan();
			testIdTable();
			testPayment();
			testPayment();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void testPayment() {
		// TODO Auto-generated method stub

	}

	private static void testIdTable() {
		// TODO Auto-generated method stub

	}

	private static void testIBan() {
		// TODO Auto-generated method stub

	}

	private static void testAccount() {
		// TODO Auto-generated method stub

	}

}
