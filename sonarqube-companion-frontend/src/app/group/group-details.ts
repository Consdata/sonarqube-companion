import {HealthStatus} from '../health/health-status';
import {Violations} from '../violations/violations';
import {GroupSummary} from './group-summary';
import {ProjectSummary} from '../project/project-summary';

export class GroupDetails {

  uuid: string;
  name: string;
  healthStatus: HealthStatus;
  violations: Violations;
  groups: GroupSummary[];
  projects: ProjectSummary[];
  issues: any[];

  constructor(data: any) {
    this.uuid = data.uuid;
    this.name = data.name;
    this.healthStatus = HealthStatus[data.healthStatus] as HealthStatus;
    this.violations = new Violations(data.violations || {});
    this.groups = data.groups ? data.groups.map(groupData => new GroupSummary(groupData)) : [];
    this.projects = data.projects ? data.projects.map(projectData => new ProjectSummary(projectData)) : [];
    this.issues = [];

    this.projects = this.projects.sort((a, b) => {
      if (a.violations.blockers === b.violations.blockers) {
        return b.violations.criticals - a.violations.criticals;
      } else {
        return b.violations.blockers - a.violations.blockers;
      }
    });
  }

  get healthStatusString(): string {
    return HealthStatus[this.healthStatus].toLowerCase();
  }

}
