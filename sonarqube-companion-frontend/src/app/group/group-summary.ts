import {HealthStatus} from '../health/health-status';
import {Violations} from '../violations/violations';

export class GroupSummary {

  uuid: string;
  name: string;
  healthStatus: HealthStatus;
  violations: Violations;

  constructor(data) {
    this.uuid = data.uuid;
    this.name = data.name;
    this.healthStatus = HealthStatus[data.healthStatus] as HealthStatus;
    this.violations = new Violations(data.violations || {});
  }

}
