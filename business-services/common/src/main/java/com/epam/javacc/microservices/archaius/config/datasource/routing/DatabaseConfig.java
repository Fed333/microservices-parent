package com.epam.javacc.microservices.archaius.config.datasource.routing;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Getter
@Setter
public class DatabaseConfig {

    private String url;

    private String username;

    private String password;

    private String driver;

    public DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

}
