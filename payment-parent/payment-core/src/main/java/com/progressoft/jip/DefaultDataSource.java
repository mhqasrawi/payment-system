package com.progressoft.jip;

import com.progressoft.jip.configuration.Configuration;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author u612
 */
public class DefaultDataSource extends BasicDataSource {

    private static final String DB_DRIVER_CLASSNAME = "db.driver-classname";
    private static final String DB_URL = "db.url";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_USERNAME = "db.username";
    @Autowired
    private Configuration configuration;

    public void config() {
        setUsername(configuration.getProperty(DB_USERNAME));
        setPassword(configuration.getProperty(DB_PASSWORD));
        setUrl(configuration.getProperty(DB_URL));
        setDriverClassName(configuration.getProperty(DB_DRIVER_CLASSNAME));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((configuration == null) ? 0 : configuration.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DefaultDataSource other = (DefaultDataSource) obj;
        if (configuration == null) {
            if (other.configuration != null)
                return false;
        } else if (!configuration.equals(other.configuration))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DefaultDataSource [configuration=" + configuration + "]" + super.toString();
    }


}
