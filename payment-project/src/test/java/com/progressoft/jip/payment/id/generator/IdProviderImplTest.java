package com.progressoft.jip.payment.id.generator;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IdProviderImplTest extends AbstractPersistenceLayerTest {

	private static final String TEST_TABLE = "test_table";
	private IdProvider IdProvider;
	private DataSource dataSource;

	@Before
	public void setup() throws Exception, ClassNotFoundException {
		dataSource = getDataSource();
		IdProvider = new IdProviderImpl(dataSource);
	}

	@Test
	public void test() throws SQLException {
		try {
			Class.forName(ORG_H2_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		long id = IdProvider.getId(TEST_TABLE);
		Assert.assertEquals(3, id);
	}

}
