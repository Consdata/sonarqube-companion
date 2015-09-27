package net.lipecki.rest.util;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Base64;

public class RestTemplateWithAuthentication extends RestTemplate {

    public RestTemplateWithAuthentication(final String username, final String password) {
        super(new SimpleClientHttpRequestFactory() {

            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                super.prepareConnection(connection, httpMethod);

                final String authorisation = String.format("%s:%s", username, password);
                final byte[] encodedAuthorisation = Base64.getEncoder().encode(authorisation.getBytes());
                connection.setRequestProperty("Authorization", String.format("Basic %s", new String
                        (encodedAuthorisation)));
            }

        });
    }

}
