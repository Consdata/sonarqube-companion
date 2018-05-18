export class WidgetModel {
  type: string;
  title: string;
  uuid: string;

  constructor(data: any) {
    this.type = data.type;
    this.title = data.title;
    this.uuid = data.uuid;
  }
}
