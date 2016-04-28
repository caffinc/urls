package com.caffinc.urls.core.db;

import com.caffinc.urls.core.entities.Shortening;

/**
 * Interface for the database
 *
 * @author Sriram
 * @since 4/26/2016
 */
public interface ShorteningDB {
    /**
     * Saves a given URL shortening
     *
     * @param shortening Shortening to save
     */
    void save(Shortening shortening);

    /**
     * Returns the matching shortening for a given full URL. Used when a shortening is to be reused, usually only for
     * unregistered users.
     *
     * @param fullUrl Full URL to get the shortened URL for
     * @return Shortening if found, null otherwise
     */
    Shortening getShorteningByFullUrl(String fullUrl);

    /**
     * Returns the matching shortening from a given shortened URL. Used for resolving shortened URLs into their actual counterparts.
     *
     * @param shortUrl Shortened URL from which to get the full URL
     * @return Shortening if found, null otherwise
     */
    Shortening getShorteningByShortUrl(String shortUrl);

    /**
     * Removes a shortening for a registered user. Unregistered users cannot remove shortened URLs, URLs expire if not
     * used for greater than the expiry time.
     *
     * @param shortening Shortening to remove
     */
    void remove(Shortening shortening);

    /**
     * Removes shortenings older than (less than) the timestamp specified
     *
     * @param timestampMs         Timestamp im milliseconds
     * @param removeOnlyAnonymous Removes only anonymous entries (UserID null)
     */
    void removeOldEntries(long timestampMs, boolean removeOnlyAnonymous);
}
