package com.caffinc.urls.core.entities;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sriram
 * @since 4/27/2016
 */
public class ShorteningTest {
    @Test
    public void testEquals() {
        Shortening s = new Shortening("test", "test");
        Assert.assertEquals(s, new Shortening(s));
    }
}
