package net.lipecki.sqcompanion.repository;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Group {

	private final String uuid;
	private final String name;
	private final List<Group> groups;
	private final List<Project> projects;

	@FunctionalInterface
	public interface GroupVisitor {

		void visit(final Group group);

	}


	public void accept(final GroupVisitor visitor) {
		visitor.visit(this);
		groups.forEach(gr -> gr.accept(visitor));
	}

}
