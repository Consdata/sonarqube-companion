package net.lipecki.sqcompanion.repository;

import net.lipecki.sqcompanion.sonarqube.SonarQubeService;
import net.lipecki.sqcompanion.sonarqube.issue.SonarQubeIssuesResultDto;
import net.lipecki.sqcompanion.sonarqube.timemachine.SonarQubeTimeMachineResultCellsDto;
import net.lipecki.sqcompanion.sonarqube.timemachine.SonarQubeTimeMachineResultsDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by gregorry on 27.09.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryServiceTest {

    public static final String P1 = "p1";
    public static final String P2 = "p2";
    private static final String G1 = "g1";
    @InjectMocks
    private RepositoryService service;
    @Mock
    private SonarQubeService sonarQubeServiceMock;
    @Mock
    private LayoutProvider layoutProviderMock;
    private SonarQubeIssuesResultDto p1issues = new SonarQubeIssuesResultDto();
    private SonarQubeIssuesResultDto p2issues = new SonarQubeIssuesResultDto();
    private SonarQubeTimeMachineResultsDto p1history = new SonarQubeTimeMachineResultsDto();
    private SonarQubeTimeMachineResultsDto p2history = new SonarQubeTimeMachineResultsDto();

    @Before
    public void setUpLayoutMocks() {
        final ProjectConfiguration project1 = new ProjectConfiguration(P1, P1, P1);
        final ProjectConfiguration project2 = new ProjectConfiguration(P2, P2, P2);

        when(layoutProviderMock.getProjects()).thenReturn(Arrays.asList(project1, project2));

        final GroupConfiguration group = new GroupConfiguration(G1, G1, Arrays.asList(P1, P2));
        when(layoutProviderMock.getGroups()).thenReturn(Arrays.asList(group));
    }

    @Before
    public void setUpSonarQubeServiceMocks() {
        when(sonarQubeServiceMock.getIssues(P1)).thenReturn(p1issues);
        when(sonarQubeServiceMock.getIssues(P2)).thenReturn(p2issues);

        when(sonarQubeServiceMock.getHistory(P1)).thenReturn(p1history);
        when(sonarQubeServiceMock.getHistory(P2)).thenReturn(p2history);
    }

    @Test
    public void shouldLoadData() {
        // when
        service.loadData();

        // then
        assertThat(service.getRepositoryModel()).isNotNull();
    }

    @Test
    public void shouldUseFirstProjectHistory() {
        // given
        p1history.setCells(new SonarQubeTimeMachineResultCellsDto[]{
                historyCell("2015-05-03", 1),
                historyCell("2015-05-04", 2),
                historyCell("2015-05-05", 3)
        });

        // when
        service.loadData();

        // then
        final Map<LocalDate, HistoryPoint> history = service.getRepositoryModel().getGroup(G1).get().getHistory()
                .getHistoryPoints();

        assertThat(history.values()).hasSize(3);
        assertThat(history.get(LocalDate.of(2015, 5, 3)).getBlockers()).isEqualTo(1);
        assertThat(history.get(LocalDate.of(2015, 5, 4)).getBlockers()).isEqualTo(2);
        assertThat(history.get(LocalDate.of(2015, 5, 5)).getBlockers()).isEqualTo(3);
    }

    @Test
    public void shouldSumProjectsHistory() {
        // given
        p1history.setCells(new SonarQubeTimeMachineResultCellsDto[]{
                historyCell("2015-05-03", 1),
                historyCell("2015-05-04", 2),
                historyCell("2015-05-05", 3)
        });
        p2history.setCells(new SonarQubeTimeMachineResultCellsDto[]{
                historyCell("2015-05-03", 2),
                historyCell("2015-05-04", 3),
                historyCell("2015-05-05", 4)
        });

        // when
        service.loadData();

        // then
        final Map<LocalDate, HistoryPoint> history = service.getRepositoryModel().getGroup(G1).get().getHistory()
                .getHistoryPoints();

        assertThat(history.values()).hasSize(3);
        assertThat(history.get(LocalDate.of(2015, 5, 3)).getBlockers()).isEqualTo(3);
        assertThat(history.get(LocalDate.of(2015, 5, 4)).getBlockers()).isEqualTo(5);
        assertThat(history.get(LocalDate.of(2015, 5, 5)).getBlockers()).isEqualTo(7);
    }

    @Test
    public void shouldUseLastKnownPointForMissingDatesInHistory() {
        // given
        p1history.setCells(new SonarQubeTimeMachineResultCellsDto[]{
                historyCell("2015-05-03", 1),
                historyCell("2015-05-04", 1),
                historyCell("2015-05-05", 1),
                historyCell("2015-05-06", 1),
                historyCell("2015-05-07", 1),
                historyCell("2015-05-10", 1)
        });
        p2history.setCells(new SonarQubeTimeMachineResultCellsDto[]{
                historyCell("2015-05-02", 1),
                historyCell("2015-05-05", 1),
                historyCell("2015-05-07", 1)
        });

        // when
        service.loadData();

        // then
        final Map<LocalDate, HistoryPoint> history = service.getRepositoryModel().getGroup(G1).get().getHistory()
                .getHistoryPoints();

        assertThat(history.get(LocalDate.of(2015, 5, 2)).getBlockers()).isEqualTo(1);
        assertThat(history.get(LocalDate.of(2015, 5, 3)).getBlockers()).isEqualTo(2);
        assertThat(history.get(LocalDate.of(2015, 5, 4)).getBlockers()).isEqualTo(2);
        assertThat(history.get(LocalDate.of(2015, 5, 5)).getBlockers()).isEqualTo(2);
        assertThat(history.get(LocalDate.of(2015, 5, 6)).getBlockers()).isEqualTo(2);
        assertThat(history.get(LocalDate.of(2015, 5, 7)).getBlockers()).isEqualTo(2);
        assertThat(history.get(LocalDate.of(2015, 5, 10)).getBlockers()).isEqualTo(2);
    }

    private SonarQubeTimeMachineResultCellsDto historyCell(final String date, final int blockers) {
        try {
            final SonarQubeTimeMachineResultCellsDto cell = new SonarQubeTimeMachineResultCellsDto();
            cell.setD(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            cell.setV(new String[]{
                    String.valueOf(blockers),
                    "0",
                    "0",
                    "0",
                    "0"
            });
            return cell;
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
