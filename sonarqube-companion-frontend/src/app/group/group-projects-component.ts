import {Component, Input} from '@angular/core';
import {ProjectSummary} from '../project/project-summary';
import {GroupViolationsHistoryDiff} from '../violations/group-violations-history-diff';
import {Violations} from '../violations/violations';

@Component({
  selector: 'sq-group-projects',
  template: `
    <table class="projects-table">
      <tr class="project-header">
        <td>Name</td>
        <td>Key</td>
        <td>Blockers</td>
        <td>Criticals</td>
        <td>Other</td>
        <td>SonarQube</td>
      </tr>
      <ng-container *ngFor="let project of projects">
        <tr
          *ngIf="isVisibleViaFilter(project)"
          [attr.health-status]="project.healthStatusString"
          class="project-row">
          <td>{{project.name}}</td>
          <td>{{project.key}}</td>
          <td>
            <sq-project-violations
              [violations]="project.violations"
              [violationsDiff]="violationsHistoryDiff?.projects[project.key]"
              [type]="'blockers'">
            </sq-project-violations>
          </td>
          <td>
            <sq-project-violations
              [violations]="project.violations"
              [violationsDiff]="violationsHistoryDiff?.projects[project.key]"
              [type]="'criticals'">
            </sq-project-violations>
          </td>
          <td>
            <sq-project-violations
              [violations]="project.violations"
              [violationsDiff]="violationsHistoryDiff?.projects[project.key]"
              [type]="'nonRelevant'">
            </sq-project-violations>
          </td>
          <td><a [href]="project.url">{{project.serverId}}</a></td>
        </tr>
      </ng-container>
    </table>
  `
})
export class GroupProjectsComponent {

  @Input() projects: ProjectSummary[];
  @Input() violationsHistoryDiff: GroupViolationsHistoryDiff;
  @Input() filter;

  isVisibleViaFilter(project: ProjectSummary): boolean {
    return this.getFilter()(project);
  }

  private getFilter(): (ProjectSummary) => boolean {
    if (this.filter === 'regression') {
      return (project: ProjectSummary) => this.getProjectDiff(project.key) && this.hasAnyAddedViolations(this.getProjectDiff(project.key));
    } else if (this.filter === 'improvement') {
      return (project: ProjectSummary) => this.getProjectDiff(project.key) && this.hasOnlyRemovedViolations(this.getProjectDiff(project.key));
    } else if (this.filter === 'changed') {
      return (project: ProjectSummary) => this.getProjectDiff(project.key) && this.hasChangedViolations(this.getProjectDiff(project.key));
    } else {
      return () => true;
    }
  }

  private getProjectDiff(projectKey: string): Violations {
    return this.violationsHistoryDiff && this.violationsHistoryDiff.projects[projectKey];
  }

  private hasAnyAddedViolations(projectDiff: Violations): boolean {
    return projectDiff.blockers > 0 || projectDiff.criticals > 0 || projectDiff.nonRelevant > 0;
  }

  private hasOnlyRemovedViolations(projectDiff: Violations): boolean {
    const nonRegression = projectDiff.blockers <= 0 && projectDiff.criticals <= 0 && projectDiff.nonRelevant <= 0;
    const atLeastOneImproved = projectDiff.blockers < 0 || projectDiff.criticals < 0 || projectDiff.nonRelevant < 0;
    return nonRegression && atLeastOneImproved;
  }

  private hasChangedViolations(projectDiff: Violations): boolean {
    return projectDiff.blockers != 0 || projectDiff.criticals != 0 || projectDiff.nonRelevant != 0;
  }

}
