package pl.consdata.ico.sqcompanion.config.validation;

public class GroupNotFound extends RuntimeException {

    private String uuid;

    public GroupNotFound(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getLocalizedMessage() {
        return String.format("Group %s not found!", uuid);
    }
}
