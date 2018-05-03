export class WidgetModel {
  type: string;
  title: string;

  constructor(data: any) {
    this.type = data.type;
    this.title = data.title;
  }
}
