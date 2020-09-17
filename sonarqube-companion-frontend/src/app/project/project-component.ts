import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {GroupDetails} from 'app/group/group-details';
import {GroupService} from '../group/group-service';
import {ProjectViolationsHistoryDiff} from '../violations/project-violations-history-diff';
import {ViolationsHistoryService} from '../violations/violations-history-service';
import {ProjectService} from './project-service';
import {ProjectSummary} from './project-summary';

@Component({
  selector: 'sq-project',
  template: `
    <sq-spinner *ngIf="!loaded"></sq-spinner>
    <div *ngIf="loaded" class="group-sections">
      <h1>{{project.name}} ({{group.name}})</h1>
      <hr/>
      <sq-project-overview-cards
        [project]="project"
        [violations]="project?.violations"
        [violationsDiff]="projectViolationsDiff?.violations"></sq-project-overview-cards>
      <div>
        <h2>Violations</h2>
        <sq-violations-history
          [group]="group"
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
  projectViolationsDiff: ProjectViolationsHistoryDiff;
  readonly daysLimit: number = 90;

  constructor(private route: ActivatedRoute,
              private groupService: GroupService,
              private projectService: ProjectService,
              private violationsHistoryService: ViolationsHistoryService) {
    route
      .paramMap
      .subscribe(params => {
        groupService.getGroup(params.get('uuid')).subscribe(group => this.group = group);
        projectService.getProject(params.get('uuid'), params.get('projectKey')).subscribe(project => this.project = project);
      });
  }

  get loaded(): boolean {
    return !!this.project && !!this.group;
  }

  violationsHistoryProvider = () => this.violationsHistoryService.getProjectHistory(this.daysLimit, this.group.uuid, this.project.key);

}
