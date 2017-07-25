import {HealthStatus} from '../health/health-status';
import {Violations} from '../violations/violations';

export class GroupDetails {

  uuid: string;
  name: string;
  healthStatus: string;
  violations: Violations;
  groups: any[];
  projects: any[];
  issues: any[];

  constructor(data: any) {
    this.uuid = data.uuid;
    this.name = data.name;
    this.healthStatus = HealthStatus[data.healthStatus];
    this.violations = new Violations(data.violations);
    this.groups = [];
    this.projects = [];
    this.issues = [];
  }

  get healthStatusString(): string {
    return (HealthStatus[this.healthStatus] as string).toLowerCase();
  }

}
