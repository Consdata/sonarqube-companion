package com.consdata.echo.configuration.organization.team;

import java.util.List;

public record Team(String id, String name, List<Team> teams) {
}
