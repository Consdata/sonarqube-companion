package pl.consdata.ico.sqcompanion.members;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.GroupLightModel;
import pl.consdata.ico.sqcompanion.config.model.Member;

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

    public void syncMembers() {
        syncLocalMembers();
    }

    public void syncLocalMembers() {
        log.info("> Sync local members");
        memberRepository.saveAll(appConfig.getMembers().getLocal()
                .stream().map(definition -> MemberEntryEntity.builder()
                        .firstName(definition.getFirstName())
                        .lastName(definition.getLastName())
                        .id(definition.getUuid())
                        .aliases(definition.getAliases())
                        .build()
                )
                .collect(Collectors.toList()));
        appConfig.getMembers().getLocal().forEach(this::addLocalAttachedEvents);
        appConfig.getMembers().getLocal().forEach(this::addLocalDetachedEvents);
        log.info("< Done");
    }

    private void addLocalAttachedEvents(Member member) {
        log.info("> Sync attached events");

        final MemberEntryEntity memberEntryEntity = memberRepository.getOne(member.getUuid());


        member.getGroups().forEach(groupId -> {
                    Optional<MembershipEntryEntity> latestEvent = membershipRepository.findFirstByMemberIdAndGroupIdOrderByDateDesc(member.getUuid(), groupId);

                    if (latestEvent.isPresent() && !latestEvent.get().getDate().isBefore(LocalDate.now())) {
                        latestEvent.get().setEvent(MembershipEntryEntity.Event.ATTACHED);
                        membershipRepository.save(latestEvent.get());
                    } else if (!latestEvent.isPresent() || latestEvent.get().getEvent().equals(MembershipEntryEntity.Event.DETACHED)) {
                        membershipRepository.save(MembershipEntryEntity.builder()
                                .date(LocalDate.now())
                                .event(MembershipEntryEntity.Event.ATTACHED)
                                .member(memberEntryEntity)
                                .groupId(groupId)
                                .build());

                    }
                }
        );


        log.info("< Done");
    }

    private void addLocalDetachedEvents(Member member) {
        log.info("> Sync detachedEvent");
        final MemberEntryEntity memberEntryEntity = memberRepository.getOne(member.getUuid());

        Set<GroupsOnlyProjection> groupsProjection = membershipRepository.findDistinctByMemberIdOrderByDateDesc(member.getUuid());
        groupsProjection.stream().map(GroupsOnlyProjection::getGroupId).forEach(groupId -> {
                    Optional<MembershipEntryEntity> latestEvent = membershipRepository.findFirstByMemberIdAndGroupIdOrderByDateDesc(member.getUuid(), groupId);

                    if (latestEvent.isPresent() && !latestEvent.get().getDate().isBefore(LocalDate.now())) {
                        latestEvent.get().setEvent(MembershipEntryEntity.Event.DETACHED);
                        membershipRepository.save(latestEvent.get());
                    } else if (latestEvent.get().getEvent().equals(MembershipEntryEntity.Event.ATTACHED)) {
                        membershipRepository.save(MembershipEntryEntity.builder()
                                .date(LocalDate.now())
                                .event(MembershipEntryEntity.Event.DETACHED)
                                .member(memberEntryEntity)
                                .groupId(groupId)
                                .build());

                    }
                }
        );
        log.info("< Done");
    }

    public List<Member> groupMembers(String groupId) {
        return getAttachedMembers(membershipRepository.findDistinctByGroupIdAndDateIsLessThanEqualOrderByDateDesc(groupId, LocalDate.now()));
    }

    public List<Member> groupMembers(String groupId, LocalDate form, LocalDate to) {
        return getAttachedMembers(membershipRepository.findDistinctByGroupIdAndDateIsBetweenOrderByDateDesc(groupId, form, to));
    }

    public List<GroupLightModel> memberGroups(String memberId) {
        return appConfig.getMembers().getLocal().stream()
                .filter(m -> memberId.equals(m.getUuid()))
                .findFirst()
                .map(Member::getGroups)
                .orElse(Collections.emptySet())
                .stream()
                .map(groupId -> GroupLightModel.of(appConfig.getGroup(groupId)))
                .collect(Collectors.toList());
    }

    private List<Member> getAttachedMembers(Set<MembershipEntryEntity> entries) {
        Map<String, MembershipEntryEntity> latestEvents = new HashMap<>();
        entries.forEach(e -> {
            if (latestEvents.containsKey(e.getMember().getId()) && e.getDate().isAfter(latestEvents.get(e.getMember().getId()).getDate())) {
                latestEvents.put(e.getMember().getId(), e);
            } else if (!latestEvents.containsKey(e.getMember().getId())) {
                latestEvents.put(e.getMember().getId(), e);
            }
        });
        return latestEvents.values().stream()
                .filter(e -> e.getEvent().equals(MembershipEntryEntity.Event.ATTACHED))
                .map(entry -> Member.builder()
                        .uuid(entry.getMember().getId())
                        .firstName(entry.getMember().getFirstName())
                        .lastName(entry.getMember().getLastName())
                        .build()).collect(Collectors.toList());
    }
}
