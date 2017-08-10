import {HealthStatus} from '../health/health-status';
import {Violations} from '../violations/violations';
import {GroupSummary} from './group-summary';
import {ProjectSummary} from '../project/project-summary';
import {SortByViolationsDesc} from '../violations/sort-by-violations';
import {GroupEvent} from './group-event';

export class GroupDetails {

  uuid: string;
  name: string;
  healthStatus: HealthStatus;
  violations: Violations;
  groups: GroupSummary[];
  projects: ProjectSummary[];
  events: GroupEvent[];
  issues: any[];

  constructor(data: any) {
    this.uuid = data.uuid;
    this.name = data.name;
    this.healthStatus = HealthStatus[data.healthStatus] as HealthStatus;
    this.violations = new Violations(data.violations || {});
    this.groups = data.groups ? data.groups.map(groupData => new GroupSummary(groupData)) : [];
    this.projects = data.projects ? data.projects.map(projectData => new ProjectSummary(projectData)) : [];
    this.events = data.events ? data.events.map(eventData => new GroupEvent(eventData)) : [];
    this.issues = [];

    this.projects = this.projects.sort(SortByViolationsDesc);
  }

  get healthy(): boolean {
    return HealthStatus.HEALTHY === this.healthStatus;
  }

  get healthStatusString(): string {
    return HealthStatus[this.healthStatus].toLowerCase();
  }

}
