package pl.consdata.ico.sqcompanion.members;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface MembershipRepository extends JpaRepository<MembershipEntryEntity, Long> {
    Optional<MembershipEntryEntity> findFirstByMemberIdAndGroupIdOrderByDateDesc(String memberId, String groupId);

    Set<GroupsOnlyProjection> findByMemberId(String memberId);

    Set<MembershipEntryEntity> findByGroupIdAndDateIsBetweenOrderByDateDesc(String groupId, LocalDate from, LocalDate to);

    Set<MembershipEntryEntity> findByMemberIdAndDateIsBetweenOrderByDateDesc(String memberId, LocalDate from, LocalDate to);

    Set<MembershipEntryEntity> findByGroupIdAndDateIsLessThanEqualOrderByDateDesc(String groupId, LocalDate to);

    Set<MembershipEntryEntity> findByMemberIdAndDateIsLessThanEqualOrderByDateDesc(String memberId, LocalDate to);

}
