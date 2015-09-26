package net.lipecki.sqcompanion.group;

public class GroupInfoBuilder {
    private String id;
    private String name;

    public GroupInfoBuilder setId(final String id) {
        this.id = id;
        return this;
    }

    public GroupInfoBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public GroupInfo createGroupInfo() {
        return new GroupInfo(id, name);
    }
}