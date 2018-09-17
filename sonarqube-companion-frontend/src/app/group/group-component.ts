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
        <div class="violations-history-chart-issues-menu">
          <a [routerLink] [queryParams]="{'history.filter.violations': 'all'}" queryParamsHandling="merge" [class.active]="'all' === historyFilter">all</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'relevant'}" queryParamsHandling="merge" [class.active]="'relevant' === historyFilter">relevant</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'nonrelevant'}" queryParamsHandling="merge" [class.active]="'nonrelevant' === historyFilter">non relevant</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'blockers'}" queryParamsHandling="merge" [class.active]="'blockers' === historyFilter">blockers</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'criticals'}" queryParamsHandling="merge" [class.active]="'criticals' === historyFilter">criticals</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'majors'}" queryParamsHandling="merge" [class.active]="'majors' === historyFilter">majors</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'minors'}" queryParamsHandling="merge" [class.active]="'minors' === historyFilter">minors</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'infos'}" queryParamsHandling="merge" [class.active]="'infos' === historyFilter">infos</a>
        </div>
        <hr/>
        <sq-violations-history
          [group]="group"
          [violationsFilter]="historyFilter"
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
          <a class="project-filter-item" [class.active]="'changed' === projectsFilter" [routerLink] [queryParams]="{'projects.filter.severity': 'changed'}" queryParamsHandling="merge">changed</a>
          | <a class="project-filter-item" [class.active]="'regression' === projectsFilter" [routerLink] [queryParams]="{'projects.filter.severity': 'regression'}" queryParamsHandling="merge">regression</a>
          | <a class="project-filter-item" [class.active]="'improvement' === projectsFilter" [routerLink] [queryParams]="{'projects.filter.severity': 'improvement'}" queryParamsHandling="merge">improvement</a>
          | <a class="project-filter-item" [class.active]="'all' === projectsFilter" [routerLink] [queryParams]="{'projects.filter.severity': 'all'}" queryParamsHandling="merge">all</a>
        </div>
        <hr/>
        <sq-group-projects
          [projects]="group.projects"
          [filter]="projectsFilter"
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
  projectsFilter = 'changed';
  historyFilter = 'relevant';
  violationsHistoryProvider = (daysLimit: number) => this.violationsHistoryService.getGroupHistory(daysLimit, this.group.uuid);

  constructor(private route: ActivatedRoute,
              private groupService: GroupService,
              private violationsHistoryService: ViolationsHistoryService) {
    route
      .paramMap
      .switchMap(params => groupService.getGroup(params.get('uuid')))
      .subscribe(group => this.group = group);
    route
      .queryParamMap
      .filter(params => params.has("projects.filter.severity"))
      .map(params => params.get("projects.filter.severity"))
      .subscribe(filterSeverity => this.projectsFilter = filterSeverity);
    route
      .queryParamMap
      .filter(params => params.has("history.filter.violations"))
      .map(params => params.get("history.filter.violations"))
      .subscribe(historyFilter => this.historyFilter = historyFilter);
  }

  onChartZoomed(zoomedEvent: any) {
    this.violationsHistoryDiff = undefined;
    this.violationsHistoryService
      .getGroupHistoryDiff(this.group.uuid, zoomedEvent.fromDate, zoomedEvent.toDate)
      .subscribe(result => this.violationsHistoryDiff = result);
  }

}
