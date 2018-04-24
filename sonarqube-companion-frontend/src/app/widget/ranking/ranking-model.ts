export class WidgetModel {
  type: string;
  constructor(data: any){
    this.type = data.type;
  }
}

export class RankingModel extends WidgetModel {
  title: string;
  limit: number;
  mode: string;
  severity: string[];

  constructor(data: any) {
    super(data);
    this.title = data.title;
    this.mode = data.mode;
    this.limit = data.limit;
    this.severity = data.severity;
  }
}
