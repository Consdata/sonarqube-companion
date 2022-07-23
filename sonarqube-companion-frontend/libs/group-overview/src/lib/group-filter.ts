import { DateRange } from '@sonarqube-companion-frontend/ui-components/time-select';

export interface GroupFilter {
  uuid: string;
  range: DateRange;
}
