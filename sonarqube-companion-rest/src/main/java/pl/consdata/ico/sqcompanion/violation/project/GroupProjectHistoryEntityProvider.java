package pl.consdata.ico.sqcompanion.violation.project;

import lombok.RequiredArgsConstructor;
import pl.consdata.ico.sqcompanion.config.model.Member;
import pl.consdata.ico.sqcompanion.members.MemberService;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.violation.user.diff.UserViolationDiffRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GroupProjectHistoryEntityProvider implements ProjectHistoryEntryEntityProvider {

    private final Group group;
    private final UserViolationDiffRepository userViolationDiffRepository;
    private final MemberService memberService;

    @Override
    public Optional<ProjectHistoryEntryEntity> getEntity(Project project, LocalDate date) {
        List<String> aliases = memberService.groupMembers(group.getUuid()).stream()
                //TODO aliases
                .map(Member::getMail)
                .collect(Collectors.toList());
        aliases.add("bradlinski@consdata.pl");
        aliases.add("bradlinski@consdata.com");
        aliases.add("adeja@consdata.pl");
        aliases.add("mpogorzelski@consdata.pl");
        ProjectHistoryEntryEntity entryEntity = new ProjectHistoryEntryEntity();
        entryEntity.setId(project.getId());
        entryEntity.setDate(date);
        entryEntity.setProjectKey(project.getKey());
        entryEntity.setServerId(project.getServerId());
        entryEntity.setInfos(0);
        entryEntity.setMinors(0);
        entryEntity.setMajors(0);
        entryEntity.setCriticals(0);
        entryEntity.setBlockers(0);
        userViolationDiffRepository.findByUserIdInAndProjectKeyAndDate(aliases, project.getKey(), date)
                .forEach(uv -> {
                    entryEntity.setBlockers(entryEntity.getBlockers() + uv.getBlockers());
                    entryEntity.setCriticals(entryEntity.getCriticals() + uv.getCriticals());
                    entryEntity.setMajors(entryEntity.getMajors() + uv.getMajors());
                    entryEntity.setMinors(entryEntity.getMinors() + uv.getMinors());
                    entryEntity.setInfos(entryEntity.getInfos() + uv.getInfos());
                });

        return Optional.of(entryEntity);
    }
}
