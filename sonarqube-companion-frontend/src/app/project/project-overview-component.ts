import {Component, OnInit} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {GroupDetails} from '../group/group-details';
import {GroupViolationsHistoryDiff} from '../violations/group-violations-history-diff';
import {ActivatedRoute} from '@angular/router';
import {GroupService} from '../group/group-service';
import {ViolationsHistoryService} from '../violations/violations-history-service';
import {filter, map, switchMap} from 'rxjs/operators';
import {ProjectService} from './project-service';
import {ProjectSummary} from './project-summary';

@Component({
  selector: 'sq-project',
  template: `
    <ng-template #spinner>
      <sq-spinner></sq-spinner>
    </ng-template>
    <div *ngIf="group$ | async as group; else spinner" class="group-sections">
      <h1>{{project?.name}}</h1>
      <hr/>
      <div class="overview-cards" *ngIf="violationsHistoryDiff$ | async as violationsHistoryDiff">
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
        <sq-violations-history *ngIf="violationsHistoryProvider"
          [group]="group"
          [violationsFilter]="historyFilter"
          [violationsHistoryProvider]="violationsHistoryProvider">
        </sq-violations-history>
      </div>
    </div>
  `
})
export class ProjectOverviewComponent implements OnInit {

  project: ProjectSummary;
  group$: Observable<GroupDetails>; //TODO refactor
  violationsHistoryDiff$: Observable<GroupViolationsHistoryDiff>;
  projectsFilter: string = 'changed';
  historyFilter: string = 'relevant';
  violationsHistoryProvider;
  readonly daysLimit: number = 90;

  constructor(private route: ActivatedRoute,
              private groupService: GroupService,
              private projectService: ProjectService,
              private violationsHistoryService: ViolationsHistoryService) {
    this.group$ = groupService.getGroup();
    route
      .paramMap
      .subscribe(params => {
        projectService.getProject2(params.get('projectKey')).subscribe(project => {
          this.project = project;
          this.violationsHistoryProvider = () =>  this.violationsHistoryService.getProjectHistoryDiff(this.daysLimit, this.project.key);
          const to = this.dateMinusDays(1);
          const from = this.dateMinusDays(this.daysLimit);
          this.violationsHistoryDiff$ = this.group$.pipe(
            switchMap(group => this.violationsHistoryService.getProjectHistory2(this.project.key, from, to))
          );
        });
      });
  }


  ngOnInit(): void {

  }

  private dateMinusDays(days: number): string {
    const date = new Date();
    date.setDate(date.getDate() - days);
    return date.toISOString().substring(0, 10);
  }

}
