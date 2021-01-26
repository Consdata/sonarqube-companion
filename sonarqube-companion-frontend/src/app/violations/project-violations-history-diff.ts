import {Violations} from './violations';

export class ProjectViolationsHistoryDiff {

  violations: Violations;
  addedViolations: Violations;
  removedViolations: Violations;
  fromDate;
  toDate;

  constructor(data) {
    this.violations = new Violations(data.violationsDiff);
    this.addedViolations = new Violations(data.addedViolations);
    this.removedViolations = new Violations(data.removedViolations);
    this.fromDate = data.fromDateString;
    this.toDate = data.toDateString;
  }

}
