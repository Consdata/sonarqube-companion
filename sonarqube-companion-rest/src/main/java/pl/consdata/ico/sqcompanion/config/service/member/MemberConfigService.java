package pl.consdata.ico.sqcompanion.config.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.Member;
import pl.consdata.ico.sqcompanion.config.service.SettingsService;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.config.validation.members.MembersValidator;
import pl.consdata.ico.sqcompanion.members.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberConfigService {
    private final AppConfig appConfig;
    private final SettingsService settingsService;
    private final MembersValidator validator;
    private final MemberService memberService;

    public ValidationResult delete(String uuid) {
        ValidationResult validationResult = validator.memberExists(uuid);
        if (validationResult.isValid()) {
            ofNullable(appConfig.getMembers().getLocal()).orElse(new ArrayList<>()).removeIf(m -> uuid.equals(m.getUuid()));
        } else {
            log.info("Unable to delete member, reason: {}", validationResult);
            return validationResult;
        }
        return settingsService.save();
    }

    public ValidationResult update(Member member) {
        ValidationResult validationResult = validator.memberExists(member.getUuid());
        if (validationResult.isValid()) {
            Member updateMember = appConfig.getMember(member.getUuid());
            updateMember.setFirstName(member.getFirstName());
            updateMember.setLastName(member.getLastName());
            updateMember.setMail(member.getMail());
            updateMember.setUuid(member.getUuid());
            updateMember.setAliases(member.getAliases());
            updateMember.setGroups(member.getGroups());
            return settingsService.save();
        } else {
            log.info("Unable to create member definition {} reason: {}", member, validationResult);
            return validationResult;
        }
    }

    public ValidationResult create(Member member) {
        ValidationResult validationResult = validator.memberWithSonarIdNotExists(member.getUuid());
        if (validationResult.isValid()) {
            appConfig.getMembers().getLocal().add(member);
            return settingsService.save();
        } else {
            log.info("Unable to create member definition {} reason: {}", member, validationResult);
            return validationResult;
        }
    }

    public List<Member> getAll() {
        return appConfig.getMembers().getLocal();
    }

    public Map<String, Long> getIntegrationsSummary() {
        return memberService.getIntegrationsSummary();
    }
}
