package com.progressoft.jip.payment.id.generator;

import com.progressoft.jip.payment.DAOException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

public class IdDAOImpl implements IdDAO {

    public static final int MINIMAL_DEFAULT_ID = 1;
    private DataSource dataSource;
    private QueryRunner queryRunner;

    public IdDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.queryRunner = new QueryRunner(this.dataSource);

    }

    public int save(String tableName) {
        int maxId = getMaxId(tableName) + 1;

        if (maxId == 2) {
            return insertNewTableID(tableName, maxId);
        } else {
            update(tableName, maxId);
        }
        return maxId;

    }

    private int insertNewTableID(String tableName, int maxId) {
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

    private int getMaxId(String tableName) {
        String sql = "select id from id_table WHERE table_name='" + tableName + "';";

        try {

            int id;
            Map<String, Object> query = this.queryRunner.query(sql, new MapHandler());
            if (query != null)
                id = ((int) query.get("id"));
            else {
                id = MINIMAL_DEFAULT_ID;
            }
            return id;

        } catch (SQLException e) {
            throw new DAOException("Error while reading max ID for table: " + tableName, e);
        }
    }

    public int update(String tableName, int maxId) {
        String sql = "update id_table set id=? where table_name = ?";
        try {
            return this.queryRunner.update(sql, maxId, tableName);
        } catch (SQLException e) {
            throw new DAOException("Error while updating id for table: " + tableName, e);
        }

    }

}
