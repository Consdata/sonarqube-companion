package net.lipecki.sqcompanion.repository;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Group {

	private String uuid;
	private String name;
	private List<Group> groups;
	private List<Project> projects;

	@FunctionalInterface
	public interface GroupVisitor {

		void visit(final Group group);

	}

	public void accept(final GroupVisitor visitor) {
		visitor.visit(this);
		groups.forEach(gr -> gr.accept(visitor));
	}

	public List<Project> getAllProjects() {
		final Set<Project> projects = new HashSet<>();
		accept(gr -> projects.addAll(gr.getProjects()));
		return new ArrayList<>(projects);
	}

	public List<Group> getAllGroups() {
		final List<Group> groups = new ArrayList<>();
		accept(gr -> groups.add(gr));
		return groups;
	}

	public List<Group> getGroups() {
		return groups != null ? groups : new ArrayList<>();
	}

	public List<Project> getProjects() {
		return projects != null ? projects : new ArrayList<>();
	}

}
