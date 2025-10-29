package com.racetalk.web.listener;

import com.racetalk.dao.*;
import com.racetalk.dao.impl.*;
import com.racetalk.service.*;
import com.racetalk.service.impl.*;
import com.racetalk.util.DatabaseConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {
    private final Logger logger = LoggerFactory.getLogger(InitListener.class);

    private ImportCleanupService importCleanupService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseConnectionUtil databaseConnection = DatabaseConnectionUtil.getInstance();

        UserDao userDao = new UserDaoImpl(databaseConnection);
        NoteDao noteDao = new NoteDaoImpl(databaseConnection);
        TeamDao teamDao = new TeamDaoImpl(databaseConnection);
        DriverDao driverDao = new DriverDaoImpl(databaseConnection, teamDao);
        RaceDao raceDao = new RaceDaoImpl(databaseConnection);
        ChatMessageDao chatMessageDao = new ChatMessageDaoImpl(databaseConnection, userDao);
        RaceResultDao raceResultDao = new RaceResultDaoImpl(databaseConnection, raceDao, driverDao, teamDao);

        ServletContext context = sce.getServletContext();

        context.setAttribute("userDao", userDao);
        context.setAttribute("noteDao", noteDao);
        context.setAttribute("teamDao", teamDao);
        context.setAttribute("driverDao", driverDao);
        context.setAttribute("raceDao", raceDao);
        context.setAttribute("chatMessageDao", chatMessageDao);
        context.setAttribute("raceResultDao", raceResultDao);

        UserService userService = new UserServiceImpl(userDao);
        NoteService noteService = new NoteServiceImpl(noteDao);
        TeamService teamService = new TeamServiceImpl(teamDao);
        DriverService driverService = new DriverServiceImpl(driverDao);
        RaceService raceService = new RaceServiceImpl(raceDao);
        ChatMessageService chatMessageService = new ChatMessageServiceImpl(chatMessageDao);
        RaceResultService raceResultService = new RaceResultServiceImpl(raceResultDao);

        context.setAttribute("userService", userService);
        context.setAttribute("noteService", noteService);
        context.setAttribute("teamService", teamService);
        context.setAttribute("driverService", driverService);
        context.setAttribute("raceService", raceService);
        context.setAttribute("chatMessageService", chatMessageService);
        context.setAttribute("raceResultService", raceResultService);

        RaceImportService raceImportService = new RaceImportServiceImpl(raceDao, raceResultDao, driverDao);
        context.setAttribute("raceImportService", raceImportService);

        importCleanupService = new ImportCleanupService(raceImportService, raceService, 2025);
        try {
            //importCleanupService.initialize();
        } catch (Exception e) {
            logger.error("Failed during import and cleanup initialization", e);
        }
    }
}