package pl.consdata.ico.sqcompanion.members;

import pl.consdata.ico.sqcompanion.config.model.Member;

import java.util.List;

public interface MembersProvider {
    List<Member> getMembers();
}
