import {Component, Input} from '@angular/core';
import {GroupDetails} from 'app/group/group-details';

@Component({
  selector: 'sq-group-overview-cards',
  template: `
    <sq-group-overview-card
      [class]="group.healthStatusString"
      [icon]="'fa fa-thumbs-o-up'"
      [value]="group.healthStatusString"
      [metric]="'overall score'">
    </sq-group-overview-card>
    <sq-group-overview-card
      [class.success]="group.violations.blockers === 0"
      [class.danger]="group.violations.blockers > 0"
      [icon]="'fa fa-ban'"
      [value]="group.violations.blockers"
      [metric]="'blockers'">
    </sq-group-overview-card>
    <sq-group-overview-card
      [class.success]="group.violations.criticals === 0"
      [class.warning]="group.violations.criticals > 0"
      [icon]="'fa fa-exclamation-circle'"
      [value]="group.violations.criticals"
      [metric]="'criticals'">
    </sq-group-overview-card>
    <sq-group-overview-card
      class="gray"
      [icon]="'fa fa-info-circle'"
      [value]="group.violations.nonRelevant"
      [metric]="'other issues'">
    </sq-group-overview-card>
    <sq-group-overview-card
      class="gray"
      [icon]="'fa fa-briefcase'"
      [value]="group.projects.length"
      [metric]="'projects'">
    </sq-group-overview-card>
    <sq-group-overview-card
      class="gray"
      [icon]="'fa fa-folder-open-o'"
      [value]="group.groups.length"
      [metric]="'groups'">
    </sq-group-overview-card>
  `
})
export class GroupOverviewCardsComponent {

  @Input() group: GroupDetails;

}
