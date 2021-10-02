import {Component, Input} from '@angular/core';
import {ProjectViolationsSummary} from '@sonarqube-companion-frontend/project';
import {ViolationsTableItem} from '../../../../ui-components/table/src/lib/table/violations-table.component';
import {GroupService} from '@sonarqube-companion-frontend/group';
import {combineLatest, ReplaySubject} from 'rxjs';
import {GroupFilter} from '../group-filter';
import {
  DateRange,
  DEFAULT_DATE_RANGE
} from '../../../../ui-components/time-select/src/lib/time-select/time-select.component';
import {map, switchMap} from 'rxjs/operators';

@Component({
  selector: 'sqc-group-projects-summary',
  template: `
    <sqc-violations-table *ngIf="vm$ | async as vm" [nameAlias]="'Project'"
                          [data]="asViolationsTableItems(vm.summary)"></sqc-violations-table>
  `,
  styleUrls: ['./group-projects-summary.component.scss']
})
export class GroupProjectsSummaryComponent {
  filterSubject: ReplaySubject<GroupFilter> = new ReplaySubject<GroupFilter>();
  filter: GroupFilter = {uuid: '', range: DEFAULT_DATE_RANGE};
  vm$ = this.filterSubject.asObservable().pipe(
    switchMap(filter =>
      combineLatest([
        this.groupService.groupProjectsViolationsSummary(filter.uuid, filter.range),
      ]).pipe(
        map(([
               summary
             ]) => ({
          summary: summary
        }))
      )
    )
  )

  constructor(private groupService: GroupService) {
  }

  @Input()
  set uuid(data: string) {
    if (data) {
      this.filter.uuid = data;
      this.filterSubject.next(this.filter);
    }
  }

  @Input()
  set range(data: DateRange) {
    if (data && this.filter.uuid !== '') {
      this.filter.range = data;
      this.filterSubject.next(this.filter);
    }
  }

  asViolationsTableItems(projectsHistory: ProjectViolationsSummary[]): ViolationsTableItem[] {
    return projectsHistory.map(entry => ({
      uuid: entry.projectKey,
      name: entry.projectKey,
      blockers: {count: entry.violationsDiff.blockers, diff: 0},
      criticals: {count: entry.violationsDiff.criticals, diff: 0},
      majors: {count: entry.violationsDiff.majors, diff: 0},
      minors: {count: entry.violationsDiff.minors, diff: 0},
      infos: {count: entry.violationsDiff.infos, diff: 0},
    }));
  }

}
