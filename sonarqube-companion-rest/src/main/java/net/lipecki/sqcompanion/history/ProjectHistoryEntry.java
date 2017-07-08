package net.lipecki.sqcompanion.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "project_history_entries")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProjectHistoryEntry {

    @Id
    @GeneratedValue
    private Long id;
    private String projectKey;
    private Integer blockers;
    private Integer criticals;
    private Integer majors;
    private Integer minors;
    private Integer infos;
    private LocalDate date;

}
