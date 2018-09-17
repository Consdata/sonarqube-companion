package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.List;

@Data
public class SQUsersSearchResponse extends SQPaginatedResponse {

    private List<SQUser> users;

}
