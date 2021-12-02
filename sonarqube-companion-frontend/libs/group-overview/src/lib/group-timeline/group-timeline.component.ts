import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {combineLatest, ReplaySubject} from 'rxjs';
import {GroupService} from '@sonarqube-companion-frontend/group';
import {GroupViolationsHistory} from '../../../../group/src/lib/group-violations-history';
import {Event, EventService} from '@sonarqube-companion-frontend/event';
import {TimelineSeries} from '../../../../ui-components/timeline/src/lib/timeline';
import {map, switchMap} from 'rxjs/operators';
import {DateRange} from '@sonarqube-companion-frontend/ui-components/time-select';
import {DEFAULT_DATE_RANGE} from '../../../../ui-components/time-select/src/lib/time-select/time-select.component';
import {GroupFilter} from '../group-filter';

@Component({
  selector: 'sqc-group-timeline',
  template: `
    <ng-container *ngIf="vm$ | async as vm ; else spinner">
      <sqc-timeline [series]="asSeries(vm.violationsHistory, vm.events )"
                    *ngIf="!noData(vm.violationsHistory); else noDataMsg"></sqc-timeline>
      <ng-template #noDataMsg>
        <div class="noData">No data :(</div>
      </ng-template>
    </ng-container>
    <ng-template #spinner>
      <div class="spinner">
        <mat-spinner></mat-spinner>
      </div>
    </ng-template>
  `,
  styleUrls: ['./group-timeline.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupTimelineComponent {
  filterSubject: ReplaySubject<GroupFilter> = new ReplaySubject<GroupFilter>();
  filter: GroupFilter = {uuid: '', range: DEFAULT_DATE_RANGE};
  vm$ = this.filterSubject.asObservable().pipe(
    switchMap(data =>
      combineLatest([
        this.groupService.groupViolationsHistoryRange(data.uuid, data.range),
        this.eventService.getByGroup(data.uuid, 30)
      ]).pipe(
        map(([
               violationsHistory,
               events
             ]) => ({
          violationsHistory: violationsHistory,
          events: events
        }))
      )
    )
  )

  constructor(private groupService: GroupService, private eventService: EventService) {
  }

  @Input()
  set uuid(data: string) {
    if (data) {
      this.filter.uuid = data;
      this.filterSubject.next(this.filter);
    }
  }

  // TODO spinnery
  @Input()
  set range(data: DateRange) {
    if (data && this.filter.uuid !== '') {
      this.filter.range = data;
      this.filterSubject.next(this.filter);
    }
  }

  noData(history: GroupViolationsHistory): boolean {
    return !history || !history.violationHistoryEntries || history.violationHistoryEntries.length == 0;
  }

  asSeries(violationsHistory: GroupViolationsHistory | null, events: Event[] | null): TimelineSeries {
    if (violationsHistory && events) {
      return {
        events: events.map(item => ({name: item.name, fromDate: new Date(item.dateString)})),
        data: violationsHistory.violationHistoryEntries.map(entry => ({
          date: new Date(entry.dateString),
          value: entry.violations.blockers + entry.violations.criticals + entry.violations.majors + entry.violations.minors + entry.violations.infos
        }))
      }
    }
    return {};
  }
}
