export class Violations {

  blockers: number;
  criticals: number;
  majors: number;
  minors: number;
  infos: number;

  constructor(data: any) {
    this.blockers = data.blockers || 0;
    this.criticals = data.criticals || 0;
    this.majors = data.majors || 0;
    this.minors = data.minors || 0;
    this.infos = data.infos || 0;
  }

  hasAny(): boolean {
    return this.blockers !== 0 || this.criticals !== 0 || this.majors !== 0 || this.minors !== 0 || this.infos !== 0;
  }

  hasRelevant(): boolean {
    return this.relevant > 0;
  }

  get all(): number {
    return this.blockers + this.criticals + this.majors + this.minors + this.infos;
  }

  get relevant(): number {
    return this.blockers + this.criticals;
  }

  get nonRelevant(): number {
    return this.majors + this.minors + this.infos;
  }

}
