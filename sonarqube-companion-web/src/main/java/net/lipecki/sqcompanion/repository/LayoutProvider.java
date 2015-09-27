package net.lipecki.sqcompanion.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Groups layout provider.
 * <p>
 * Reads project config and provides list of groups, projects and dependencies between both.
 * </p>
 * <p>
 * Configuration can be reloaded without restarting application.
 * </p>
 */
public class LayoutProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(LayoutProvider.class);
    private final Cache<String, LayoutConfiguration> layoutConfigurationCache;
    private final ResourceLoader resourceLoader;
    private final String configFilePath;

    public LayoutProvider(final ResourceLoader resourceLoader, final int cacheDuration, final TimeUnit cacheTimeUnit, final String configFilePath) {
        this.resourceLoader = resourceLoader;
        this.configFilePath = configFilePath;
        this.layoutConfigurationCache = CacheBuilder.newBuilder()
                .expireAfterWrite(cacheDuration, cacheTimeUnit)
                .build();
    }

    public List<ProjectConfiguration> getProjects() {
        return config().getProjects();
    }

    public List<GroupConfiguration> getGroups() {
        return config().getGroups();
    }

    private LayoutConfiguration config() {
        try {
            return layoutConfigurationCache.get("layoutConfiguration", () -> LayoutConfiguration.fromJson(resourceLoader.getAsString(configFilePath)));
        } catch (ExecutionException e) {
            LOGGER.error("Can't load layout configuration from file! Returning empty.", e);
            return new LayoutConfiguration();
        }
    }

}
