package com.progressoft.jip.payment.id.generator;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.progressoft.jip.payment.DAOException;

public class IdDAOImpl implements IdDAO {

	private DataSource dataSource;
	private QueryRunner queryRunner;

	public IdDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
		this.queryRunner = new QueryRunner(this.dataSource);

	}

	public long insert(String tableName) {
		int newId = this.update(tableName);
		if (newId > 0)
			return newId;
		String sql = "insert into id_table values(?,?,?,?,?,?)";

		long maxId = this.getMaxId(tableName) + 1;
		try {
			int updated = this.queryRunner.update(sql, tableName, maxId);
			if (updated == 0)
				throw new DAOException("Error While Inserting the id for table: " + tableName);
			else
				return maxId;

		} catch (SQLException e) {
			throw new DAOException("Error While Inserting the id for table: " + tableName);
		}
	}

	public long getMaxId(String tableName) {
		String sql = "select MAX(_id) from id_table WHERE table_name='" + tableName + "'";
		try {
			return this.queryRunner.query(sql, new BeanHandler<Long>(Long.class));

		} catch (SQLException e) {
			throw new DAOException("Error while reading max ID for table: " + tableName);
		}
	}

	public int update(String tableName) {
		String sql = "update id_table set _id=?";
		long maxId = getMaxId(tableName) + 1;
		try {
			return this.queryRunner.update(sql, maxId);
		} catch (SQLException e) {
			throw new DAOException("Error while updating id for table: " + tableName);
		}

	}

}
