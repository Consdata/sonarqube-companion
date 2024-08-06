package com.consdata.echo.integration.ldap.configuration.team;

import java.util.List;

public record Team(String id, String name, List<TeamMember> members, List<Team> teams) {
}
