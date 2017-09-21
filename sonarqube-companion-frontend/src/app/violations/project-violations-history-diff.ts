import {Violations} from './violations';

export class ProjectViolationsHistoryDiff {

  violations: Violations;
  fromDate;
  toDate;

  constructor(data) {
    this.violations = new Violations(data.violationsDiff);
    this.fromDate = data.fromDateString;
    this.toDate = data.toDateString;
  }

}
