package com.caffinc.urls.core.db.impl;

import com.caffinc.urls.core.db.ShorteningDB;
import com.caffinc.urls.core.entities.Shortening;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides an in-memory list implementation for the ShorteningDB
 * WARNING: HIGHLY VOLATILE, ONLY FOR TESTING
 *
 * @author Sriram
 * @since 4/26/2016
 */
public class SimpleDB implements ShorteningDB {
    List<Shortening> shortenings;

    public SimpleDB() {
        this.shortenings = new ArrayList<>();
    }

    @Override
    public void save(Shortening shortening) {
        for (Shortening s : shortenings) {
            if (s.getShortUrl().equals(shortening.getShortUrl())) {
                s.setTimestamp(shortening.getTimestamp());
                return;
            }
        }
        shortenings.add(new Shortening(shortening));
    }


    @Override
    public Shortening getShorteningByFullUrl(String fullUrl) {
        for (Shortening shortening : shortenings) {
            if (shortening.getFullUrl().equals(fullUrl)) {
                return new Shortening(shortening);
            }
        }
        return null;
    }

    @Override
    public Shortening getShorteningByShortUrl(String shortUrl) {
        for (Shortening shortening : shortenings) {
            if (shortening.getShortUrl().equals(shortUrl)) {
                return new Shortening(shortening);
            }
        }
        return null;
    }

    @Override
    public synchronized void remove(Shortening shortening) {
        if (shortening.getUserId() == null) {
            return;
        }
        Iterator<Shortening> shorteningIterator = shortenings.iterator();
        while (shorteningIterator.hasNext()) {
            Shortening s = shorteningIterator.next();
            if (s.equals(shortening)) {
                shorteningIterator.remove();
            }
        }
    }

    @Override
    public synchronized void removeOldEntries(long timestampMs, boolean removeOnlyAnonymous) {
        Iterator<Shortening> shorteningIterator = shortenings.iterator();
        while (shorteningIterator.hasNext()) {
            Shortening s = shorteningIterator.next();
            if (((removeOnlyAnonymous && s.getUserId() == null) || (!removeOnlyAnonymous)) && s.getTimestamp() < timestampMs) {
                shorteningIterator.remove();
            }
        }
    }
}
