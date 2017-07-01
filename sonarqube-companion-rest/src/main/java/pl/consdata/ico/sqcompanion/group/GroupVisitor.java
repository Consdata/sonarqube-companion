package pl.consdata.ico.sqcompanion.group;

@FunctionalInterface
public interface GroupVisitor {

    void visit(Group group);

}
