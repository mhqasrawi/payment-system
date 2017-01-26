package com.progressoft.jip.test;

import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * Created by mhqasrawi on 02/12/16.
 */
public class DataSourceConfig {

    private static final String INIT_SQL_FILE = "init.sql";
    IdDAO idDAO;
    DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        dataSource = configureDataSource();
        idDAO = new IdDAOImpl(dataSource);
    }

    protected DataSource configureDataSource() throws Exception {
        Class.forName("org.h2.Driver");
        JdbcDataSource ds = new JdbcDataSource();
        String url = "jdbc:h2:mem:test";
        ds.setUser("sa");
        ds.setPassword("sa");
        ds.setURL(url);

        List<String> strings = Files
                .readAllLines(new File(this.getClass().getClassLoader().getResource(INIT_SQL_FILE).getFile()).toPath());
        String line = "";
        for (String l : strings)
            line += l;

        Connection connection = ds.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(line);

        return ds;
    }
}
