import {Component, Input} from '@angular/core';
import {GroupsConfigService} from '../../../../../settings/src/lib/service/groups-config.service';
import {combineLatest, ReplaySubject} from 'rxjs';
import {GroupFilter} from '../../../../../group-overview/src/lib/group-filter';
import {map, switchMap} from 'rxjs/operators';
import {DEFAULT_DATE_RANGE} from '../../../../time-select/src/lib/time-select/time-select.component';

@Component({
  selector: 'sqc-crumbs',
  template: `
    <ng-container *ngIf="vm$ | async as vm">
      <div class="crumbs">
        <div class="crumb" *ngFor="let crumb of vm.crumbs">
          <div class="name">{{crumb.name}}</div>
          <div class="span">></div>
        </div>
      </div>
    </ng-container>
  `,
  styleUrls: ['./crumbs.component.scss']
})
export class CrumbsComponent {
  filterSubject: ReplaySubject<GroupFilter> = new ReplaySubject<GroupFilter>();
  filter: GroupFilter = {uuid: '', range: DEFAULT_DATE_RANGE};
  vm$ = this.filterSubject.asObservable().pipe(
    switchMap(filter =>
      combineLatest([
        this.groupService.crumbs(filter.uuid),
      ]).pipe(
        map(([
               groups
             ]) => ({
          crumbs: groups
        }))
      )
    )
  )

  constructor(private groupService: GroupsConfigService) {
  }

  @Input()
  set uuid(id: string) {
    this.filter.uuid = id;
    this.filterSubject.next(this.filter);
  }

}
