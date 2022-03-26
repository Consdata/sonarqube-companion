package pl.consdata.ico.sqcompanion.group;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.event.EventService;
import pl.consdata.ico.sqcompanion.members.MemberService;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.violation.Violations;
import pl.consdata.ico.sqcompanion.violation.group.summary.GroupViolationsHistoryService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {
    private final RepositoryService repositoryService;
    private final MemberService memberService;
    private final GroupViolationsHistoryService groupViolationsHistoryService;
    private final EventService eventService;

    @Cacheable(value = Caches.GROUP_DETAILS_CACHE, sync = true, key = "#group.uuid")
    public GroupDetails getGroupDetails(Group group) {
        return GroupDetails
                .builder()
                .uuid(group.getUuid())
                .name(group.getName())
                .parentUuid(ofNullable(group.getParentGroups()).filter(list -> !list.isEmpty()).map(list -> list.get(list.size() - 1).getUuid()).orElse("root"))
                .projects(group.getAllProjects().size())
                .groups(group.getGroups().stream().map(this::getGroupDetails).collect(Collectors.toList()))
                .members(memberService.groupMembers(group.getUuid()).size())
                .events(eventService.getByGroup(group.getUuid(), Optional.of(30)).size()) //TODO limit
                .build();
    }

    @Cacheable(value = Caches.ALL_PROJECTS_DETAILS_CACHE, sync = true)
    public GroupDetails getRootGroupDetails() {
        Group group = repositoryService.getRootGroup();

        return GroupDetails
                .builder()
                .uuid(group.getUuid())
                .parentUuid("root")
                .name(group.getName())
                .description(group.getDescription())
                .projects(group.getAllProjects().size())
                .groups(group.getGroups().stream().map(this::getGroupDetails).collect(Collectors.toList()))
                .members(memberService.groupMembers(group.getUuid()).size())
                .events(eventService.getByGroup(group.getUuid(), Optional.of(30)).size())
                .build();
    }

    @Cacheable(value = Caches.GROUP_VIOLATIONS_CACHE, sync = true, key = "#group.uuid + #localDate")
    public Violations getViolations(Group group, LocalDate localDate) {
        return groupViolationsHistoryService.getViolations(group.getUuid(), localDate);
    }

    @Cacheable(value = Caches.GROUP_VIOLATIONS_DIFF_CACHE, sync = true, key = "#group.uuid + #from + #to")
    public Violations getViolationsDiff(Group group, LocalDate from, LocalDate to) {
        return groupViolationsHistoryService.getViolationsDiff(group.getUuid(), from, to);
    }

}
