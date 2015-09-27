package net.lipecki.sqcompanion.repository;

import java.io.IOException;

/**
 * Created by gregorry on 26.09.2015.
 */
public class ResourceLoadingException extends RuntimeException {

    public ResourceLoadingException(final String message, final IOException cause) {
        super(message, cause);
    }

    public ResourceLoadingException(final String message) {
        super(message);
    }

}
