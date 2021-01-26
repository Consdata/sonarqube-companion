import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {GroupDetails} from 'app/group/group-details';
import {GroupService} from '../group/group-service';
import {ProjectViolationsHistoryDiff} from '../violations/project-violations-history-diff';
import {ViolationsHistoryService} from '../violations/violations-history-service';
import {ProjectService} from './project-service';
import {ProjectSummary} from './project-summary';
import {Observable} from 'rxjs';

@Component({
  selector: 'sq-project',
  template: `
    <sq-spinner *ngIf="!loaded"></sq-spinner>
    <div *ngIf="loaded" class="group-sections">
      <h1>{{project.name}} ({{group.name}})</h1>
      <hr/>
      <sq-project-overview-cards *ngIf="projectViolationsDiff$ | async as projectViolationsDiff"
                                 [project]="project"
                                 [violations]="projectViolationsDiff?.violations"
                                 [violationsDiff]="projectViolationsDiff?.violations"
                                 [addedViolations]="projectViolationsDiff?.addedViolations"
                                 [removedViolations]="projectViolationsDiff?.removedViolations"
      ></sq-project-overview-cards>
      <div>
        <h2>Violations</h2>
        <sq-violations-history
          [violationsFilter]="'all'"
          [violationsHistoryProvider]="violationsHistoryProvider">
        </sq-violations-history>
      </div>
    </div>
  `
})
export class ProjectComponent {

  project: ProjectSummary;
  group: GroupDetails;
  projectViolationsDiff$: Observable<ProjectViolationsHistoryDiff>;
  readonly daysLimit: number = 90;

  constructor(private route: ActivatedRoute,
              private groupService: GroupService,
              private projectService: ProjectService,
              private violationsHistoryService: ViolationsHistoryService) {
    route
      .paramMap
      .subscribe(params => {
        groupService.getGroup(params.get('uuid')).subscribe(group => {
          this.group = group;
        });
        projectService.getGroupProjectSummary(params.get('uuid'), params.get('projectKey')).subscribe(project => {
          this.project = project;
          const to = this.dateMinusDays(1);
          const from = this.dateMinusDays(this.daysLimit);
          this.projectViolationsDiff$ = this.violationsHistoryService.getGroupProjectHistoryDiff(params.get('uuid'), this.project.key, from, to);
        });
      });
  }

  get loaded(): boolean {
    return !!this.project && !!this.group;
  }

  violationsHistoryProvider = () => this.violationsHistoryService.getGroupProjectHistory(this.daysLimit, this.group.uuid, this.project.key);

  private dateMinusDays(days: number): string {
    const date = new Date();
    date.setDate(date.getDate() - days);
    return date.toISOString().substring(0, 10);
  }
}

