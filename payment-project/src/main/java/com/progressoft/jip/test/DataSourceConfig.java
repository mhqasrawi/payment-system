package com.progressoft.jip.test;

import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * Created by mhqasrawi on 02/12/16.
 */
public class DataSourceConfig {

    IdDAO idDAO;
    DataSource dataSource;


    @Before
    public void setUp() throws Exception {
        dataSource = configureDataSource();
        //   jdbcibandao=new JDBCIBANDAO(dataSource);
        // ibanPersistenceService=new IBANPersistenceServiceImpl(jdbcibandao);
        idDAO = new IdDAOImpl(dataSource);


    }

    protected DataSource configureDataSource() throws Exception {
        Class.forName("org.h2.Driver");
        JdbcDataSource ds = new JdbcDataSource();
        String url = "jdbc:h2:mem:test";
        ds.setUser("sa");
        ds.setPassword("sa");
        ds.setURL(url);


        List<String> strings = Files.readAllLines(Paths.get("./init.sql"));
        String line = "";
        for (String l : strings)
            line += l;

        Connection connection = ds.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(line);

        return ds;
    }
}
