import {WidgetModel} from "../widget-model";

export class RankingModel extends WidgetModel {

  limit: number;
  mode: string;
  severity: string[];
  include: string[] = [];
  exclude: string[] = [];
  from: string;
  to: string;
  server: string;
  sort: string[];
  dynamicRange: boolean;

  constructor(data: any) {
    super(data);

    this.mode = data.mode;
    this.limit = data.limit;
    this.severity = data.severity;
    this.from = data.from;
    this.include = data.include;
    this.exclude = data.exclude;
    this.server = data.server;
    this.sort = data.sort;
    this.dynamicRange = data.dynamicRange;
  }
}

export class RankingEntry {
  name: string;
  blockers: number;
  criticals: number;
  majors: number;
  minors: number;
  infos: number;

  constructor(data: any) {
    this.name = data.name;
    this.blockers = data.blockers ? data.blockers : 0;
    this.criticals = data.criticals ? data.criticals : 0;
    this.majors = data.majors ? data.majors : 0;
    this.minors = data.minors ? data.minors : 0;
    this.infos = data.infos ? data.infos : 0;
  }
}
