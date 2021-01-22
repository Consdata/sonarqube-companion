import {Component, OnInit} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {GroupDetails} from '../group/group-details';
import {GroupViolationsHistoryDiff} from '../violations/group-violations-history-diff';
import {ActivatedRoute} from '@angular/router';
import {GroupService} from '../group/group-service';
import {ViolationsHistoryService} from '../violations/violations-history-service';
import {filter, map, switchMap} from 'rxjs/operators';

@Component({
  selector: 'sq-projects',
  template: `
    <ng-template #spinner>
      <sq-spinner></sq-spinner>
    </ng-template>
    <div *ngIf="group$ | async as group; else spinner" class="group-sections">
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
        <sq-projects-summary
          [projects]="group.projects"
          [filter]="projectsFilter"
          [violationsHistoryDiff]="violationsHistoryDiff$ | async"
          [uuid]="group.uuid">
        </sq-projects-summary>
      </div>
    </div>
  `
})
export class ProjectsOverviewComponent implements OnInit {

  group$: Observable<GroupDetails>;
  violationsHistoryDiff$: Observable<GroupViolationsHistoryDiff>;
  projectsFilter: string = 'changed';
  historyFilter: string = 'relevant';
  readonly daysLimit: number = 90;

  constructor(private route: ActivatedRoute,
              private groupService: GroupService,
              private violationsHistoryService: ViolationsHistoryService) {
    this.group$ = groupService.getGroup(); // TOOD ignore members
  }


  ngOnInit(): void {
    const to = this.dateMinusDays(1);
    const from = this.dateMinusDays(this.daysLimit);
    this.violationsHistoryDiff$ = this.group$.pipe(
      switchMap(group => this.violationsHistoryService.getProjectsHistory(from, to))
    );
  }

  private dateMinusDays(days: number): string {
    const date = new Date();
    date.setDate(date.getDate() - days);
    return date.toISOString().substring(0, 10);
  }

}
