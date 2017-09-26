import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ViolationsHistoryService} from '../violations/violations-history-service';
import {ProjectSummary} from './project-summary';
import {ProjectService} from './project-service';
import {ProjectViolationsHistoryDiff} from '../violations/project-violations-history-diff';
import {GroupService} from '../group/group-service';
import {GroupDetails} from 'app/group/group-details';

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
          [violationsHistoryProvider]="violationsHistoryProvider"
          (zoomed)="onChartZoomed($event)">
        </sq-violations-history>
      </div>
    </div>
  `
})
export class ProjectComponent {

  project: ProjectSummary;
  group: GroupDetails;
  projectViolationsDiff: ProjectViolationsHistoryDiff;
  violationsHistoryProvider = (daysLimit: number) => this.violationsHistoryService.getProjectHistory(daysLimit, this.group.uuid, this.project.key);

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

  onChartZoomed(zoomedEvent: any) {
    this.projectViolationsDiff = undefined;
    this.violationsHistoryService
      .getProjectHistoryDiff(this.group.uuid, this.project.key, zoomedEvent.fromDate, zoomedEvent.toDate)
      .subscribe(result => this.projectViolationsDiff = result);
  }
}
