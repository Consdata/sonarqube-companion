package com.consdata.echo.storage;

import com.consdata.echo.issues.Issue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "issues")
public class IssueEntity {
    @Id
    private String id;

    public static IssueEntity of(Issue issue) {
        IssueEntity entity = new IssueEntity();
        entity.id = issue.id();
        return entity;
    }

    public static List<IssueEntity> of(List<Issue> issues) {
        return issues.stream().map(IssueEntity::of).toList();
    }

    public static Issue toIssue(IssueEntity entity) {
        return new Issue(entity.id);
    }

    public static List<Issue> toIssue(List<IssueEntity> entities) {
        return entities.stream().map(IssueEntity::toIssue).toList();
    }
}
