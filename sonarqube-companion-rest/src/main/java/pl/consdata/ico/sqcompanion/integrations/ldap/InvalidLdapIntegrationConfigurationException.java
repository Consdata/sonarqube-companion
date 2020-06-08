package pl.consdata.ico.sqcompanion.integrations.ldap;

public class InvalidLdapIntegrationConfigurationException extends RuntimeException{
    public InvalidLdapIntegrationConfigurationException(String msg){
        super(msg);
    }
}
