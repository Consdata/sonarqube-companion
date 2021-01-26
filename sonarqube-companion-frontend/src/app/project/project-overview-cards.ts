import {Component, Input} from '@angular/core';
import {Violations} from '../violations/violations';
import {ProjectSummary} from './project-summary';

@Component({
  selector: 'sq-project-overview-cards',
  template: `
    <sq-overview-card
      [class]="project.healthStatusString"
      [icon]="'fa ' + (project.healthy ? 'fa-thumbs-o-up' : 'fa-thumbs-down')">
      <div text>
        {{project.healthStatusString}}
      </div>
      <div description>
        overall score
      </div>
    </sq-overview-card>
    <sq-overview-card
      [class.success]="project.violations.blockers === 0"
      [class.danger]="project.violations.blockers > 0"
      [icon]="'fa fa-ban'">
      <div text>
        <sq-project-violations
          [violations]="violations"
          [violationsDiff]="violationsDiff"
          [addedViolations]="addedViolations"
          [removedViolations]="removedViolations"
          [detailedDiff]="true"
          [type]="'blockers'">
        </sq-project-violations>
      </div>
      <div description>
        blockers
      </div>
    </sq-overview-card>
    <sq-overview-card
      [class.success]="project.violations.criticals === 0"
      [class.warning]="project.violations.criticals > 0"
      [icon]="'fa fa-exclamation-circle'">
      <div text>
        <sq-project-violations
          [violations]="violations"
          [violationsDiff]="violationsDiff"
          [addedViolations]="addedViolations"
          [removedViolations]="removedViolations"
          [detailedDiff]="true"
          [type]="'criticals'">
        </sq-project-violations>
      </div>
      <div description>
        criticals
      </div>
    </sq-overview-card>
    <sq-overview-card
      class="gray"
      [icon]="'fa fa-info-circle'">
      <div text>
        <sq-project-violations
          [violations]="violations"
          [violationsDiff]="violationsDiff"
          [addedViolations]="addedViolations"
          [removedViolations]="removedViolations"
          [detailedDiff]="true"
          [type]="'nonRelevant'">
        </sq-project-violations>
      </div>
      <div description>
        other issues
      </div>
    </sq-overview-card>
  `
})
export class ProjectOverviewCardsComponent {

  @Input() project: ProjectSummary;
  @Input() violations: Violations;
  @Input() violationsDiff: Violations;
  @Input() addedViolations: Violations;
  @Input() removedViolations: Violations;

}
