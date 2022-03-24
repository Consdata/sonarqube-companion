export interface GroupDetails {
  uuid: string;
  parentUuid: string;
  name: string;
  description: string;
  projects: number;
  members: number;
  events: number;
  groups: GroupDetails[];
}
