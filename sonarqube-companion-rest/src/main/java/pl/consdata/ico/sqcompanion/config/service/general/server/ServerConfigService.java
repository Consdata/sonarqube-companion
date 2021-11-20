package pl.consdata.ico.sqcompanion.config.service.general.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.ServerAuthentication;
import pl.consdata.ico.sqcompanion.config.model.ServerDefinition;
import pl.consdata.ico.sqcompanion.config.service.SettingsService;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.config.validation.general.server.ServerConfigValidator;
import pl.consdata.ico.sqcompanion.event.EventService;
import pl.consdata.ico.sqcompanion.event.EventsFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServerConfigService {

    private final ServerConfigValidator validator;
    private final SettingsService settingsService;
    private final AppConfig appConfig;
    private final EventService eventService;
    private final EventsFactory eventsFactory;

    public ValidationResult update(final ServerDefinition serverDefinition) {
        log.info("Update server with {}", serverDefinition);
        ValidationResult validationResult = validator.validate(serverDefinition);
        ServerDefinition oldDefinition = null;
        if (validationResult.isValid()) {
            ServerDefinition server = appConfig.getServer(serverDefinition.getUuid());
            oldDefinition = server.toBuilder().build();
            server.setId(serverDefinition.getId());
            server.setUrl(serverDefinition.getUrl());
            server.setAuthentication(serverDefinition.getAuthentication());
            server.setBlacklistUsers(serverDefinition.getBlacklistUsers());
            server.setAliases(serverDefinition.getAliases());
            validationResult = settingsService.save();
        } else {
            log.info("Invalid server definition {} reason: {}", serverDefinition, validationResult);
        }

        if (validationResult.isValid()) {
            eventService.addEvent(eventsFactory.serverUpdate(serverDefinition, oldDefinition));
        }
        return validationResult;
    }

    public void create() {
        log.info("Create server");
        String uuid = UUID.randomUUID().toString();
        appConfig.getServers().add(ServerDefinition.builder()
                .uuid(uuid)
                .id(uuid)
                .aliases(new ArrayList<>())
                .blacklistUsers(new ArrayList<>())
                .url("http://localhost:9000")
                .authentication(new ServerAuthentication("none", emptyMap()))
                .build());
        settingsService.save();
    }


    public ValidationResult delete(final String uuid) {
        log.info("Delete server with id {}", uuid);
        appConfig.getServers().removeIf(server -> server.getId().equals(uuid));
        ValidationResult result = settingsService.save();
        if (result.isValid()) {
            log.info("Server {} removed permanently", uuid);
        }
        return result;
    }

    public List<ServerDefinition> get() {
        log.info("Get servers list");
        return appConfig.getServers();
    }
}

