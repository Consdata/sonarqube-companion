export interface GroupConfig {
  uuid: string;
  name: string;
  description: string;
  groups: GroupConfig[];
  projectLinks: ProjectLink[];
}

export interface ProjectLink {
  uuid: string;
  serverId: string;
  type: string;
  project: string;
  exclude: boolean;
}
