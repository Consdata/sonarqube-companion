import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {BehaviorSubject, Observable} from 'rxjs';
import {filter, map, switchMap} from 'rxjs/operators';
import {GroupViolationsHistoryDiff} from '../violations/group-violations-history-diff';
import {ViolationsHistoryService} from '../violations/violations-history-service';

import {GroupDetails} from './group-details';
import {GroupService} from './group-service';

@Component({
  selector: 'sq-group',
  template: `
    <ng-template #spinner>
      <sq-spinner></sq-spinner>
    </ng-template>
    <div *ngIf="group$ | async as group; else spinner" class="group-sections">
      <h1>{{group.name}}</h1>
      <hr/>
      <div class="overview-cards" *ngIf="violationsHistoryDiff$ | async as violationsHistoryDiff; else spinner">
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
          <a [routerLink] [queryParams]="{'history.filter.violations': 'all'}" queryParamsHandling="merge"
             [class.active]="'all' === historyFilter">all</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'relevant'}" queryParamsHandling="merge"
             [class.active]="'relevant' === historyFilter">relevant</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'nonrelevant'}" queryParamsHandling="merge"
             [class.active]="'nonrelevant' === historyFilter">non relevant</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'blockers'}" queryParamsHandling="merge"
             [class.active]="'blockers' === historyFilter">blockers</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'criticals'}" queryParamsHandling="merge"
             [class.active]="'criticals' === historyFilter">criticals</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'majors'}" queryParamsHandling="merge"
             [class.active]="'majors' === historyFilter">majors</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'minors'}" queryParamsHandling="merge"
             [class.active]="'minors' === historyFilter">minors</a>
          |
          <a [routerLink] [queryParams]="{'history.filter.violations': 'infos'}" queryParamsHandling="merge"
             [class.active]="'infos' === historyFilter">infos</a>
        </div>
        <hr/>
        <sq-violations-history
          [violationsFilter]="historyFilter"
          [violationsHistoryProvider]="violationsHistoryProvider">
        </sq-violations-history>
      </div>
      <div>
        <h2>Groups</h2>
        <div *ngFor="let subGroup of group.groups">
          <a routerLink="/groups/{{subGroup.uuid}}">{{subGroup.name}}</a>
        </div>
      </div>
      <div>
        <h2>Members</h2>
        <sq-group-members [group]="group" [zoom]="zoom"></sq-group-members>
      </div>
      <div>
        <h2>Projects</h2>
        <div class="group-projects-filter">
          <a class="project-filter-item" [class.active]="'changed' === projectsFilter" [routerLink]
             [queryParams]="{'projects.filter.severity': 'changed'}" queryParamsHandling="merge">changed</a>
          | <a class="project-filter-item" [class.active]="'regression' === projectsFilter" [routerLink]
               [queryParams]="{'projects.filter.severity': 'regression'}" queryParamsHandling="merge">regression</a>
          | <a class="project-filter-item" [class.active]="'improvement' === projectsFilter" [routerLink]
               [queryParams]="{'projects.filter.severity': 'improvement'}" queryParamsHandling="merge">improvement</a>
          | <a class="project-filter-item" [class.active]="'all' === projectsFilter" [routerLink]
               [queryParams]="{'projects.filter.severity': 'all'}" queryParamsHandling="merge">all</a>
        </div>
        <hr/>
        <sq-group-projects *ngIf="violationsHistoryDiff$ | async as violationsHistoryDiff; else spinner"
          [projects]="group.projects"
          [filter]="projectsFilter"
          [authors]="memberAliases$ | async"
          [violationsHistoryDiff]="violationsHistoryDiff"
          [uuid]="group.uuid">
        </sq-group-projects>
      </div>
    </div>
  `
})
export class GroupComponent implements OnInit {

  group$: BehaviorSubject<GroupDetails> = new BehaviorSubject<GroupDetails>(undefined);
  violationsHistoryDiff$: Observable<GroupViolationsHistoryDiff>;
  memberAliases$: Observable<String[]>;
  projectsFilter: string = 'changed';
  historyFilter: string = 'relevant';
  zoom: { fromDate: string, toDate: string };
  readonly daysLimit: number = 90;

  constructor(private route: ActivatedRoute,
              private groupService: GroupService,
              private violationsHistoryService: ViolationsHistoryService) {
    route
      .paramMap
      .pipe(
        switchMap(params => groupService.getGroup(params.get('uuid')))
      )
      .subscribe(group => this.group$.next(group));
    route
      .queryParamMap
      .pipe(
        filter(params => params.has('projects.filter.severity')),
        map(params => params.get('projects.filter.severity'))
      )
      .subscribe(filterSeverity => this.projectsFilter = filterSeverity);
    route
      .queryParamMap
      .pipe(
        filter(params => params.has('history.filter.violations')),
        map(params => params.get('history.filter.violations'))
      )
      .subscribe(historyFilter => this.historyFilter = historyFilter);
  }

  violationsHistoryProvider = () => this.violationsHistoryService.getGroupHistory(this.daysLimit, this.group$.value.uuid);

  ngOnInit(): void {
    const to = this.dateMinusDays(1);
    const from = this.dateMinusDays(this.daysLimit);
    this.violationsHistoryDiff$ = this.group$.pipe(
      switchMap(group => this.violationsHistoryService.getGroupHistoryDiff(group.uuid, from, to))
    );
    this.memberAliases$ = this.group$.pipe(
      switchMap(group => this.groupService.getAliases(group.uuid))
    );
  }

  private dateMinusDays(days: number): string {
    const date = new Date();
    date.setDate(date.getDate() - days);
    return date.toISOString().substring(0, 10);
  }

}
