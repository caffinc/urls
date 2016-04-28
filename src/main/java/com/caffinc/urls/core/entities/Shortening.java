package com.caffinc.urls.core.entities;

import lombok.EqualsAndHashCode;

/**
 * Holds a URL entry for CRUD operations
 *
 * @author Sriram
 * @since 4/26/2016
 */
@EqualsAndHashCode
public class Shortening {
    private static final String UNREGISTERED_USER = null;
    private String userId;
    private String shortUrl;
    private String fullUrl;
    private long timestamp;

    /**
     * Creates a shortening from another
     *
     * @param shortening Shortening to clone
     */
    public Shortening(Shortening shortening) {
        this(shortening.getUserId(), shortening.getShortUrl(), shortening.getFullUrl(), shortening.getTimestamp());
    }

    /**
     * Creates a Shortening for an unregistered user with the timestamp set as System.currentTimeMillis()
     *
     * @param shortUrl Shortened URL
     * @param fullUrl  Full URL
     */
    public Shortening(String shortUrl, String fullUrl) {
        this(shortUrl, fullUrl, System.currentTimeMillis());
    }

    /**
     * Creates a Shortening for an unregistered user
     *
     * @param shortUrl  Shortened URL
     * @param fullUrl   Full URL
     * @param timestamp Timestamp for this shortening, will be used for creation, updation and removal of the
     *                  shortening
     */
    public Shortening(String shortUrl, String fullUrl, long timestamp) {
        this(UNREGISTERED_USER, shortUrl, fullUrl, timestamp);
    }

    /**
     * Creates a Shortening with the timestamp set as System.currentTimeMillis()
     *
     * @param userId   User ID of the registered user, null if not registered
     * @param shortUrl Shortened URL
     * @param fullUrl  Full URL
     */
    public Shortening(String userId, String shortUrl, String fullUrl) {
        this(userId, shortUrl, fullUrl, System.currentTimeMillis());
    }

    /**
     * Creates a new Shortening with all values set
     *
     * @param userId    User ID of the registered user, null if not registered
     * @param shortUrl  Shortened URL
     * @param fullUrl   Full URL
     * @param timestamp Timestamp for this shortening, will be used for creation, updation and removal of the
     *                  shortening
     */
    public Shortening(String userId, String shortUrl, String fullUrl, long timestamp) {
        this.userId = userId;
        this.shortUrl = shortUrl;
        this.fullUrl = fullUrl;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
