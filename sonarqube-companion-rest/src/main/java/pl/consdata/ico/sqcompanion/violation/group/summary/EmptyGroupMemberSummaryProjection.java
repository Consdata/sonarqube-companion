package pl.consdata.ico.sqcompanion.violation.group.summary;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;


@RequiredArgsConstructor
public class EmptyGroupMemberSummaryProjection implements GroupMemberSummaryProjection {
    private final LocalDate formDate;
    private final String id;

    @Override
    public String userId() {
        return id;
    }

    @Override
    public LocalDate date() {
        return formDate;
    }

    @Override
    public Integer blockers() {
        return 0;
    }

    @Override
    public Integer criticals() {
        return 0;
    }

    @Override
    public Integer majors() {
        return 0;
    }

    @Override
    public Integer minors() {
        return 0;
    }

    @Override
    public Integer infos() {
        return 0;
    }
}
