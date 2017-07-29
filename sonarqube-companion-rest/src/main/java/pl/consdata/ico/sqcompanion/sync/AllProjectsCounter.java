package pl.consdata.ico.sqcompanion.sync;

import pl.consdata.ico.sqcompanion.repository.Group;

/**
 * @author pogoma
 */
public class AllProjectsCounter implements Group.GroupVisitor {

    private long count;

    @Override
    public void visit(Group group) {
        count += group.getProjects().size();
    }

    public long getCount() {
        return count;
    }

}
