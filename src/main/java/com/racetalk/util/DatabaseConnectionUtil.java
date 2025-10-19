package com.racetalk.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionUtil{
    private static DatabaseConnectionUtil instance;
    private HikariDataSource ds;

    private DatabaseConnectionUtil() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:postgresql://localhost:5432/racetalk_db");
        config.setUsername("postgres");
        config.setPassword("08122006Ar");
        config.setDriverClassName("org.postgresql.Driver");

        config.setMaximumPoolSize(25);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(20000);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(1800000);
        config.setAutoCommit(true);

        ds = new HikariDataSource(config);
    }

    public static synchronized DatabaseConnectionUtil getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionUtil();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

