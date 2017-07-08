package net.lipecki.sqcompanion.repository;

import net.lipecki.sqcompanion.BaseItTest;
import net.lipecki.sqcompanion.TestAppConfig;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author gregorry
 */
public class GetRootGroupItTest extends BaseItTest {

	@Test
	public void shouldLoadRootGroup() {
		// when
		tickSynchronization();
		final Group rootGroup = repositoryService.getRootGroup();

		// then
		assertThat(rootGroup).isNotNull();
		assertThat(rootGroup.getProjects()).isNotEmpty();
		assertThat(rootGroup.getProjects().get(0).getKey()).isEqualTo(TestAppConfig.RootGroup.Project1.KEY);
	}

}
