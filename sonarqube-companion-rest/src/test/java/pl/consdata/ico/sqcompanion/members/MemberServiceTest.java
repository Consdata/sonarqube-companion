package pl.consdata.ico.sqcompanion.members;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import pl.consdata.ico.sqcompanion.BaseItTest;
import pl.consdata.ico.sqcompanion.config.model.GroupLightModel;
import pl.consdata.ico.sqcompanion.config.model.Member;
import pl.consdata.ico.sqcompanion.sync.SynchronizationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class MemberServiceTest extends BaseItTest {
    @Test
    public void shouldAddLocalUsersToDb() throws SynchronizationException {
        //given
        //TestAppConfig
        //when
        tickSynchronization();

        //then
        List<MemberEntryEntity> entries = memberRepository.findAll();
        assertThat(entries).hasSize(1);
        assertThat(entries.get(0).getId()).isEqualTo("member1");
    }


    @Test
    public void shouldSetAttachedForNewEntries() throws SynchronizationException {
        //given
        //TestAppConfig

        //when
        tickSynchronization();

        //then
        List<MembershipEntryEntity> entries = membershipRepository.findAll();
        assertThat(entries).hasSize(1);
        assertThat(entries.get(0).getEvent()).isEqualTo(MembershipEntryEntity.Event.ATTACHED);
        assertThat(entries.get(0).getGroupId()).isEqualTo("group1");
        assertThat(entries.get(0).getMember().getId()).isEqualTo("member1");
    }


    @Test
    public void shouldChangeStatusIfDetached() throws SynchronizationException {
        //given
        MemberEntryEntity member = memberRepository.save(MemberEntryEntity.builder().id("member1").build());
        membershipRepository.save(MembershipEntryEntity.builder()
                .membershipId(1L)
                .groupId("group1")
                .event(MembershipEntryEntity.Event.DETACHED)
                .date(LocalDate.now().minusDays(3))
                .member(member)
                .build());

        //when
        tickSynchronization();

        //then
        Optional<MembershipEntryEntity> entry = membershipRepository.findFirstByMemberIdAndGroupIdOrderByDateDesc("member1", "group1");
        assertThat(entry).isPresent();
        assertThat(entry.get().getEvent()).isEqualTo(MembershipEntryEntity.Event.ATTACHED);
        assertThat(entry.get().getDate()).isEqualTo(LocalDate.now());
        assertThat(entry.get().getGroupId()).isEqualTo("group1");
        assertThat(entry.get().getMember().getId()).isEqualTo("member1");
    }


    @Test
    public void shouldSetAttachedOnExistingEntryIfLocalDateIsEqual() throws SynchronizationException {
        //given
        MemberEntryEntity member = memberRepository.save(MemberEntryEntity.builder().id("member1").build());
        membershipRepository.save(MembershipEntryEntity.builder()
                .groupId("group1")
                .event(MembershipEntryEntity.Event.DETACHED)
                .date(LocalDate.now())
                .member(member)
                .build());

        //when
        tickSynchronization();

        //then
        List<MembershipEntryEntity> entries = membershipRepository.findAll();
        assertThat(entries).hasSize(1);
        assertThat(entries.get(0).getEvent()).isEqualTo(MembershipEntryEntity.Event.ATTACHED);
        assertThat(entries.get(0).getDate()).isEqualTo(LocalDate.now());
        assertThat(entries.get(0).getGroupId()).isEqualTo("group1");
        assertThat(entries.get(0).getMember().getId()).isEqualTo("member1");
    }

    @Test
    public void shouldNotCreateNewEntryIfAlreadyAttached() throws SynchronizationException {
        //given
        MemberEntryEntity member = memberRepository.save(MemberEntryEntity.builder().id("member1").build());
        membershipRepository.save(MembershipEntryEntity.builder()
                .groupId("group1")
                .event(MembershipEntryEntity.Event.ATTACHED)
                .date(LocalDate.now().minusDays(3))
                .member(member)
                .build());

        //when
        tickSynchronization();

        //then
        List<MembershipEntryEntity> entries = membershipRepository.findAll();
        assertThat(entries).hasSize(1);
        assertThat(entries.get(0).getEvent()).isEqualTo(MembershipEntryEntity.Event.ATTACHED);
        assertThat(entries.get(0).getDate()).isEqualTo(LocalDate.now().minusDays(3));
        assertThat(entries.get(0).getGroupId()).isEqualTo("group1");
        assertThat(entries.get(0).getMember().getId()).isEqualTo("member1");
    }


    @Test
    public void shouldNotSetDetachedForNewEntries() throws SynchronizationException {
        //given
        //TestAppConfig

        //when
        tickSynchronization();

        //then
        Optional<MembershipEntryEntity> entry = membershipRepository.findFirstByMemberIdAndGroupIdOrderByDateDesc("member1", "group1");
        assertThat(entry).isPresent();
        assertThat(entry.get().getEvent()).isEqualTo(MembershipEntryEntity.Event.ATTACHED);
        assertThat(entry.get().getDate()).isEqualTo(LocalDate.now());
    }


    @Test
    public void shouldChangeStatusIfAttached() throws SynchronizationException {
        //given
        MemberEntryEntity member = memberRepository.save(MemberEntryEntity.builder().id("member1").build());
        membershipRepository.save(MembershipEntryEntity.builder()
                .groupId("group2")
                .event(MembershipEntryEntity.Event.ATTACHED)
                .date(LocalDate.now().minusDays(3))
                .member(member)
                .build());

        //when
        tickSynchronization();

        //then
        Optional<MembershipEntryEntity> entry = membershipRepository.findFirstByMemberIdAndGroupIdOrderByDateDesc("member1", "group2");
        assertThat(entry).isPresent();
        assertThat(entry.get().getEvent()).isEqualTo(MembershipEntryEntity.Event.DETACHED);
        assertThat(entry.get().getDate()).isEqualTo(LocalDate.now());
    }


    @Test
    public void shouldSetDetachedOnExistingEntryIfLocalDateIsEqual() throws SynchronizationException {
        //given
        MemberEntryEntity member = memberRepository.save(MemberEntryEntity.builder().id("member1").build());
        membershipRepository.save(MembershipEntryEntity.builder()
                .groupId("group2")
                .event(MembershipEntryEntity.Event.ATTACHED)
                .date(LocalDate.now())
                .member(member)
                .build());

        //when
        tickSynchronization();

        //then
        List<MembershipEntryEntity> entries = membershipRepository.findAll().stream().filter(e -> "group2".equals(e.getGroupId())).collect(Collectors.toList());
        assertThat(entries).hasSize(1);
        assertThat(entries.get(0).getEvent()).isEqualTo(MembershipEntryEntity.Event.DETACHED);
        assertThat(entries.get(0).getDate()).isEqualTo(LocalDate.now());
        assertThat(entries.get(0).getGroupId()).isEqualTo("group2");
        assertThat(entries.get(0).getMember().getId()).isEqualTo("member1");
    }

    @Test
    public void shouldNotCreateNewEntryIfAlreadyDetached() throws SynchronizationException {
        //given
        MemberEntryEntity member = memberRepository.save(MemberEntryEntity.builder().id("member1").build());
        membershipRepository.save(MembershipEntryEntity.builder()
                .groupId("group2")
                .event(MembershipEntryEntity.Event.DETACHED)
                .date(LocalDate.now().minusDays(3))
                .member(member)
                .build());

        //when
        tickSynchronization();

        //then
        List<MembershipEntryEntity> entries = membershipRepository.findAll().stream().filter(e -> "group2".equals(e.getGroupId())).collect(Collectors.toList());
        assertThat(entries).hasSize(1);
        assertThat(entries.get(0).getEvent()).isEqualTo(MembershipEntryEntity.Event.DETACHED);
        assertThat(entries.get(0).getDate()).isEqualTo(LocalDate.now().minusDays(3));
        assertThat(entries.get(0).getGroupId()).isEqualTo("group2");
        assertThat(entries.get(0).getMember().getId()).isEqualTo("member1");
    }

    @Test
    public void shouldReturnGroupsForMemberInGivenPeriod() throws SynchronizationException {
        //given
        MemberEntryEntity member = memberRepository.save(MemberEntryEntity.builder().id("member1").build());
        tickSynchronization();
        membershipRepository.save(MembershipEntryEntity.builder()
                .groupId("group2")
                .event(MembershipEntryEntity.Event.ATTACHED)
                .date(LocalDate.now().minusDays(3))
                .member(member)
                .build());

        //when
        List<GroupLightModel> groups = memberService.memberGroups("member1", LocalDate.now().minusDays(4), LocalDate.now().minusDays(2));

        //then
        assertThat(groups).hasSize(1);
    }

    @Test
    public void shouldReturnCurrentGroupsForMember() throws SynchronizationException {
        //given
        MemberEntryEntity member = memberRepository.save(MemberEntryEntity.builder().id("member1").build());
        tickSynchronization();
        membershipRepository.save(MembershipEntryEntity.builder()
                .groupId("group2")
                .event(MembershipEntryEntity.Event.ATTACHED)
                .date(LocalDate.now().minusDays(3))
                .member(member)
                .build());


        //when
        List<GroupLightModel> groups = memberService.memberGroups("member1");

        //then
        assertThat(groups).hasSize(2);
    }

    @Test
    public void shouldReturnLatestAttachedMembersOnlyForCompletedDays() throws SynchronizationException {
        //given`
        MemberEntryEntity member = memberRepository.save(MemberEntryEntity.builder().id("member2").build());
        membershipRepository.save(MembershipEntryEntity.builder()
                .groupId("group1")
                .event(MembershipEntryEntity.Event.ATTACHED)
                .date(LocalDate.now().minusDays(3))
                .member(member)
                .build());
        tickSynchronization();

        //when
        Set<Member> members = memberService.groupMembers("group1");

        //then
        assertThat(members).hasSize(1);
    }

    @Test
    public void shouldReturnAttachedMembersInGivenPeriod() throws SynchronizationException {
        //given
        MemberEntryEntity member = memberRepository.save(MemberEntryEntity.builder().id("member2").build());
        membershipRepository.save(MembershipEntryEntity.builder()
                .groupId("group1")
                .event(MembershipEntryEntity.Event.ATTACHED)
                .date(LocalDate.now().minusDays(3))
                .member(member)
                .build());
        tickSynchronization();

        //when
        Set<Member> members = memberService.groupMembers("group1", LocalDate.now().minusDays(4), LocalDate.now().minusDays(2));

        //then
        assertThat(members).hasSize(1);
    }

}