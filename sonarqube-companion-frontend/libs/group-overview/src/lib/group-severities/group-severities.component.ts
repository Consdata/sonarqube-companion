import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {combineLatest, ReplaySubject} from 'rxjs';
import {GroupFilter} from '../group-filter';
import {
  DateRange,
  DEFAULT_DATE_RANGE
} from '../../../../ui-components/time-select/src/lib/time-select/time-select.component';
import {map, switchMap, tap} from 'rxjs/operators';
import {GroupService} from '@sonarqube-companion-frontend/group';

@Component({
  selector: 'sqc-group-severities',
  template: `
    <ng-container *ngIf="vm$ | async as vm">
      <ng-container *ngIf="!loading">
        <sqc-value-badge [label]="'blockers'"
                         [suffix]="formatSuffix(vm.violationsDiff.blockers)">{{vm.violations.blockers}}</sqc-value-badge>
        <sqc-value-badge [label]="'criticals'"
                         [suffix]="formatSuffix(vm.violationsDiff.criticals)">{{vm.violations.criticals}}</sqc-value-badge>
        <sqc-value-badge [priority]="''" [label]="'majors'"
                         [suffix]="formatSuffix(vm.violationsDiff.majors)">{{vm.violations.majors}}</sqc-value-badge>
        <sqc-value-badge [priority]="''" [label]="'minors'"
                         [suffix]="formatSuffix(vm.violationsDiff.minors)">{{vm.violations.minors}}</sqc-value-badge>
        <sqc-value-badge [priority]="''" [label]="'infos'"
                         [suffix]="formatSuffix(vm.violationsDiff.infos)">{{vm.violations.infos}}</sqc-value-badge>
      </ng-container>
    </ng-container>
    <ng-container *ngIf="loading">
      <div class="spinner">
        <mat-spinner [diameter]="40"></mat-spinner>
      </div>
    </ng-container>
  `,
  styleUrls: ['./group-severities.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupSeveritiesComponent {
  loading: boolean = false;
  filterSubject: ReplaySubject<GroupFilter> = new ReplaySubject<GroupFilter>();
  filter: GroupFilter = {uuid: '', range: DEFAULT_DATE_RANGE};
  vm$ = this.filterSubject.asObservable().pipe(
    tap(_ => this.loading = true),
    switchMap(filter =>
      combineLatest([
        this.groupService.violations(filter.uuid, filter.range),
        this.groupService.violationsDiff(filter.uuid, filter.range)
      ]).pipe(
        map(([
               violations,
               violationsDiff
             ]) => ({
          violations: violations,
          violationsDiff: violationsDiff
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

  formatSuffix(value: number): string {
    if (value) {
      return value > 0 ? `+${value}` : `${value}`;
    } else {
      return '0';
    }
  }

}
