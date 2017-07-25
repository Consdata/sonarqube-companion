import {HealthStatus} from '../health/health-status';

export class GroupOverview {

  uuid: string;
  name: string;
  description: string;
  healthStatus: string;
  groups: GroupOverview[];

  constructor(data: any) {
    this.uuid = data.uuid;
    this.name = data.name;
    this.description = data.description;
    this.healthStatus = HealthStatus[data.healthStatus];
    this.groups = data.groups ? data.groups.map(groupData => new GroupOverview(groupData)) : [];
  }

  get healthStatusString(): string {
    return (HealthStatus[this.healthStatus] as string).toLowerCase();
  }

}
