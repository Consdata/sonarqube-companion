package com.consdata.echo.integration.ldap.teams;

import com.consdata.echo.integration.ldap.LdapIntegrationAutoconfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {
        LdapIntegrationAutoconfiguration.class,
        LdapTestConfiguration.class,
        ServletWebServerFactoryAutoConfiguration.class,
        WebMvcAutoConfiguration.class})
@AutoConfigureMockMvc
class LdapIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldThrowUnauthorized() throws Exception {
        mvc.perform(get("/test")).andExpect(status().isUnauthorized());

    }

    @Test
    public void shouldAuthorize() throws Exception {
        mvc.perform(get("/test").with(httpBasic("some.person2", "password"))).andExpect(status().isOk());
    }

    @Test
    public void shouldThrowForbidden() throws Exception {
        mvc.perform(get("/test").with(httpBasic("some.person3", "password"))).andExpect(status().isForbidden());
    }

}
