package com.consdata.echo.storage;

import com.consdata.echo.issues.Issue;
import com.consdata.echo.issues.IssueStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaIssuesStorage implements IssueStorage {
    private final IssuesRepository issuesRepository;

    @Override
    public void save(List<Issue> issues) {
        issuesRepository.saveAll(IssueEntity.of(issues));
    }
}
