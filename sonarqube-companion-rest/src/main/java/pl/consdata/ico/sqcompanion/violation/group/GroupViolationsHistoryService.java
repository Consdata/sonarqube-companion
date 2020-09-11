package pl.consdata.ico.sqcompanion.violation.group;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.members.MemberService;
import pl.consdata.ico.sqcompanion.violation.user.diff.UserViolationDiffRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupViolationsHistoryService {
    private final UserViolationDiffRepository userViolationDiffRepository;
    private final MemberService memberService;

}
