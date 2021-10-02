import {Component, Input} from '@angular/core';
import {combineLatest, ReplaySubject} from 'rxjs';
import {GroupFilter} from '../group-filter';
import {
  DateRange,
  DEFAULT_DATE_RANGE
} from '../../../../ui-components/time-select/src/lib/time-select/time-select.component';
import {map, switchMap} from 'rxjs/operators';
import {GroupService} from '@sonarqube-companion-frontend/group';
import {ViolationsTableItem} from '../../../../ui-components/table/src/lib/table/violations-table.component';
import {MemberViolationsSummary} from '@sonarqube-companion-frontend/member';

@Component({
  selector: 'sqc-group-members-summary',
  template: `
    <sqc-violations-table *ngIf="vm$ | async as vm" [nameAlias]="'Member'"
                          [data]="membersDiffAsViolationsTableItems(vm.summary)"></sqc-violations-table>
  `,
  styleUrls: ['./group-members-summary.component.scss']
})
export class GroupMembersSummaryComponent {
  filterSubject: ReplaySubject<GroupFilter> = new ReplaySubject<GroupFilter>();
  filter: GroupFilter = {uuid: '', range: DEFAULT_DATE_RANGE};
  vm$ = this.filterSubject.asObservable().pipe(
    switchMap(filter =>
      combineLatest([
        this.groupService.groupMembersViolationsSummary(filter.uuid, filter.range),
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

  membersDiffAsViolationsTableItems(projectsHistory: MemberViolationsSummary[]): ViolationsTableItem[] {
    return projectsHistory.map(entry => ({
      uuid: entry.uuid,
      name: entry.name,
      blockers: {count: entry.violationsDiff.blockers, diff: 0},
      criticals: {count: entry.violationsDiff.criticals, diff: 0},
      majors: {count: entry.violationsDiff.majors, diff: 0},
      minors: {count: entry.violationsDiff.minors, diff: 0},
      infos: {count: entry.violationsDiff.infos, diff: 0},
    }));
  }
}
