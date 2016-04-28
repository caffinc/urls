package com.caffinc.urls.app;

import com.caffinc.jetter.Api;
import com.caffinc.urls.core.Shortener;
import com.caffinc.urls.core.db.ShorteningDB;
import com.caffinc.urls.core.db.impl.SimpleDB;
import com.caffinc.urls.resources.UrlsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class from which the URL shortener is started from
 *
 * @author Sriram
 * @since 4/26/2016
 */
public class UrlsApp {
    private static final Logger LOG = LoggerFactory.getLogger(UrlsApp.class);
    private static final String DEFAULT_BASEPATH = "";
    private static final int DEFAULT_PORT = 9099;
    private static final String DEFAULT_HOST = "http://localhost:$PORT/";
    private static final long DEFAULT_TIMEOUT = 30000L;

    private UrlsApp() {
    }

    /**
     * Starts the URL Shortener
     *
     * @param port     Port to start the shortener on
     * @param basePath Base Path on which to start the shortener on
     * @param db       ShorteningDB to use as the backing DB for the shortener
     * @throws Exception
     */
    public static void run(ShorteningDB db) throws Exception {
        setDefaults();
        startRefreshThread(db);
        Shortener.getInstance().setShorteningDB(db);
        new Api(Integer.parseInt(System.getProperty("urls.port")))
                .setBaseUrl(System.getProperty("urls.basepath"))
                .addStaticResource(
                        UrlsApp.class.getClassLoader().getResource("swaggerui").toURI().toString(),
                        System.getProperty("urls.basepath") + "/docs")
                .addServiceResource(UrlsResource.class, "Urls", "URL Shortener Service")
                .enableCors()
                .start();
    }

    /**
     * Sets default properties
     */
    private static void setDefaults() {
        if (System.getProperty("urls.port") == null) {
            System.setProperty("urls.port", String.valueOf(DEFAULT_PORT));
        }
        if (System.getProperty("urls.host") == null) {
            System.setProperty("urls.host", DEFAULT_HOST.replace("$PORT", System.getProperty("urls.port")));
        }
        if (System.getProperty("urls.basepath") == null) {
            System.setProperty("urls.basepath", DEFAULT_BASEPATH);
        }
        if (System.getProperty("urls.refreshms") == null) {
            System.setProperty("urls.refreshms", String.valueOf(DEFAULT_TIMEOUT));
        }
    }

    /**
     * Starts a thread to remove old entries in the DB
     *
     * @param db DB to remove old entries
     */
    private static void startRefreshThread(final ShorteningDB db) {
        new Thread("RefreshThread") {
            private final long REFRESH_TIMEOUT = Long.parseLong(System.getProperty("urls.refreshms"));

            @Override
            public void run() {
                long ts = System.currentTimeMillis();
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        LOG.warn("Exception while waiting for refreshing DB", e);
                    }
                    if (ts < System.currentTimeMillis() - REFRESH_TIMEOUT) {
                        LOG.info("Refreshing DB");
                        db.removeOldEntries(ts, true);
                        ts = System.currentTimeMillis();
                    }
                }
            }
        }.start();
    }

    /**
     * Main method for starting a standalone shortener instance
     *
     * @param args Command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        final ShorteningDB db = new SimpleDB();
        run(db);
    }
}
