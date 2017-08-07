import {Violations} from './violations';

export class ViolationsHistoryEntry {

  date: any;
  violations: Violations;

  constructor(data) {
    this.date = new Date(data.dateString);
    this.violations = new Violations(data.violations || {});
  }

  get blockers() {
    return this.violations.blockers;
  }

  get criticals() {
    return this.violations.criticals;
  }

  get majors() {
    return this.violations.majors;
  }

  get minors() {
    return this.violations.minors;
  }

  get infos() {
    return this.violations.infos;
  }

  get relevant() {
    return this.violations.relevant;
  }

  get nonrelevant() {
    return this.nonRelevant;
  }

  get nonRelevant() {
    return this.violations.nonRelevant;
  }

  get all() {
    return this.violations.all;
  }

}
