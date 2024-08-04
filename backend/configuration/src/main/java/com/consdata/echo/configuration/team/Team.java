package com.consdata.echo.configuration.team;

import java.util.List;

public record Team(String name, List<TeamMember> members) {
}
