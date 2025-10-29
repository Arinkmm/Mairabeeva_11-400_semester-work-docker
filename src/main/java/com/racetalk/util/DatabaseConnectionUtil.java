package com.racetalk.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionUtil{
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionUtil.class);

    private static DatabaseConnectionUtil instance;
    private HikariDataSource ds;

    private DatabaseConnectionUtil() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(System.getenv("DATABASE_CONNECTION_URL"));
        config.setUsername(System.getenv("DATABASE_CONNECTION_USERNAME"));
        config.setPassword(System.getenv("DATABASE_CONNECTION_PASSWORD"));
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
            logger.error("Failed to get database connection", e);
            throw new RuntimeException("Database connection failure", e);
        }
    }
}

