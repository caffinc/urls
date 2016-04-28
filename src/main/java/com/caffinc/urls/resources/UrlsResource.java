package com.caffinc.urls.resources;

import com.caffinc.urls.core.Shortener;
import com.caffinc.urls.core.entities.Shortening;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * API Resource for the URL shortener
 *
 * @author Sriram
 * @since 4/26/2016
 */
@Path("/")
@Api("Core APIs")
public class UrlsResource {
    private static final Shortener SHORTENER = Shortener.getInstance();

    /**
     * Endpoint used to shorten the URL
     *
     * @param fullUrl  URL to shorten
     * @param shortUrl Optional shortened URL to use for registered users
     * @return Returns the shortened URL
     */
    @GET
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    public Response getShortening(@QueryParam("u") String fullUrl, @QueryParam("s") String shortUrl) {
        // TODO User Management
        String userId = null;
        if (shortUrl != null) {
            return Response.ok(SHORTENER.shorten(userId, fullUrl, shortUrl)).build();
        } else {
            return Response.ok(SHORTENER.shorten(userId, fullUrl)).build();
        }
    }

    /**
     * Endpoint used to redirect the short URL to the full URL
     *
     * @param shortUrl Shortened URL
     * @return Redirect response if successful, 404 if not found
     * @throws URISyntaxException
     */
    @GET
    @Path("{shortUrl}")
    public Response redirect(@PathParam("shortUrl") String shortUrl) throws URISyntaxException {
        Shortening shortening = SHORTENER.resolve(shortUrl);
        if (shortening == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.temporaryRedirect(new URI(shortening.getFullUrl())).build();
    }
}
