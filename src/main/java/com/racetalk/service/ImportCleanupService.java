package com.racetalk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;


public class ImportCleanupService {
    private static final Logger logger = LoggerFactory.getLogger(ImportCleanupService.class);

    private final RaceImportService raceImportService;
    private final RaceService raceService;
    private final int year;

    public ImportCleanupService(RaceImportService raceImportService, RaceService raceService, int year) {
        this.raceImportService = raceImportService;
        this.raceService = raceService;
        this.year = year;
    }

    public void initialize() {
        try {
            raceImportService.importSeasonRacesAndResults(year);
            raceService.deleteUpcomingRacesByDate(LocalDate.now());
        } catch (Exception e) {
            logger.error("Error during import and cleanup process for year {}", year, e);
        }
    }
}

