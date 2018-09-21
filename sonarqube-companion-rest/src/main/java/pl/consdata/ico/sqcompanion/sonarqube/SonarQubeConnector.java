package pl.consdata.ico.sqcompanion.sonarqube;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.ServerAuthentication;
import pl.consdata.ico.sqcompanion.config.ServerDefinition;
import pl.consdata.ico.sqcompanion.sonarqube.sqapi.SQPaginatedResponse;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author gregorry
 */
@Slf4j
@Service
public class SonarQubeConnector {

    public static final int DEFAULT_PAGE_SIZE = 100;
    // SonarQube WEB Api uses 1-based page indexes
    public static final int FIRST_PAGE = 1;
    public static final String PAGING_TEMPLATE = "p=%d&ps=%d";
    public static final String SERVER_WITH_URI_TEMPLATE = "%s%s";
    public static final String BASIC_AUTH_TEMPLATE = "Basic %s";
    public static final String BASIC_AUTH_VALUE_TEMPLATE = "%s:%s";
    private final AppConfig config;
    private final RestTemplate restTemplate;
    private final Timer paginatedListTimer;

    public SonarQubeConnector(final AppConfig config, final RestTemplate restTemplate, final MeterRegistry meterRegistry) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.paginatedListTimer = meterRegistry.timer("SonarQubeConnector.getForPaginatedList");
    }

    public <T extends SQPaginatedResponse, R> Stream<R> getForPaginatedList(
            final String serverId,
            final String uri,
            final Class<T> responseClass,
            final Function<T, List<R>> dataExtractor) {
        return paginatedListTimer.record(() -> {
            final List<R> result = new ArrayList<>();
            final ServerDefinition server = getServerDefinition(serverId);
            T lastResponse = null;
            do {
                int pageIdx = lastResponse != null ? lastResponse.getNextPage() : FIRST_PAGE;
                final String url = String.format(
                        SERVER_WITH_URI_TEMPLATE + (uri.contains("?") ? "&" : "?") + PAGING_TEMPLATE,
                        server.getUrl(),
                        uri,
                        pageIdx,
                        DEFAULT_PAGE_SIZE
                );

                final HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

                if (server.hasAuthentication()) {
                    addAuthentication(server.getAuthentication(), headers);
                }

                lastResponse = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        new HttpEntity<String>(null, headers),
                        responseClass
                ).getBody();

                result.addAll(dataExtractor.apply(lastResponse));
            } while (lastResponse != null && lastResponse.hasMorePages());

            return result.stream();
        });
    }

    private void addAuthentication(final ServerAuthentication authentication, final HttpHeaders headers) {
        if ("token".equals(authentication.getType())) {
            headers.add(
                    "Authorization",
                    basicAuthorization(
                            authentication.getParams().get("token"),
                            ""
                    )
            );
        } else if ("basic".equals(authentication.getType())) {
            headers.add(
                    "Authorization",
                    basicAuthorization(
                            authentication.getParams().get("user"),
                            authentication.getParams().get("password")
                    )
            );
        } else {
            throw new SQCompanionException("Unknown server authentication type: " + authentication);
        }
    }

    private String basicAuthorization(final String user, final String password) {
        return String.format(
                BASIC_AUTH_TEMPLATE,
                Base64Utils.encodeToString(
                        String
                                .format(BASIC_AUTH_VALUE_TEMPLATE, user, password)
                                .getBytes(StandardCharsets.UTF_8)
                )
        );
    }

    private ServerDefinition getServerDefinition(final String serverId) {
        return config.getServer(serverId);
    }

}
