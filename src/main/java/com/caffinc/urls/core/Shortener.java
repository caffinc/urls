package com.caffinc.urls.core;

import com.caffinc.urls.core.db.ShorteningDB;
import com.caffinc.urls.core.db.impl.SimpleDB;
import com.caffinc.urls.core.entities.Shortening;
import com.caffinc.urls.core.exceptions.ShorteningException;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Performs the main shortening work
 *
 * @author Sriram
 * @since 4/26/2016
 */
public class Shortener {
    private static final String URL_SHORTENER_HOST = System.getProperty("urls.host");
    private static final int DEFAULT_LENGTH = 8;
    private static final Shortener INSTANCE = new Shortener();

    private ShorteningDB shorteningDB = new SimpleDB();

    /**
     * Private constructor
     */
    private Shortener() {
    }

    public static Shortener getInstance() {
        return INSTANCE;
    }

    public void setShorteningDB(ShorteningDB shorteningDB) {
        this.shorteningDB = shorteningDB;
    }

    /**
     * Shortens a full URL
     *
     * @param userId  Registered User ID, null if anonymous
     * @param fullUrl Full URL to shorten
     * @return Shortening object stored in the database
     */
    public Shortening shorten(String userId, String fullUrl) {
        Shortening shortening = new Shortening(
                userId,
                URL_SHORTENER_HOST + hash(userId, fullUrl, System.currentTimeMillis() / 60000).substring(0, DEFAULT_LENGTH),
                fullUrl
        );
        this.shorteningDB.save(shortening);
        return shortening;
    }

    /**
     * Shortens a full URL into a provided short URL for registered users
     *
     * @param userId   Registered User ID
     * @param fullUrl  Full URL to shorten
     * @param shortUrl Short URL to use as the shortening for the Full URL
     * @return Shortening object stored in the database
     */
    public Shortening shorten(String userId, String fullUrl, String shortUrl) {
        if (userId != null) {
            if (this.shorteningDB.getShorteningByShortUrl(shortUrl) != null) {
                throw new ShorteningException("Short URL already exists, delete the URL if it belongs to you");
            }
            Shortening shortening = new Shortening(userId, shortUrl, fullUrl);
            this.shorteningDB.save(shortening);
            return shortening;
        } else {
            throw new ShorteningException("Unregistered users cannot provide a short URL");
        }
    }

    /**
     * Resolves the short URL into a Shortening object
     *
     * @param shortUrl URL to resolve
     * @return Shortening for the shortUrl
     */
    public Shortening resolve(String shortUrl) {
        return this.shorteningDB.getShorteningByShortUrl(URL_SHORTENER_HOST + shortUrl);
    }

    /**
     * Converts a list of Objects into a String and hashes it
     *
     * @param objects Objects to hash
     * @return Hashed string
     */
    private String hash(Object... objects) {
        StringBuilder sb = new StringBuilder();
        for (Object o : objects) {
            sb.append(o);
        }
        return sha256(sb.toString());
    }

    /**
     * Hashes a String using SHA-256
     *
     * @param string string to hash
     * @return Hashed String
     */
    private String sha256(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(string.getBytes("UTF-8"));
            return Hex.encodeHexString(md.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new IllegalStateException("Could not hash string", e);
        }
    }
}
