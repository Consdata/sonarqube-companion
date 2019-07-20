import {AuthenticationData} from './authentication-data';

export class ServerDefinition {
  id: string;
  url: string;
  authentication: AuthenticationData;
  blacklistUsers: string[];


  constructor(data?: any) {
    if (data) {
      this.id = data.id;
      this.url = data.url;
      this.authentication = new AuthenticationData(data.authentication);
      this.blacklistUsers = data.blacklistUsers;
    } else {
      this.authentication = new AuthenticationData();
    }
  }


}
