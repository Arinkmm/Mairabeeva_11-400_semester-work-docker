package com.racetalk.service.impl;

import com.racetalk.dao.RaceDao;
import com.racetalk.dao.RaceResultDao;
import com.racetalk.dao.DriverDao;
import com.racetalk.dto.RaceDto;
import com.racetalk.dto.RaceResultDto;
import com.racetalk.entity.Driver;
import com.racetalk.entity.Race;
import com.racetalk.entity.RaceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.racetalk.service.RaceImportService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RaceImportServiceImpl implements RaceImportService {
    private final ObjectMapper mapper = new ObjectMapper();

    private final RaceDao raceDao;
    private final RaceResultDao raceResultDao;
    private final DriverDao driverDao;

    public RaceImportServiceImpl(RaceDao raceDao, RaceResultDao raceResultDao, DriverDao driverDao) {
        this.raceDao = raceDao;
        this.raceResultDao = raceResultDao;
        this.driverDao = driverDao;
    }

    @Override
    public void importSeasonRacesAndResults(int year) {
        String racesUrl = "https://api.openf1.org/v1/sessions?year=" + year + "&session_name=Race";

        try {
            String racesJson = sendGetRequest(racesUrl);
            RaceDto[] racesArray = mapper.readValue(racesJson, RaceDto[].class);
            List<RaceDto> races = Arrays.asList(racesArray);

            for (RaceDto raceDto : races) {
                Optional<Race> existingRace = raceDao.findBySessionKey(raceDto.getSession_key());

                Race race = existingRace.orElseGet(() -> {
                    Race newRace = new Race();
                    newRace.setSessionKey(raceDto.getSession_key());
                    return newRace;
                });

                race.setLocation(raceDto.getCircuit_short_name());
                race.setRaceDate(LocalDate.parse(raceDto.getDate_start().split("T")[0]));
                race.setFinished(true);
                saveOrUpdateRace(race);

                String resultsUrl = "https://api.openf1.org/v1/session_result?session_key=" + raceDto.getSession_key();
                String resultsJson = sendGetRequest(resultsUrl);
                Thread.sleep(1000);

                RaceResultDto[] resultsArray = mapper.readValue(resultsJson, RaceResultDto[].class);
                List<RaceResultDto> results = Arrays.asList(resultsArray);

                for (RaceResultDto resDto : results) {
                    Driver driver = driverDao.findByDriverNumber(resDto.getDriver_number()).orElse(null);

                    RaceResult result = new RaceResult();
                    result.setRace(race);
                    result.setDriver(driver);
                    result.setPosition(resDto.getPosition());
                    result.setPoints(resDto.getPoints());

                    saveOrUpdateRaceResult(result);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String sendGetRequest(String url) {
        try {
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void saveOrUpdateRace(Race race) {
        if (raceDao.existsPastRaceById(race.getId())) {
            raceDao.updatePastRace(race);
        } else {
            raceDao.createPastRace(race);
        }
    }

    private void saveOrUpdateRaceResult(RaceResult result) {
        if (raceResultDao.existsById(result.getId())) {
            raceResultDao.update(result);
        } else {
            raceResultDao.create(result);
        }
    }
}
