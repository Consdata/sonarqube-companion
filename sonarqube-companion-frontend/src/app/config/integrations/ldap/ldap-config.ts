export interface LdapConfig {
  connection: LdapConnectionConfig;
  members: LdapMembersConfig;
  groups: LdapGroupsConfig;
  enabled: boolean;
}

export interface LdapConnectionConfig {
  urls: string[];
  userDn: string;
  password: string;
  base: string;
  anonymous: boolean;
}

export interface LdapMembersConfig {
  memberObjectClass: string;
  mailAttribute: string;
  firstNameAttribute: string;
  lastNameAttribute: string;
  aliasesAttributes: string[];
  idAttribute: string;
}

export interface LdapGroupsConfig {
  groupObjectClass: string;
  groupNameAttribute: string;
  membershipAttribute: string;
  groupMappings: { [key: string]: string };
}

