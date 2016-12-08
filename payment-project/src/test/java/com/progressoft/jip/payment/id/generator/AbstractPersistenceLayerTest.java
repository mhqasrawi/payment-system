package com.progressoft.jip.payment.id.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.Driver;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;

public class AbstractPersistenceLayerTest {

	private static final String INIT_SQL_FILE = "init.sql";
	private static final String PASSWORD = "";
	private static final String USERNAME = "sa";
	private static final String JDBC_H2_MEM_PAYMENT_DB = "jdbc:h2:mem:payment_db";
	protected static final String ORG_H2_DRIVER = "org.h2.Driver";

	protected DataSource dataSource;

	public DataSource getDataSource() throws ClassNotFoundException, Exception {
		return dataSource = configureDataSource();
	}

	private DataSource configureDataSource() throws Exception, ClassNotFoundException {
		Class.forName(ORG_H2_DRIVER);
		JdbcDataSource jdbcDataSource = new JdbcDataSource();
		BasicDataSource basicDataSource = configureBasicDataSource();
		List<String> readAllLines = getAllInitScriptLine();
		for (String line : readAllLines) {
			executeLine(basicDataSource, line);
		}
		return basicDataSource;
	}

	private BasicDataSource configureBasicDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(JDBC_H2_MEM_PAYMENT_DB);
		basicDataSource.setUsername(USERNAME);
		basicDataSource.setPassword(PASSWORD);
		return basicDataSource;
	}

	private void executeLine(BasicDataSource basicDataSource, String line) throws SQLException {
		Connection connection = basicDataSource.getConnection();
		getStatment(connection).execute(line);
		connection.close();
	}

	private List<String> getAllInitScriptLine() throws IOException {
		List<String> readAllLines = Files
				.readAllLines(new File(this.getClass().getClassLoader().getResource(INIT_SQL_FILE).getFile()).toPath());
		return readAllLines;
	}

	private Statement getStatment(Connection connection) throws SQLException {
		return connection.createStatement();
	}

}
