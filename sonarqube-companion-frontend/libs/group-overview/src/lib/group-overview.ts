export interface GroupOverview {
  uuid: string;
  name: string;
  groups: GroupOverview[];
  violations: Violations;
  projectCount: number;
  healthStatus: string;
}

export interface Violations {
  blockers: number;
  criticals: number;
  majors: number;
  minors: number;
  infos: number;
}

