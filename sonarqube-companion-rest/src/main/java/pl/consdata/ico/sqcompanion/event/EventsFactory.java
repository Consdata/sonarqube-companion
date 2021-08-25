package pl.consdata.ico.sqcompanion.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.model.ServerDefinition;
import pl.consdata.ico.sqcompanion.members.MemberEntryEntity;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

import javax.annotation.Nullable;
import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class EventsFactory {
    private final ObjectMapper objectMapper;
    private final RepositoryService repositoryService;

    @SneakyThrows
    public Event serverUpdate(ServerDefinition serverDefinition, @Nullable ServerDefinition oldDefinition) {
        return Event.builder()
                .type("SERVER_UPDATE")
                .name("Server update(" + serverDefinition.getId() + ")")
                .description("Server definition changed")
                .date(LocalDate.now())
                .log("Server definition changed to " + serverDefinition)
                .showOnTimeline(ofNullable(oldDefinition).map(definition -> !definition.getUrl().equals(serverDefinition.getUrl())).orElse(false))
                .build();
    }

    @SneakyThrows
    public Event memberAttachedToGroup(MemberEntryEntity member, String groupId) {
        return Event.builder()
                .type("MEMBER_ATTACHED")
                .name("New member(" + member.getId() + ")")
                .description("Member " + member.getId() + " attached to group " + repositoryService.getGroup(groupId).map(Group::getName).orElse(""))
                .log("Member " + member + " attached to group " + groupId)
                .date(LocalDate.now())
                .userId(member.getId())
                .groupId(groupId)
                .showOnTimeline(true)
                .build();
    }

    @SneakyThrows
    public Event memberDetachedFromGroup(MemberEntryEntity member, String groupId) {
        return Event.builder()
                .type("MEMBER_DETACHED")
                .name("Member removed(" + member.getId() + ")")
                .description("Member " + member.getId() + " detached from group " + repositoryService.getGroup(groupId).map(Group::getName).orElse(""))
                .log("Member " + member + " detached rom group " + groupId)
                .date(LocalDate.now())
                .userId(member.getId())
                .groupId(groupId)
                .showOnTimeline(true)
                .build();
    }
}
