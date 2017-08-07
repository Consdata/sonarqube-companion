import {Component} from '@angular/core';

import {GroupDetails} from './group-details';
import {GroupService} from './group-service';
import {ActivatedRoute} from '@angular/router';
import {ViolationsHistoryService} from '../violations/violations-history-service';
import {GroupViolationsHistoryDiff} from '../violations/group-violations-history-diff';

@Component({
  selector: 'sq-group',
  template: `
    <sq-spinner *ngIf="!group"></sq-spinner>
    <div *ngIf="group" class="group-sections">
      <h1>{{group.name}}</h1>
      <hr/>
      <div class="overview-cards">
        <sq-group-overview-cards
          [group]="group"
          [violations]="group.violations"
          [violationsHistoryDiff]="violationsHistoryDiff?.groupDiff">
        </sq-group-overview-cards>
      </div>
      <div>
        <h2>Violations</h2>
        <sq-group-violations-history
          [uuid]="group.uuid"
          (zoomed)="onChartZoomed($event)">
        </sq-group-violations-history>
      </div>
      <div>
        <h2>Groups</h2>
        <div *ngFor="let subGroup of group.groups">
          <a routerLink="/groups/{{subGroup.uuid}}">{{subGroup.name}}</a>
        </div>
      </div>
      <div>
        <h2>Projects</h2>
        <sq-group-projects
          [projects]="group.projects"
          [violationsHistoryDiff]="violationsHistoryDiff">
        </sq-group-projects>
      </div>
    </div>
  `
})
export class GroupComponent {

  group: GroupDetails;
  violationsHistoryDiff: GroupViolationsHistoryDiff;

  constructor(private route: ActivatedRoute,
              private groupService: GroupService,
              private violationsHistoryService: ViolationsHistoryService) {
    route
      .paramMap
      .switchMap(params => groupService.getGroup(params.get('uuid')))
      .subscribe(group => this.group = group);
  }

  onChartZoomed(zoomedEvent: any) {
    this.violationsHistoryDiff = undefined;
    this.violationsHistoryService
      .getHistoryDiff(this.group.uuid, zoomedEvent.fromDate, zoomedEvent.toDate)
      .subscribe(result => this.violationsHistoryDiff = result);
  }

}
