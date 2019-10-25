package pl.consdata.ico.sqcompanion.config.service.webhook;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.config.model.WebhookDefinition;
import pl.consdata.ico.sqcompanion.config.validation.SettingsExceptionHandler;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.hook.callback.WebhookCallback;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings/group")
public class WebhookConfigController extends SettingsExceptionHandler {

    private final WebhookConfigService service;

    @ApiOperation(value = "Get group webhooks",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/{uuid}/webhooks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<WebhookDefinition> getWebhooks(@PathVariable String uuid) {
        log.info("Get group webhooks {}", uuid);
        return service.getWebhooks(uuid);
    }

    @ApiOperation(value = "Create group webhook",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{uuid}/webhooks/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult createWebhook(@PathVariable String uuid, @RequestBody WebhookDefinition webhookDefinition) {
        log.info("Create group webhook [{}] {}", uuid, webhookDefinition);
        return service.createWebhook(uuid, webhookDefinition);
    }

    @ApiOperation(value = "Update group webhook",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{uuid}/webhooks/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult updateWebhook(@PathVariable String uuid, @RequestBody WebhookDefinition webhookDefinition) {
        log.info("Update group webhook [{}] {}", uuid, webhookDefinition);
        return service.updateWebhook(uuid, webhookDefinition);
    }

    @ApiOperation(value = "Delete group webhook",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @DeleteMapping(value = "/{uuid}/webhooks/{webhookUuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult deleteWebhook(@PathVariable String uuid, @PathVariable String webhookUuid) {
        log.info("Delete group webhook [{}/{}]", uuid, webhookUuid);
        return service.deleteWebhook(uuid, webhookUuid);
    }

    @ApiOperation(value = "Get webook callbacks",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/{groupUuid}/webhooks/{webhookUuid}/callbacks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<WebhookCallback> getCallbacks(@PathVariable String groupUuid, @PathVariable String webhookUuid) {
        log.info("Get callbacks for {}/{}", groupUuid, webhookUuid);
        return service.getCallbacks(groupUuid, webhookUuid);
    }

    @ApiOperation(value = "Create callback",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{groupUuid}/webhooks/{webhookUuid}/callbacks/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult createCallback(@PathVariable String groupUuid, @PathVariable String webhookUuid, @RequestBody WebhookCallback callback) {
        log.info("Create callback for {}/{}", groupUuid, webhookUuid);
        return service.createCallback(groupUuid, webhookUuid, callback);
    }

    @ApiOperation(value = "Update callback",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{groupUuid}/webhooks/{webhookUuid}/callbacks/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult updateCallback(@PathVariable String groupUuid, @PathVariable String webhookUuid, @RequestBody WebhookCallback callback) {
        log.info("Update callback for {}/{}", groupUuid, webhookUuid);
        return service.updateCallback(groupUuid, webhookUuid, callback);
    }

    @ApiOperation(value = "Delete callback",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @DeleteMapping(value = "/{groupUuid}/webhooks/{webhookUuid}/callbacks/{callbackUuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult getCallbacks(@PathVariable String groupUuid, @PathVariable String webhookUuid, @PathVariable String callbackUuid) {
        log.info("Delete callback {}/{}/{}", groupUuid, webhookUuid, callbackUuid);
        return service.deleteCallback(groupUuid, webhookUuid, callbackUuid);
    }
}