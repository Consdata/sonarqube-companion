package pl.consdata.ico.sqcompanion.config.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.Member;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final AppConfig appConfig;

    public ValidationResult delete(String uuid) {
        return null;
    }

    public ValidationResult update(Member member) {
        return null;
    }

    public ValidationResult create(Member member) {
        return null;
    }

    public List<Member> getAll() {
        return appConfig.getMembers().getLocal();
    }
}
