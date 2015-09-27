package net.lipecki.sqcompanion.repository;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;

/**
 * Created by gregorry on 26.09.2015.
 */
public class LayoutProviderTest {

    private LayoutProvider provider;

    private ResourceLoader resourceLoader = new ResourceLoader();

    @Before
    public void setUpProvider() {
        this.provider = new LayoutProvider(resourceLoader, 0, TimeUnit.NANOSECONDS,
                "classpath:layoutConfiguration-test.json");
    }

    @Test
    public void shouldStartWithoutException() {
        assertThat(provider).isNotNull();
    }


    @Test
    public void shouldParseProjcets() {
        assertThat(provider.getProjects()).isNotEmpty();
    }

    @Test
    public void shouldParseExpectedProject() {
        assertThat(extractProperty("name").from(provider.getProjects())).contains("Sample 1");
        assertThat(extractProperty("key").from(provider.getProjects())).contains("sample-1");
        assertThat(extractProperty("sonarqubeKey").from(provider.getProjects())).contains("sq-sample-1");
    }

}
