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
          [violationsDiff]="violationsHistoryDiff?.groupDiff"
          [addedViolations]="violationsHistoryDiff?.addedViolations"
          [removedViolations]="violationsHistoryDiff?.removedViolations">
        </sq-group-overview-cards>
      </div>
      <div>
        <h2>Violations</h2>
        <sq-violations-history
          [group]="group"
          [violationsHistoryProvider]="violationsHistoryProvider"
          (zoomed)="onChartZoomed($event)">
        </sq-violations-history>
      </div>
      <div>
        <h2>Groups</h2>
        <div *ngFor="let subGroup of group.groups">
          <a routerLink="/groups/{{subGroup.uuid}}">{{subGroup.name}}</a>
        </div>
      </div>
      <div>
        <h2>Projects</h2>
        <div class="group-projects-filter">
          <span class="project-filter-item" [class.active]="'changed' === filter"
                (click)="filter = 'changed'">changed</span>
          | <span class="project-filter-item" [class.active]="'regression' === filter" (click)="filter = 'regression'">regression</span>
          | <span class="project-filter-item" [class.active]="'improvement' === filter"
                  (click)="filter = 'improvement'">improvement</span>
          | <span class="project-filter-item" [class.active]="'all' === filter" (click)="filter = 'all'">all</span>
        </div>
        <hr/>
        <sq-group-projects
          [projects]="group.projects"
          [filter]="filter"
          [violationsHistoryDiff]="violationsHistoryDiff"
          [uuid]="group.uuid">
        </sq-group-projects>
      </div>
    </div>
  `
})
export class GroupComponent {

  group: GroupDetails;
  violationsHistoryDiff: GroupViolationsHistoryDiff;
  filter = 'changed';
  violationsHistoryProvider = (daysLimit: number) => this.violationsHistoryService.getGroupHistory(daysLimit, this.group.uuid);

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
      .getGroupHistoryDiff(this.group.uuid, zoomedEvent.fromDate, zoomedEvent.toDate)
      .subscribe(result => this.violationsHistoryDiff = result);
  }

}
