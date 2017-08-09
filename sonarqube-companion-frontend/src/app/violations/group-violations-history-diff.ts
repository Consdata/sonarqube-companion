import {Violations} from './violations';

export class GroupViolationsHistoryDiff {

  groupDiff: Violations;
  addedViolations: Violations;
  removedViolations: Violations;
  projects: {[key: string]: Violations} = {};

  constructor(data) {
    this.groupDiff = new Violations(data.groupDiff);
    this.addedViolations = new Violations(data.addedViolations);
    this.removedViolations = new Violations(data.removedViolations);
    data.projectDiffs.forEach(projectDiff => {
      this.projects[projectDiff.projectKey] = new Violations(projectDiff.violationsDiff);
    });
  }

}
