export interface GroupViolationsHistory {
  violationHistoryEntries: GroupViolationHistoryEntry[];
}

export interface GroupViolationHistoryEntry {
  dateString: string;
  violations: Violations;
}

export interface Violations {
  blockers: number;
  criticals: number;
  majors: number;
  minors: number;
  infos: number;
}

