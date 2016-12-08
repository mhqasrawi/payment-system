package com.progressoft.jip.test;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;

/**
 * Created by mhqasrawi on 02/12/16.
 */
public class IdDAOImplTest extends DataSourceConfig {
    DataSource dataSource;

    IdDAO idDAO;

    @Before
    public void setUp() throws Exception {
        dataSource = configureDataSource();
        idDAO = new IdDAOImpl(dataSource);
    }

    @Test
    public void GivenEmptyTable_WhenInsertRecord_ThenIDShouldReturn_2() {
        long id = idDAO.save("table1");
        assertEquals(2, id);
    }

    @Test
    public void GivenEmptyTable_WhenInsertTwoRecord_ThenIDShouldReturn_3() {
        long id = idDAO.save("table1");
        long id1 = idDAO.save("table1");
        assertEquals(3, id1);
    }

    @Test
    public void GivenEmptyTable_WhenInsertThreeRecord_ThenIDShouldReturn_4() throws Exception {
        long table1 = idDAO.save("table1");
        long table2 = idDAO.save("table1");
        long table3 = idDAO.save("table1");

        assertEquals(4, table3);

    }

    @Test
    public void test(){
        long dummy = idDAO.save("dummy");
        assertEquals(2,dummy);
    }

}
