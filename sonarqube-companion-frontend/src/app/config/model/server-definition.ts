import {AuthenticationData} from './authentication-data';

export class ServerDefinition {
  id: string;
  uuid: string;
  url: string;
  authentication: AuthenticationData;
  blacklistUsers: string[];
  aliases: string[];


  constructor(data?: any) {
    if (data) {
      this.id = data.id;
      this.uuid = data.uuid;
      this.url = data.url;
      this.authentication = new AuthenticationData(data.authentication);
      this.blacklistUsers = data.blacklistUsers;
      this.aliases = data.aliases;
    } else {
      this.authentication = new AuthenticationData();
    }
  }


}
