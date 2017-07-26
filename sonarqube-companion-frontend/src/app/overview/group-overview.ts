import {HealthStatus} from '../health/health-status';
import {Violations} from '../violations/violations';

export class GroupOverview {

  uuid: string;
  name: string;
  description: string;
  healthStatus: HealthStatus;
  violations: Violations;
  groups: GroupOverview[];
  projectCount: number;

  constructor(data: any) {
    this.uuid = data.uuid;
    this.name = data.name;
    this.description = data.description;
    this.healthStatus = HealthStatus[data.healthStatus] as HealthStatus;
    this.groups = data.groups ? data.groups.map(groupData => new GroupOverview(groupData)) : [];
    this.violations = new Violations(data.violations || {});
    this.projectCount = data.projectCount || 0;
  }

  get healthStatusString(): string {
    return HealthStatus[this.healthStatus].toLowerCase();
  }

}
