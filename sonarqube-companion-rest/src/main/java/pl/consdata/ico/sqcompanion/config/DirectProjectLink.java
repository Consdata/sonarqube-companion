package pl.consdata.ico.sqcompanion.config;

public class DirectProjectLink {

    public static final String LINK = "link";

    private final ProjectLink projectLink;

    public DirectProjectLink(final ProjectLink projectLink) {
        this.projectLink = projectLink;
    }

    public static DirectProjectLink of(final ProjectLink projectLink) {
        return new DirectProjectLink(projectLink);
    }

    public String getLink() {
        return (String) projectLink.getConfig().get(LINK);
    }

}
