export interface MemberViolationsHistoryDiff {
  uuid: string;
  name: string;
  violationsDiff: Violations;
  addedViolations: Violations;
  removedViolation: Violations;
  fromDate: Date;
  toDate: Date;
}


export interface Violations {
  blockers: number;
  criticals: number;
  majors: number;
  minors: number;
  infos: number;
}

