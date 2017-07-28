package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SQIssue {

    private String key;
    private String component;
    private String project;
    private String rule;
    private String status;
    private String resulution;
    private String severity;
    private String message;
    private Long line;
    private String author;
    private String effort;
    private Date creationDate;
    private Date updateDate;
    private List<String> tags;
    private String type;
    private String login;
    private String htmlText;
    private String markdown;
    private Boolean updatable;
    private Date createdAt;

    // inne potencjalnie przydatne pola: textRange
    // inne potencjalnie przydatne pola: startLine
    // inne potencjalnie przydatne pola: comments
    // inne potencjalnie przydatne pola: attr / jira-issue-key
    // inne potencjalnie przydatne pola: transitions
    // inne potencjalnie przydatne pola: actions

}
