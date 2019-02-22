import {AuthenticationData} from "./authentication-data";

export class ServerDefinition {
  id: string;
  url: string;
  authentication: AuthenticationData;

  constructor(data?: any) {
    if (data) {
      this.id = data.id;
      this.url = data.url;
      this.authentication = new AuthenticationData(data.authentication);
    } else {
      this.authentication = new AuthenticationData();
    }
  }
}
