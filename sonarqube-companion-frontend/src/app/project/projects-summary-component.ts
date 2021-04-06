import {Component, Input} from '@angular/core';
import {ProjectSummary} from '../project/project-summary';
import {GroupViolationsHistoryDiff} from '../violations/group-violations-history-diff';
import {Violations} from '../violations/violations';

@Component({
  selector: 'sq-projects-summary',
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
          <td>
            <a [routerLink]="['/project', project.key]">
              {{project.name}}
            </a>
          </td>
          <td>
            <a [routerLink]="['/project', project.key]">
              {{project.key}}
            </a>
          </td>
          <td class="project-violations-cell">
            <ng-container *ngIf="violationsHistoryDiff?.projects[project.key]">
              <sq-project-violations
                [violationsDiffLink]="getViolationsDiffUrl(project, 'blockers')"
                [violationsLink]="getViolationsUrl(project, 'blockers')"
                [violations]="project.violations"
                [violationsDiff]="violationsHistoryDiff.projects[project.key].violations"
                [type]="'blockers'">
              </sq-project-violations>
            </ng-container>
          </td>
          <td class="project-violations-cell">
            <ng-container *ngIf="violationsHistoryDiff?.projects[project.key]">
              <sq-project-violations
                [violationsDiffLink]="getViolationsDiffUrl(project, 'criticals')"
                [violationsLink]="getViolationsUrl(project, 'criticals')"
                [violations]="project.violations"
                [violationsDiff]="violationsHistoryDiff.projects[project.key].violations"
                [type]="'criticals'">
              </sq-project-violations>
            </ng-container>
          </td>
          <td class="project-violations-cell">
            <ng-container *ngIf="violationsHistoryDiff?.projects[project.key]">
              <sq-project-violations
                [violationsDiffLink]="getViolationsDiffUrl(project, 'nonRelevant')"
                [violationsLink]="getViolationsUrl(project, 'nonRelevant')"
                [violations]="project.violations"
                [violationsDiff]="violationsHistoryDiff.projects[project.key].violations"
                [type]="'nonRelevant'">
              </sq-project-violations>
            </ng-container>
          </td>
          <td><a target="_blank" rel="noopener noreferrer"
                 [href]="getProjectDashboardUrl(project)">{{project.serverId}}</a></td>
        </tr>
      </ng-container>
    </table>
  `
})
export class ProjectsSummaryComponent {

  @Input() uuid: string;
  @Input() projects: ProjectSummary[];
  @Input() violationsHistoryDiff: GroupViolationsHistoryDiff;
  @Input() filter: string;

  isVisibleViaFilter(project: ProjectSummary): boolean {
    return this.getFilter()(project);
  }

  getProjectDashboardUrl(project: ProjectSummary): string {
    return `${project.serverUrl}dashboard?id=${encodeURI(project.key)}`;
  }

  getViolationsDiffUrl(project: ProjectSummary, type: string): string {
    const fromDate = this.violationsHistoryDiff.projects[project.key].fromDate;
    const toDate = this.violationsHistoryDiff.projects[project.key].toDate;
    return `${project.serverUrl}project/issues?resolved=false&id=${encodeURI(project.key)}&severities=${this.mapAsSeverities(type)}&createdAfter=${fromDate}&createdBefore=${toDate}`;
  }

  getViolationsUrl(project: ProjectSummary, type: string): string {
    const toDate = this.violationsHistoryDiff.projects[project.key].toDate;
    return `${project.serverUrl}project/issues?resolved=false&id=${encodeURI(project.key)}&severities=${this.mapAsSeverities(type)}&createdBefore=${toDate}`;
  }


  private getFilter(): (project: ProjectSummary) => boolean {
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
    return this.violationsHistoryDiff && this.violationsHistoryDiff.projects[projectKey] && this.violationsHistoryDiff.projects[projectKey].violations;
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
    return projectDiff.blockers !== 0 || projectDiff.criticals !== 0 || projectDiff.nonRelevant !== 0;
  }

  private mapAsSeverities(type: string): string {
    if (type === 'blockers') {
      return 'BLOCKER';
    } else if (type === 'criticals') {
      return 'CRITICAL';
    } else {
      return 'MAJOR,MINOR,INFO';
    }
  }

}
