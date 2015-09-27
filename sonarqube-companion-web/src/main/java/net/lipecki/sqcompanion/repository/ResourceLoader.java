package net.lipecki.sqcompanion.repository;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by gregorry on 26.09.2015.
 */
public class ResourceLoader {

    public static final String RESOURCE_CLASSPATH = "classpath:";

    private static final String RESOURCE_FILESYSTEM = "filesystem:";

    public String getAsString(final String resource) {
        try {
            if (resource.startsWith(RESOURCE_CLASSPATH)) {
                return getFromClasspath(resource);
            } else if (resource.startsWith(RESOURCE_FILESYSTEM)) {
                return getFromFilesystem(resource);
            } else {
                throw new ResourceLoadingException(String.format("Unknown resource type [resource=%s]",
                        resource));
            }
        } catch (IOException e) {
            throw new ResourceLoadingException(String.format("Exception while loading resource [resource=%s]",
                    resource), e);
        }
    }

    private String getFromFilesystem(final String resource) throws IOException {
        return Files.toString(new File(resource.substring(RESOURCE_FILESYSTEM.length())), Charsets.UTF_8);
    }

    private String getFromClasspath(final String resource) throws IOException {
        final URL url = Resources.getResource(resource.substring(RESOURCE_CLASSPATH.length()));
        return Resources.toString(url, Charsets.UTF_8);
    }

}
