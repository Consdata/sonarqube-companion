package pl.consdata.ico.sqcompanion.config;

public enum ProjectLinkType {

    /**
     * Project configured directly by SonarQube server key.
     * Project name and details based on SonarQube definition.
     */
    DIRECT,

    /**
     * Project configured via Companion project alias.
     * Project name and details based on Companion alias definition.
     */
    ALIAS,

    /**
     * One or more projects configured based on regex match from SonarQube server projects.
     * Project name and details same as with Direct link.
     */
    REGEX

}
