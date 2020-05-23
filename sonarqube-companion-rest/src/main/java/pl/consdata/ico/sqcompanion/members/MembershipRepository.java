package pl.consdata.ico.sqcompanion.members;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface MembershipRepository extends JpaRepository<MembershipEntryEntity, Long> {
    Optional<MembershipEntryEntity> findFirstByMemberIdAndGroupIdOrderByDateDesc(String memberId, String groupId);

    Set<GroupsOnlyProjection> findDistinctByMemberIdOrderByDateDesc(String memberId);

    Set<MembershipEntryEntity> findDistinctByGroupIdAndDateIsBetweenOrderByDateDesc(String groupId, LocalDate from, LocalDate to);

    Set<MembershipEntryEntity> findDistinctByGroupIdAndDateIsLessThanEqualOrderByDateDesc(String groupId, LocalDate to);

}
