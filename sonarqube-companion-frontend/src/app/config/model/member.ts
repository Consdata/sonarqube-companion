export class Member {
  firstName: string;
  lastName: string;
  mail: string;
  uuid: string;
  aliases: string[];
  groups: string[];


  constructor(data: any) {
    this.firstName = data.firstName;
    this.lastName = data.lastName;
    this.mail = data.mail;
    this.uuid = data.uuid;
    this.aliases = data.aliases || [];
    this.groups = data.groups || [];

  }
}
