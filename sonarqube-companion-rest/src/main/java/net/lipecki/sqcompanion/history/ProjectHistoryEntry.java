package net.lipecki.sqcompanion.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "project_history_entries")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProjectHistoryEntry {

    @Id
    @GeneratedValue
    private Long id;
    private Long blockers;
    private Long criticals;
    private Long majors;
    private Long minors;
    private Long infos;

}
