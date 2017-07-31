package pl.consdata.ico.sqcompanion;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        if (allowSelfSignedHttps) {
            return getRestTemplateAllowingSelfSigned();
        } else {
            return new RestTemplate();
        }
    }

    private RestTemplate getRestTemplateAllowingSelfSigned() {
        try {
            final SSLContext allowSelfSignedSslContext = SSLContexts
                    .custom()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .build();
            final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    allowSelfSignedSslContext,
                    NoopHostnameVerifier.INSTANCE
            );
            final CloseableHttpClient httpClient = HttpClientBuilder
                    .create()
                    .setSSLSocketFactory(sslsf)
                    .build();
            return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
        } catch (final Exception exception) {
            throw new SQCompanionException("Can't initialize RestTemplate with Self Signed https support", exception);
        }
    }

    @Value("${app.allowSelfSignedHttps:false}")
    private boolean allowSelfSignedHttps;

}
