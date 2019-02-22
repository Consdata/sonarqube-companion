package pl.consdata.ico.sqcompanion.config.service;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.model.SchedulerConfig;
import pl.consdata.ico.sqcompanion.config.model.ServerDefinition;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/settings")
@AllArgsConstructor
public class SettingsController {

    private SettingsService settingsService;

    @GetMapping("/servers")
    public List<ServerDefinition> getServerConfig() {
        return settingsService.getConfig().getServers();
    }

    @GetMapping(value = "/scheduler", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SchedulerConfig setSchedulerConfig() {
        return settingsService.getConfig().getScheduler();
    }

    @ApiOperation(
            value = "Set scheduler configuration."
    )
    @PostMapping(value = "/scheduler", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean setSchedulerConfig(@RequestBody SchedulerConfig config) {
        settingsService.getConfig().setScheduler(config);
        settingsService.store();
        return true;
    }

    @ApiOperation(
            value = "Set servers configuration."
    )
    @PostMapping(value = "/servers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean setServersConfig(@RequestBody List<ServerDefinition> config) {
        settingsService.getConfig().setServers(config);
        settingsService.store();
        return true;
    }

    @GetMapping("/group/root")
    public GroupDefinition getRooConfig() {
        return settingsService.getConfig().getRootGroup();
    }


    @PostMapping("/group/root")
    public boolean setRootGroupConfig(@RequestBody GroupDefinition rootGroup) {
        settingsService.getConfig().setRootGroup(rootGroup);
        settingsService.store();
        return true;
    }

    @GetMapping(value = "/group/{uuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GroupDefinition getGroupConfig(@PathVariable("uuid") String uuid) {
        return settingsService.getGroup(uuid, Collections.singletonList(settingsService.getConfig().getRootGroup()));
    }


    @PostMapping("/group/{uuid}")
    public boolean setGroupConfig(@RequestBody GroupDefinition group, @PathVariable("uuid") String uuid) {
        settingsService.setGroup(uuid, group);
        settingsService.store();
        return true;
    }


    @GetMapping("/restore")
    public boolean restoreDefaults() {
        log.info("Restore default settings");
        return settingsService.restoreDefaultConfig();
    }

    @PostMapping("update")
    public AppConfig update() {
        return null;
    }
}
