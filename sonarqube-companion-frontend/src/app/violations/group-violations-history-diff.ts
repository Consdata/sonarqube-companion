import {Violations} from './violations';

export class GroupViolationsHistoryDiff {

  groupDiff: Violations;
  projects: {[key: string]: Violations} = {};

  constructor(data) {
    this.groupDiff = new Violations(data.groupDiff);
    data.projectDiffs.forEach(projectDiff => {
      this.projects[projectDiff.projectKey] = new Violations(projectDiff.violationsDiff);
    });
  }

}
