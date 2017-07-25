export class Violations {

  blockers: number;
  criticals: number;
  majors: number;
  minors: number;
  infos: number;

  constructor(data: any) {
    this.blockers = data.blockers;
    this.criticals = data.criticals;
    this.majors = data.majors;
    this.minors = data.minors;
    this.infos = data.infos;
  }

}
