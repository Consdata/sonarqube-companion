package pl.consdata.ico.sqcompanion.members;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.consdata.ico.sqcompanion.config.model.GroupLightModel;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value = "Get all members",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/{uuid}/groups", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupLightModel> getMemberGroups(@PathVariable String uuid) {
        log.info("Get member groups");
        return memberService.memberGroups(uuid);
    }


}
