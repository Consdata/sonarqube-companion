export class GroupEvent {
  uuid: string;
  type: string;
  name: string;
  description: string;
  data: { [key: string]: any };

  constructor(data) {
    this.uuid = data.uuid;
    this.type = data.type;
    this.name = data.name;
    this.description = data.description || '';
    this.data = data.data;
  }

}
