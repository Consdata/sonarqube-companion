import {Component, Input} from '@angular/core';
import {GroupDetails} from 'app/group/group-details';
import {Violations} from '../violations/violations';

@Component({
  selector: 'sq-group-overview-cards',
  template: `
    <sq-group-overview-card
      [class]="group.healthStatusString"
      [icon]="'fa ' + (group.healthy ? 'fa-thumbs-o-up' : 'fa-thumbs-down')">
      <div text>
        {{group.healthStatusString}}
      </div>
      <div description>
        overall score
      </div>
    </sq-group-overview-card>
    <sq-group-overview-card
      [class.success]="group.violations.blockers === 0"
      [class.danger]="group.violations.blockers > 0"
      [icon]="'fa fa-ban'">
      <div text>
        <sq-project-violations
          [violations]="violations"
          [addedViolations]="addedViolations"
          [removedViolations]="removedViolations"
          [violationsDiff]="violationsDiff"
          [detailedDiff]="true"
          [type]="'blockers'">
        </sq-project-violations>
      </div>
      <div description>
        blockers
      </div>
    </sq-group-overview-card>
    <sq-group-overview-card
      [class.success]="group.violations.criticals === 0"
      [class.warning]="group.violations.criticals > 0"
      [icon]="'fa fa-exclamation-circle'">
      <div text>
        <sq-project-violations
          [violations]="violations"
          [addedViolations]="addedViolations"
          [removedViolations]="removedViolations"
          [violationsDiff]="violationsDiff"
          [detailedDiff]="true"
          [type]="'criticals'">
        </sq-project-violations>
      </div>
      <div description>
        criticals
      </div>
    </sq-group-overview-card>
    <sq-group-overview-card
      class="gray"
      [icon]="'fa fa-info-circle'">
      <div text>
        <sq-project-violations
          [violations]="violations"
          [addedViolations]="addedViolations"
          [removedViolations]="removedViolations"
          [violationsDiff]="violationsDiff"
          [detailedDiff]="true"
          [type]="'nonRelevant'">
        </sq-project-violations>
      </div>
      <div description>
        other issues
      </div>
    </sq-group-overview-card>
    <sq-group-overview-card
      class="gray"
      [icon]="'fa fa-briefcase'">
      <div text>
        {{group.projects.length}}
      </div>
      <div description>
        projects
      </div>
    </sq-group-overview-card>
    <sq-group-overview-card
      class="gray"
      [icon]="'fa fa-folder-open-o'">
      <div text>
        {{group.groups.length}}
      </div>
      <div description>
        groups
      </div>
    </sq-group-overview-card>
  `
})
export class GroupOverviewCardsComponent {

  @Input() group: GroupDetails;
  @Input() violations: Violations;
  @Input() violationsDiff: Violations;
  @Input() addedViolations: Violations;
  @Input() removedViolations: Violations;

}
