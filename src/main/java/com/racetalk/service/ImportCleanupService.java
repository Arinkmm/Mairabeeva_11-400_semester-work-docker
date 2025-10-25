package com.racetalk.service;

import java.time.LocalDate;


public class ImportCleanupService {
    private final RaceImportService raceImportService;
    private final RaceService raceService;
    private final int year;

    public ImportCleanupService(RaceImportService raceImportService, RaceService raceService, int year) {
        this.raceImportService = raceImportService;
        this.raceService = raceService;
        this.year = year;
    }

    public void initialize() {
        raceImportService.importSeasonRacesAndResults(year);
        raceService.deleteUpcomingRacesByDate(LocalDate.now());
    }
}

