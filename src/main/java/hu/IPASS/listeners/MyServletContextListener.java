package hu.IPASS.listeners;

import hu.IPASS.persistence.GebruikerData;
import hu.IPASS.persistence.OefeningTypeData;
import hu.IPASS.persistence.PersistenceManager;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.HttpResources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.Duration;

@WebListener
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            PersistenceManager.loadUserFromAzure();
            PersistenceManager.loadOefeningTypeFromAzure();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
//            PersistenceManager.sendUsersToAzure();
//            PersistenceManager.sendOefeningTypeToAzure();

            Schedulers.shutdownNow();
            HttpResources.disposeLoopsAndConnectionsLater(Duration.ZERO, Duration.ZERO).block();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}
