package net.lipecki.sqcompanion.sonarqube;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;

@Configuration
public class SonarQubeInjector {

    @Bean
    public SonarQubeServiceOld sonarQubeMetricCollector(final SonarQubeConnector sonarQubeConnector) {
        return new SonarQubeServiceOld(sonarQubeConnector);
    }

    @Bean
    public SonarQubeService sonarQubeService(final SonarQubeConnector sonarQubeConnector) {
        return new SonarQubeService(sonarQubeConnector);
    }

    @Bean
    public SonarQubeConnector sonarQubeConnector() {
        return new SonarQubeConnector(sonarQubeUrl, restTemplate());
    }

    @Bean
    public RestTemplate restTemplate() {
        try {
            // SSL
            final KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            try (final InputStream keystoreInputStream = resourceLoader.getResource(keystoreFile).getInputStream()) {
                trustStore.load(keystoreInputStream, keystorePassword.toCharArray());
            }
            final SSLContext sslContext = SSLContexts
                    .custom()
                    .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                    .build();
            final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    new DefaultHostnameVerifier()
            );

            // Basic authentication
            final BasicCredentialsProvider credentialsProvider =  new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(sonarQubeUsername, sonarQubePassword));

            // Request factory
            final CloseableHttpClient httpClient = HttpClientBuilder
                    .create()
                    .setSSLSocketFactory(sslsf)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .build();

            // RequestFactory with preemptive authentication for sonarqube
            final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory
                    (httpClient) {

                @Override
                protected HttpContext createHttpContext(final HttpMethod httpMethod, final URI uri) {
                    final HttpClientContext httpContext = HttpClientContext.create();

                    final AuthCache authCache = new BasicAuthCache();
                    final BasicScheme basicScheme = new BasicScheme();
                    authCache.put(URIUtils.extractHost(URI.create(sonarQubeUrl)), basicScheme);
                    httpContext.setAuthCache(authCache);

                    return httpContext;
                }
            };

            // RestTemplate
            final RestTemplate restTemplate = new RestTemplate(requestFactory);

            return restTemplate;
        } catch (Exception ex) {
            throw new RuntimeException("Can't create RestTemplate", ex);
        }
    }

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${sonarqube.url}")
    private String sonarQubeUrl;

    @Value("${sonarqube.username}")
    private String sonarQubeUsername;

    @Value("${sonarqube.password}")
    private String sonarQubePassword;

    @Value("${app.keystore.file}")
    private String keystoreFile;

    @Value("${app.keystore.password}")
    private String keystorePassword;

}
