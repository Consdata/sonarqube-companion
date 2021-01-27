package pl.consdata.ico.sqcompanion.config.service.member;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.config.model.Member;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings/member")
public class MemberConfigController {
    private final MemberConfigService service;

    @ApiOperation(value = "Get all members",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Member> getAllMembers() {
        log.info("Get all members");
        return service.getAll();
    }

    @ApiOperation(value = "Get group members",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/group/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Member> getAllMembers(@PathVariable String groupId) {
        log.info("Get all members");
        return service.getAll();
    }

    @ApiOperation(value = "Create member definition",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResult createMember(@RequestBody Member member) {
        log.info("Create member {}", member);
        return service.create(member);
    }

    @ApiOperation(value = "Update member",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResult updateMember(@RequestBody Member member) {
        log.info("Update member {}", member);
        return service.update(member);
    }

    @ApiOperation(value = "Delete member",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResult deleteMember(@PathVariable String uuid) {
        log.info("Delete member {}", uuid);
        return service.delete(uuid);
    }

    @ApiOperation(value = "Get members integration summary",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/integrations", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> getMemberGroups() {
        log.info("Get member integrations summary");
        return service.getIntegrationsSummary();
    }
}
