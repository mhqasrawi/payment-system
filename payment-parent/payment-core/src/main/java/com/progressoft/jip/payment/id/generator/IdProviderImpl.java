package com.progressoft.jip.payment.id.generator;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

import com.progressoft.jip.payment.DAOException;

public class IdProviderImpl implements IdProvider {

	private static final String INSERT_INTO_ID_TABLE_VALUES = "insert into id_table values(?,?)";
	private static final String ERROR_WHILE_INSERTING_THE_ID_FOR_TABLE = "Error While Inserting the id for table: ";
	private static final String SELECT_ID_FROM_ID_TABLE_WHERE_TABLE_NAME_S = "select id from id_table WHERE table_name='%s'";
	private static final String ERROR_WHILE_UPDATING_ID_FOR_TABLE = "Error while updating id for table: ";
	private static final String ERROR_WHILE_READING_MAX_ID_FOR_TABLE = "Error while reading max ID for table: ";
	private static final String UPDATE_ID_TABLE_SET_ID_WHERE_TABLE_NAME = "update id_table set id=? where table_name = ?";
	private static final int MINIMAL_ID = 2;

	private DataSource dataSource;
	private QueryRunner queryRunner;

	public IdProviderImpl(DataSource dataSource) {
		this.dataSource = dataSource;
		this.queryRunner = new QueryRunner(this.dataSource);

	}

	public long getId(String tableName) {
		long maxId = getMaxId(tableName) + 1;
		if (maxId == 2) {
			return insertNewTableID(tableName, maxId);
		} else {
			updateTableId(tableName, maxId);
		}
		return maxId;

	}

	private long insertNewTableID(String tableName, long maxId) {
		try {
			int updated = update(tableName, INSERT_INTO_ID_TABLE_VALUES, maxId);
			if (updated == 0)
				throw new DAOException(ERROR_WHILE_INSERTING_THE_ID_FOR_TABLE + tableName);
			else
				return maxId;

		} catch (SQLException e) {
			throw new DAOException(ERROR_WHILE_INSERTING_THE_ID_FOR_TABLE + tableName, e);
		}
	}

	private int update(String tableName, String sqlStament, long maxId) throws SQLException {
		return this.queryRunner.update(sqlStament, tableName, maxId);
	}

	private long getMaxId(String tableName) {
		try {
			Map<String, Object> query = this.queryRunner
					.query(String.format(SELECT_ID_FROM_ID_TABLE_WHERE_TABLE_NAME_S, tableName), new MapHandler());
			long id = getId(query);
			return id;

		} catch (SQLException e) {
			throw new DAOException(ERROR_WHILE_READING_MAX_ID_FOR_TABLE + tableName, e);
		}
	}

	private long getId(Map<String, Object> query) {
		long id;
		if (query == null) {
			return MINIMAL_ID;
		}
		Integer integerId = (Integer) query.get("id");
		id = integerId.longValue();
		return id;
	}

	private int updateTableId(String tableName, long maxId) {
		try {
			return this.queryRunner.update(UPDATE_ID_TABLE_SET_ID_WHERE_TABLE_NAME, maxId, tableName);
		} catch (SQLException e) {
			throw new DAOException(ERROR_WHILE_UPDATING_ID_FOR_TABLE + tableName, e);
		}

	}

}
