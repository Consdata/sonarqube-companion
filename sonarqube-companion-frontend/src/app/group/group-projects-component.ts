import {Component, Input} from '@angular/core';
import {ProjectSummary} from '../project/project-summary';
import {GroupViolationsHistoryDiff} from '../violations/group-violations-history-diff';

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
      <tr *ngFor="let project of projects" [attr.health-status]="project.healthStatusString"
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
    </table>
  `
})
export class GroupProjectsComponent {

  @Input() projects: ProjectSummary[];
  @Input() violationsHistoryDiff: GroupViolationsHistoryDiff;

}
