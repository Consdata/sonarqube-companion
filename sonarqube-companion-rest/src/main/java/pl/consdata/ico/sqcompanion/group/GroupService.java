package pl.consdata.ico.sqcompanion.group;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.members.MemberService;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.violation.Violations;
import pl.consdata.ico.sqcompanion.violation.group.summary.GroupViolationsHistoryService;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {
    private final RepositoryService repositoryService;
    private final MemberService memberService;
    private final GroupViolationsHistoryService groupViolationsHistoryService;

    @Cacheable(value = Caches.GROUP_DETAILS_CACHE, sync = true, key = "#group.uuid")
    public GroupDetails getGroupDetails(Group group) {
        return GroupDetails
                .builder()
                .uuid(group.getUuid())
                .name(group.getName())
                .projects(group.getAllProjects().size())
                .members(memberService.groupMembers(group.getUuid()).size())
                .events(0)
                .build();
    }

    @Cacheable(value = Caches.ALL_PROJECTS_DETAILS_CACHE, sync = true)
    public GroupDetails getRootGroupDetails() {
        Group group = repositoryService.getRootGroup();

        return GroupDetails
                .builder()
                .uuid(group.getUuid())
                .name(group.getName())
                .projects(group.getAllProjects().size())
                .members(memberService.groupMembers(group.getUuid()).size())
                .events(0)
                .build();
    }

    public Violations getViolations(Group group) {
        return groupViolationsHistoryService.getViolations(group.getUuid());
    }
}
