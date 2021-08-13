package pl.consdata.ico.sqcompanion.violation.group.summary;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;


public interface GroupProjectSummaryProjection {
    @Value("#{target.projectKey}")
    String projectKey();

    @Value("#{target.date}")
    LocalDate date();

    @Value("#{target.blockers}")
    Integer blockers();

    @Value("#{target.criticals}")
    Integer criticals();

    @Value("#{target.majors}")
    Integer majors();

    @Value("#{target.minors}")
    Integer minors();

    @Value("#{target.infos}")
    Integer infos();
}
