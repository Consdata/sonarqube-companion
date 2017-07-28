import {Violations} from './violations';

export class ProjectViolationsHistoryEntry {

  date: any;
  violations: Violations;

  constructor(data) {
    this.date = new Date(data.dateString);
    this.violations = new Violations(data.violations || {});
  }

}
