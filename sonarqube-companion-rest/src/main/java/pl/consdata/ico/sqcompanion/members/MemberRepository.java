package pl.consdata.ico.sqcompanion.members;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntryEntity, String> {
    Long countAllByRemoteAndRemoteType(boolean remote, String remoteType);
}
