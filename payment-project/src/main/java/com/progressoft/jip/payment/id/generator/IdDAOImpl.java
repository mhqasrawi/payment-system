package com.progressoft.jip.payment.id.generator;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

import com.progressoft.jip.payment.DAOException;

public class IdDAOImpl implements IdDAO {

	public static final int MINIMAL_DEFAULT_ID = 1;
	private DataSource dataSource;
	private QueryRunner queryRunner;

	public IdDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
		this.queryRunner = new QueryRunner(this.dataSource);

	}

	public long save(String tableName) {
		long maxId = getMaxId(tableName) + 1;

		if (maxId == 2) {
			return insertNewTableID(tableName, maxId);
		} else {
			update(tableName, maxId);
		}
		return maxId;

	}

	private long insertNewTableID(String tableName, long maxId) {
		String sql = "insert into id_table values(?,?)";
		try {
			int updated = this.queryRunner.update(sql, tableName, maxId);
			if (updated == 0)
				throw new DAOException("Error While Inserting the id for table: " + tableName);
			else
				return maxId;

		} catch (SQLException e) {
			throw new DAOException("Error While Inserting the id for table: " + tableName, e);
		}
	}

	private long getMaxId(String tableName) {
		String sql = "select id from id_table WHERE table_name='" + tableName + "';";

		try {

			long id;
			Map<String, Object> query = this.queryRunner.query(sql, new MapHandler());
			if (query != null) {
				Object object = query.get("id");
				if (object instanceof String) {
					id = ((long) Long.valueOf((String) object));
				} else {
					id = ((long) object);
				}
			} else {
				id = MINIMAL_DEFAULT_ID;
			}
			return id;

		} catch (SQLException e) {
			throw new DAOException("Error while reading max ID for table: " + tableName, e);
		}
	}

	public int update(String tableName, long maxId) {
		String sql = "update id_table set id=? where table_name = ?";
		try {
			return this.queryRunner.update(sql, maxId, tableName);
		} catch (SQLException e) {
			throw new DAOException("Error while updating id for table: " + tableName, e);
		}

	}

}
