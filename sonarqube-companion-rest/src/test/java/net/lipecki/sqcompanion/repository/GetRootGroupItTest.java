package net.lipecki.sqcompanion.repository;

import org.junit.Ignore;
import org.junit.Test;
import net.lipecki.sqcompanion.BaseItTest;
import net.lipecki.sqcompanion.TestAppConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author gregorry
 */
public class GetRootGroupItTest extends BaseItTest {

	@Ignore
	@Test
	public void shouldLoadRootGroup() {
		// when
		final Group rootGroup = repositoryService.getRootGroup();

		// then
		assertThat(rootGroup).isNotNull();
		assertThat(rootGroup.getProjects()).isNotEmpty();
		assertThat(rootGroup.getProjects().get(0).getKey()).isEqualTo(TestAppConfig.RootGroup.Project1.KEY);
	}

}
