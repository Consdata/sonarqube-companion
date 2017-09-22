import {Violations} from './violations';
import {ProjectViolationsHistoryDiff} from './project-violations-history-diff';

export class GroupViolationsHistoryDiff {

  groupDiff: Violations;
  addedViolations: Violations;
  removedViolations: Violations;
  projects: {[key: string]: ProjectViolationsHistoryDiff} = {};

  constructor(data) {
    this.groupDiff = new Violations(data.groupDiff);
    this.addedViolations = new Violations(data.addedViolations);
    this.removedViolations = new Violations(data.removedViolations);
    data.projectDiffs.forEach(projectDiff => {
      this.projects[projectDiff.projectKey] = new ProjectViolationsHistoryDiff(projectDiff);
    });
  }

}
