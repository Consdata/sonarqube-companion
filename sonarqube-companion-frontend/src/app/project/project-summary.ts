import {HealthStatus} from '../health/health-status';
import {Violations} from '../violations/violations';

export class ProjectSummary {

  name: string;
  key: string;
  serverId: string;
  health: HealthStatus;
  violations: Violations;

  constructor(data) {
    this.name = data.name;
    this.key = data.key;
    this.serverId = data.serverId;
    this.health = HealthStatus[data.healthStatus] as HealthStatus;
    this.violations = new Violations(data.violations || {});
  }

}
