import {ProjectViolationsHistoryEntry} from './project-violations-history-entry';

export class ProjectViolationsHistory {

  history: ProjectViolationsHistoryEntry[];

  constructor(data) {
    this.history = data.violationHistoryEntries.map(entry => new ProjectViolationsHistoryEntry(entry));
  }

}
