export class GroupEvent {

  type: string;
  name: string;
  description: string;
  data: {[key: string]: any};

  constructor(data) {
    this.type = data.type;
    this.name = data.name;
    this.description = data.description || '';
    this.data = data.data;
  }

}
