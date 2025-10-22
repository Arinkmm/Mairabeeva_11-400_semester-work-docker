package com.racetalk.service;

import com.racetalk.util.DatabaseConnectionUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final RaceImportService raceImportService;
    private final DatabaseConnectionUtil databaseConnection;
    private final int year;

    public DataScheduler(RaceImportService raceImportService, DatabaseConnectionUtil databaseConnection, int year) {
        this.raceImportService = raceImportService;
        this.databaseConnection = databaseConnection;
        this.year = year;
    }

    public void start() {
        long initialDelay = computeInitialDelayToMidnight();
        long period = TimeUnit.DAYS.toMillis(1);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                raceImportService.importSeasonRacesAndResults(year);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                deleteRecordsForToday();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    private long computeInitialDelayToMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMidnight = now.with(LocalTime.MIDNIGHT).plusDays(1);
        return Duration.between(now, nextMidnight).toMillis();
    }

    private void deleteRecordsForToday() {
        String sql = "DELETE FROM upcoming_races WHERE race_date = ?";
        LocalDate today = LocalDate.now();

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(today));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        scheduler.shutdownNow();
    }
}
