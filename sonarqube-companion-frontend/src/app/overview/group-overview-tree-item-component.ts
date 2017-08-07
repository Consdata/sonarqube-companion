import {Component, HostBinding, Input} from '@angular/core';
import {GroupOverview} from './group-overview';

@Component({
  selector: 'sq-overview-tree-item',
  template: `
    <a routerLink="/groups/{{group.uuid}}">
      <div class="group">
        <div class="name">{{group.name}}</div>
        <div class="description">
          <span *ngIf="group.description">{{group.description}} | </span>
          <span>projects: {{group.projectCount}}</span>
          <span>|</span>
          <span>{{group.healthStatusString}} ({{healthDescription}})</span>
        </div>
        <div class="expander"></div>
        <div>
          <i class="fa fa-arrow-circle-right"></i>
        </div>
      </div>
    </a>
    <div class="subgroups">
      <sq-overview-tree-item [group]="subGroup" *ngFor="let subGroup of group.groups"></sq-overview-tree-item>
    </div>
  `
})
export class GroupOverviewTreeItemComponent {

  @Input() group: GroupOverview;

  @HostBinding('attr.health-status')
  get attrHealthStatus() {
    return this.group.healthStatusString;
  }

  get healthDescription() {
    const violations = this.group.violations;
    if (!violations.hasAny()) {
      return 'no violations found';
    }

    let healthDescription = '';
    if (violations.blockers > 0) {
      healthDescription += `blockers: ${violations.blockers}`;
    }
    if (violations.criticals > 0) {
      if (healthDescription.length > 0) {
        healthDescription += ', '
      }
      healthDescription += `criticals: ${violations.criticals}`;
    }
    if (violations.nonRelevant > 0) {
      if (healthDescription.length > 0) {
        healthDescription += ', '
      }
      healthDescription += `nonRelevant: ${violations.nonRelevant}`;
    }

    return healthDescription;
  }

}
