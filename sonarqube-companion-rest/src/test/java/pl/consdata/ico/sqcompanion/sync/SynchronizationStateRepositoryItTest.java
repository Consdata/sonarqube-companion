package pl.consdata.ico.sqcompanion.sync;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.consdata.ico.sqcompanion.BaseItTest;

import static org.assertj.core.api.Assertions.assertThat;

public class SynchronizationStateRepositoryItTest extends BaseItTest {

    @Autowired
    private SynchronizationStateRepository uut;

    @Before
    public void setUp() {
        uut.deleteAll();
    }

    @Test
    public void shouldReturnNullForEmptyTable() {
        // given

        // when
        Double result = uut.findAverageDurationOfLastSynchronizations();

        // then
        assertThat(result).isNull();
    }

    @Test
    public void shouldReturnAvgForLessThan5FinishedSynchronizations() {
        // given
        addRow(1000L);
        addRow(2000L);
        addRow(3000L);

        // when
        Double result = uut.findAverageDurationOfLastSynchronizations();

        // then
        assertThat(result).isEqualTo(2000.0);
    }

    @Test
    public void shouldReturnAvgForMoreThan5FinishedSynchronizations() {
        // given
        addRow(1000L);
        addRow(2000L);
        addRow(3000L);
        addRow(4000L);
        addRow(5000L);
        addRow(6000L);
        addRow(7000L);

        // when
        Double result = uut.findAverageDurationOfLastSynchronizations();

        // then
        assertThat(result).isEqualTo(5000.0);
    }

    private void addRow(Long duration) {
        uut.saveAndFlush(SynchronizationStateEntity
                .builder()
                .startTimestamp(System.currentTimeMillis())
                .duration(duration)
                .build());
    }

}
