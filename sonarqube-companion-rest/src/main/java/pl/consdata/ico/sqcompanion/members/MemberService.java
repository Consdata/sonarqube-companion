package pl.consdata.ico.sqcompanion.members;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.GroupLightModel;
import pl.consdata.ico.sqcompanion.config.model.Member;
import pl.consdata.ico.sqcompanion.event.EventService;
import pl.consdata.ico.sqcompanion.event.EventsFactory;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final AppConfig appConfig;
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final MembersIntegrations membersIntegrations;
    private final RepositoryService repositoryService;
    private final EventService eventService;
    private final EventsFactory eventsFactory;

    public void syncMembers() {
        log.info("> Sync members");
        syncLocalMembers();
        syncRemoteMembers();
        log.info("< Synced  members");
    }

    private void syncLocalMembers() {
        log.info("> Sync local members");
        memberRepository.saveAll(appConfig.getMembers().getLocal()
                .stream().map(definition -> MemberEntryEntity.builder()
                        .firstName(definition.getFirstName())
                        .lastName(definition.getLastName())
                        .id(definition.getUuid())
                        .aliases(definition.getAliases())
                        .mail(definition.getMail())
                        .remote(false)
                        .build()
                )
                .collect(Collectors.toList()));
        appConfig.getMembers().getLocal().forEach(this::detachMemberFromGroups);
        appConfig.getMembers().getLocal().forEach(this::attachMemberToGroups);
    }

    private void syncRemoteMembers() {
        log.info("> Sync remote members");
        List<Member> remoteMembers = membersIntegrations.getMembers();
        memberRepository.saveAll(remoteMembers
                .stream().map(definition -> MemberEntryEntity.builder()
                        .firstName(definition.getFirstName())
                        .lastName(definition.getLastName())
                        .mail(definition.getMail())
                        .id(definition.getUuid())
                        .aliases(definition.getAliases())
                        .mail(definition.getMail())
                        .remote(definition.isRemote())
                        .remoteType(definition.getRemoteType())
                        .build()
                )
                .collect(Collectors.toList()));
        remoteMembers.forEach(this::detachMemberFromGroups);
        remoteMembers.forEach(this::attachMemberToGroups);
    }

    private void attachMemberToGroups(Member member) {
        log.info("> Sync attached events");

        final MemberEntryEntity memberEntryEntity = memberRepository.getOne(member.getUuid());
        member.getGroups().forEach(groupId -> attachMemberToGroup(memberEntryEntity, groupId));

        log.info("< Synced attached events");
    }

    private void attachMemberToGroup(MemberEntryEntity memberEntryEntity, String groupId) {
        Optional<MembershipEntryEntity> latestEvent = membershipRepository.findFirstByMemberIdAndGroupIdOrderByDateDesc(memberEntryEntity.getId(), groupId);

        if (latestEvent.isPresent() && !latestEvent.get().getDate().isBefore(LocalDate.now())) {
            latestEvent.get().setEvent(MembershipEntryEntity.Event.ATTACHED);
            membershipRepository.save(latestEvent.get());
        } else if (!latestEvent.isPresent() || isDetached(latestEvent.get())) {
            membershipRepository.save(MembershipEntryEntity.builder()
                    .date(LocalDate.now())
                    .event(MembershipEntryEntity.Event.ATTACHED)
                    .member(memberEntryEntity)
                    .groupId(groupId)
                    .build());
            this.eventService.addEvent(eventsFactory.memberAttachedToGroup(memberEntryEntity, groupId));

        }
    }

    private void detachMemberFromGroups(Member member) {
        log.info("> Sync detached events");
        final MemberEntryEntity memberEntryEntity = memberRepository.getOne(member.getUuid());
        membershipRepository.findByMemberId(member.getUuid()).stream()
                .map(GroupsOnlyProjection::getGroupId)
                .filter(groupId -> !member.getGroups().contains(groupId))
                .forEach(groupId -> detachMemberFromGroup(memberEntryEntity, groupId));
        log.info("< Synced detached events");
    }

    private void detachMemberFromGroup(MemberEntryEntity memberEntryEntity, String groupId) {
        Optional<MembershipEntryEntity> latestEvent = membershipRepository.findFirstByMemberIdAndGroupIdOrderByDateDesc(memberEntryEntity.getId(), groupId);
        if (latestEvent.isPresent() && !latestEvent.get().getDate().isBefore(LocalDate.now())) {
            latestEvent.get().setEvent(MembershipEntryEntity.Event.DETACHED);
            membershipRepository.save(latestEvent.get());
        } else if (latestEvent.isPresent() && isAttached(latestEvent.get())) {
            membershipRepository.save(MembershipEntryEntity.builder()
                    .date(LocalDate.now())
                    .event(MembershipEntryEntity.Event.DETACHED)
                    .member(memberEntryEntity)
                    .groupId(groupId)
                    .build());
            this.eventService.addEvent(eventsFactory.memberDetachedFromGroup(memberEntryEntity, groupId));
        }
    }

    public Set<Member> groupMembers(String groupId) {
        return repositoryService.getGroup(groupId)
                .map(Group::getAllGroups)
                .orElse(Collections.emptyList())
                .stream()
                .map(group -> getAttachedMembers(membershipRepository.findByGroupIdAndDateIsLessThanEqualOrderByDateDesc(group.getUuid(), LocalDate.now().minusDays(1))))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Set<String> membersAliases(String groupId) {
        return groupMembers(groupId)
                .stream()
                .map(Member::getAliases)
                .filter(Objects::nonNull)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }


    public Set<Member> groupMembers(String groupId, LocalDate form, LocalDate to) {
        return getAttachedMembers(membershipRepository.findByGroupIdAndDateIsBetweenOrderByDateDesc(groupId, form, to));
    }

    public List<GroupLightModel> memberGroups(String memberId) {
        return getMemberGroups(membershipRepository.findByMemberIdAndDateIsLessThanEqualOrderByDateDesc(memberId, LocalDate.now()));
    }

    public List<GroupLightModel> memberGroups(String memberId, LocalDate form, LocalDate to) {
        return getMemberGroups(membershipRepository.findByMemberIdAndDateIsBetweenOrderByDateDesc(memberId, form, to));
    }

    private void putIfLatestOrSkip(Map<String, MembershipEntryEntity> map, MembershipEntryEntity entryEntity, String key) {
        if (!map.containsKey(key) || entryEntity.getDate().isAfter(map.get(key).getDate())) {
            map.put(key, entryEntity);
        }
    }

    private List<GroupLightModel> getMemberGroups(Set<MembershipEntryEntity> entries) {
        return entries.stream()
                .collect(
                        HashMap<String, MembershipEntryEntity>::new,
                        (map, e) -> putIfLatestOrSkip(map, e, e.getGroupId()),
                        HashMap::putAll
                ).values().stream()
                .filter(this::isAttached)
                .map(entry -> GroupLightModel.builder()
                        .uuid(entry.getGroupId())
                        .name(appConfig.getGroup(entry.getGroupId()).getName())
                        .build())
                .collect(Collectors.toList());
    }

    private Set<Member> getAttachedMembers(Set<MembershipEntryEntity> entries) {
        return entries.stream()
                .collect(
                        HashMap<String, MembershipEntryEntity>::new,
                        (map, e) -> putIfLatestOrSkip(map, e, e.getMember().getId()),
                        HashMap::putAll
                ).values().stream()
                .filter(this::isAttached)
                .map(entry -> Member.builder()
                        .uuid(entry.getMember().getId())
                        .firstName(entry.getMember().getFirstName())
                        .lastName(entry.getMember().getLastName())
                        .mail(entry.getMember().getMail())
                        .remoteType(entry.getMember().getRemoteType())
                        .remote(entry.getMember().isRemote())
                        .aliases(entry.getMember().getAliases())
                        .build()).collect(Collectors.toSet());
    }

    private boolean isAttached(MembershipEntryEntity entryEntity) {
        return MembershipEntryEntity.Event.ATTACHED.equals(entryEntity.getEvent());
    }

    private boolean isDetached(MembershipEntryEntity entryEntity) {
        return MembershipEntryEntity.Event.DETACHED.equals(entryEntity.getEvent());
    }

    public Map<String, Long> getIntegrationsSummary() {
        return membersIntegrations.getSummary();
    }
}
