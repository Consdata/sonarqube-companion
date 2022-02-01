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
import {map, switchMap, tap} from 'rxjs/operators';

@Component({
  selector: 'sqc-group-projects-summary',
  template: `
    <ng-container *ngIf="vm$ | async as vm">
      <ng-container *ngIf="!loading">
        <sqc-violations-table [nameAlias]="'Project'"
                              [data]="asViolationsTableItems(vm.summary)"></sqc-violations-table>

      </ng-container>
    </ng-container>
    <ng-container *ngIf="loading">
      <div class="spinner">
        <mat-spinner></mat-spinner>
      </div>
    </ng-container>
  `,
  styleUrls: ['./group-projects-summary.component.scss']
})
export class GroupProjectsSummaryComponent {
  filterSubject: ReplaySubject<GroupFilter> = new ReplaySubject<GroupFilter>();
  filter: GroupFilter = {uuid: '', range: DEFAULT_DATE_RANGE};
  loading: boolean = false;
  vm$ = this.filterSubject.asObservable().pipe(
    tap(_ => this.loading = true),
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
    ),
    tap(_ => this.loading = false)
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
